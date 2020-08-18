package com.heroland.competition.service.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.enums.CompetitionResultEnum;
import com.heroland.competition.common.enums.HeroLevelEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandCompetitionResultDP;
import com.heroland.competition.domain.dto.HeroLandQuestionListForTopicDto;
import com.heroland.competition.domain.dto.HeroLandTopicDto;
import com.heroland.competition.domain.qo.HeroLandAccountManageQO;
import com.heroland.competition.domain.request.HeroLandTopicPageRequest;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;
import com.heroland.competition.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 应试赛规则：
 * * 整盤勝負及計分方法：
 * * 賽事規則：每盤比賽有多條題目，答對題數多，又快者，勝。
 * * 整盤勝負：整盤贏多局者為勝，相同局數計和局，相同和局計總時數，快者勝。
 * * 計分方法：各回合按勝負和對手等級高低計分，然後加總。勝方總分再 x 2，負方總分 x 1
 *
 * @author wangkai
 */
@Service("HeroLandTestOrientedCompetitionService")
@Slf4j
public class HeroLandTestOrientedCompetitionServiceImpl implements HeroLandCompetitionService {

    @Resource
    private HeroLandQuestionService heroLandQuestionService;

    @Resource
    private HeroLandAccountService heroLandAccountService;

    @Resource
    private RedisService redisService;

    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Resource
    private HeroLandQuestionRecordDetailService heroLandQuestionRecordDetailService;

    /**
     * type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     *
     * @return Integer
     */
    @Override
    public Integer getType() {
        return CompetitionEnum.EXAM.getType();
    }

    /**
     * 選擇不同英雄等級的對手，每一道题計分方法：
     * * 勝高兩級對手，得6分；
     * * 勝高一級對手，得5分；
     * * 勝同級別對手，得4分；
     * * 勝低一級對手，得3分；
     * * 勝低兩級對手，得2分。
     * <p>
     * 应试赛规则：
     * * * 整盤勝負及計分方法：
     * * * 賽事規則：每盤比賽有多條題目，答對題數多，又快者，勝。
     * * * 整盤勝負：整盤贏多局者為勝，相同局數計和局，相同和局計總時數，快者勝。
     * * * 計分方法：各回合按勝負和對手等級高低計分，然後加總。勝方總分再 x 2，負方總分 x 1
     * *
     *
     * @param record 答题
     * @return HeroLandCompetitionRecordDP
     */

    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(HeroLandCompetitionRecordDP record) {
        record.addTestOrientedCheck();

        // 先查出题目
        HeroLandTopicQuestionsPageRequest heroLandTopicQuestionsPageRequest = new HeroLandTopicQuestionsPageRequest();
        heroLandTopicQuestionsPageRequest.setTopicIds(Lists.newArrayList(Long.valueOf(record.getTopicId())));
        PageResponse<HeroLandQuestionListForTopicDto> topicQuestions = heroLandQuestionService.getTopicQuestions(heroLandTopicQuestionsPageRequest);
        if (CollectionUtils.isEmpty(topicQuestions.getItems())) {
            ResponseBodyWrapper.fail("比赛不存在", "50003");
        }
        int questionCount = topicQuestions.getItems().size();

        if (record.getDetails().size() != questionCount) {
            ResponseBodyWrapper.fail("请完整答题", "50004");
        }
        // 判断是否超时

        HeroLandTopicPageRequest topicQuestionsPageRequest = new HeroLandTopicPageRequest();
        topicQuestionsPageRequest.setTopicId(Long.valueOf(record.getTopicId()));
        // 先查出该场比赛的是否在比赛时间内

        HeroLandTopicDto topic = heroLandQuestionService.getTopic(topicQuestionsPageRequest);
        // 如果小于0 则说明结束时间比当前时间小，超时
        if (topic.getEndTime().getTime() - System.currentTimeMillis() < 0) {
            ResponseBodyWrapper.fail("请在规定时间范围内答题", "50005");
        }

        String redisKey = record.getTopicId() + record.getInviteId() + record.getOpponentId();
        boolean lock = redisService.setNx(redisKey, record.getTopicId(), "PT24H");
        if (record.getUserId().equalsIgnoreCase(record.getInviteId())) {
            record.setInviteEndTime(new Date());
        } else if (record.getUserId().equalsIgnoreCase(record.getOpponentId())) {
            record.setOpponentEndTime(new Date());
        }
        // 谁先拿到锁 谁先答完题
        if (lock) {
            // 先拿到锁的人进入
            // 返回答对几道题
            HeroLandCompetitionResultDP resultDP = judgeAnswerAndCalculateScore(record, topicQuestions.getItems());

            redisService.set("question:" + getType() + record.getUserId(), resultDP);
            log.info("resultDP{}", JSON.toJSONString(resultDP));
            // 如果全部答对 直接判胜利
            if (resultDP.getRightCount() == questionCount) {
                //  如果是邀请人
                if (record.getUserId().equalsIgnoreCase(record.getInviteId())) {
                    record.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                    record.setInviteScore(resultDP.getScore());
                } else if (record.getUserId().equalsIgnoreCase(record.getOpponentId())) {
                    // 如果是被邀请人
                    record.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                    record.setOpponentScore(resultDP.getScore());
                }

                heroLandCompetitionRecordService.updateCompetitionRecord(record);
                heroLandQuestionRecordDetailService.addQuestionRecords(record.record2Detail());
            } else {
                // 不是全部答对，直接保存，等下一个人来计算
                heroLandCompetitionRecordService.updateCompetitionRecord(record);
                heroLandQuestionRecordDetailService.addQuestionRecords(record.record2Detail());
            }


        } else {
            try {
                // 后答题的人进入
                HeroLandCompetitionResultDP otherResult = judgeAnswerAndCalculateScore(record, topicQuestions.getItems());

                // 当前人是邀请人
                if (record.getUserId().equalsIgnoreCase(record.getInviteId())) {
                    HeroLandCompetitionResultDP rightCount = (HeroLandCompetitionResultDP) redisService.get("question:" +getType() +record.getOpponentId());
                    log.info("rightCount{}", JSON.toJSONString(rightCount));
                    record.setInviteScore(otherResult.getScore());
                    // 答题数相等对方胜 1 需要把对方计算过后的分数*2 加上
                    if (rightCount.getRightCount() == questionCount) {
                        HeroLandAccountManageQO heroLandAccountManageQO = new HeroLandAccountManageQO();
                        heroLandAccountManageQO.setScore(rightCount.getScore());
                        heroLandAccountManageQO.setUserId(record.getInviteId());
                        heroLandAccountService.incrDecrUserScore(heroLandAccountManageQO);
                        heroLandQuestionRecordDetailService.addQuestionRecords(record.getDetails());
                    } else {
                        // 否则判断每个人的时间和答对数量
                        // 1 如果答题数量相等或者前一个答对数量多 则前一个人胜
                        if (rightCount.getRightCount().equals(otherResult.getRightCount()) || rightCount.getRightCount() > otherResult.getRightCount()) {

                            record.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                            // 否则就是答对比对方多 胜
                        } else {
                            record.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                        }
                        heroLandCompetitionRecordService.updateCompetitionRecord(record);
                        heroLandQuestionRecordDetailService.addQuestionRecords(record.record2Detail());
                    }

                } else {

                    // 当前人是被邀请人
                    HeroLandCompetitionResultDP inviteResult = (HeroLandCompetitionResultDP) redisService.get("question:" + getType() + record.getInviteId());
                    log.info("inviteResult{}", JSON.toJSONString(inviteResult));
                    record.setOpponentScore(otherResult.getScore());
                    // 答题数相等对方胜 1 需要把对方计算过后的分数*2 加上
                    if (inviteResult.getRightCount() == questionCount) {
                        HeroLandAccountManageQO heroLandAccountManageQO = new HeroLandAccountManageQO();
                        heroLandAccountManageQO.setScore(inviteResult.getScore());
                        heroLandAccountManageQO.setUserId(record.getOpponentId());
                        heroLandAccountService.incrDecrUserScore(heroLandAccountManageQO);
                        heroLandQuestionRecordDetailService.addQuestionRecords(record.record2Detail());
                    } else {
                        // 否则判断每个人的时间和答对数量
                        // 1 如果答题数量相等或者前一个答对数量多 则前一个人胜
                        if (inviteResult.getRightCount().equals(otherResult.getRightCount()) || inviteResult.getRightCount() > otherResult.getRightCount()) {

                            record.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                            // 否则就是答对比对方多 胜
                        } else {
                            record.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                        }
                        heroLandCompetitionRecordService.updateCompetitionRecord(record);
                        heroLandQuestionRecordDetailService.addQuestionRecords(record.record2Detail());
                    }
                }


            } finally {
                redisService.del(redisKey);
            }
        }


        return ResponseBodyWrapper.successWrapper(record);
    }

    private HeroLandCompetitionResultDP judgeAnswerAndCalculateScore(HeroLandCompetitionRecordDP record, List<HeroLandQuestionListForTopicDto> items) {
        Map<String, HeroLandQuestionListForTopicDto> topicDtoHashMap = items.stream().collect(
                Collectors.toMap(HeroLandQuestionListForTopicDto::getQtId, item -> item, (a, b) -> b, () -> new HashMap<>(items.size())));


        //  1 account插入计算分数 如果胜利之后再乘
        HeroLandCompetitionResultDP resultDP = new HeroLandCompetitionResultDP();
        AtomicInteger rightCount = new AtomicInteger();
        AtomicInteger score = new AtomicInteger();
        record.getDetails().forEach(v -> {
            HeroLandQuestionListForTopicDto heroLandQuestionListForTopicDto = topicDtoHashMap.get(v.getQuestionId());
            //如果答对
            if (heroLandQuestionListForTopicDto != null && heroLandQuestionListForTopicDto.getOptionAnswer().equalsIgnoreCase(v.getYourAnswer())) {
                // 如果是邀请者
                if (record.getUserId().equalsIgnoreCase(record.getInviteId())) {
                    int levelScore = HeroLevelEnum.getLevelScore(record.getInviteLevel(), record.getOpponentLevel());
                    v.setCorrectAnswer(true);
                    rightCount.addAndGet(1);
                    score.addAndGet(levelScore);
                } else {
                    // 如果是被邀请者
                    int levelScore = HeroLevelEnum.getLevelScore(record.getOpponentLevel(), record.getInviteLevel());
                    v.setCorrectAnswer(true);
                    rightCount.addAndGet(1);
                    score.addAndGet(levelScore);
                }

            } else if (heroLandQuestionListForTopicDto == null || !heroLandQuestionListForTopicDto.getOptionAnswer().equalsIgnoreCase(v.getYourAnswer())) {
                // 没有找到题 直接错误
                v.setCorrectAnswer(false);
            }

        });

        //  2 redis 存住这次的数据，用于后续比较
        resultDP.setRightCount(rightCount.get());
        resultDP.setScore(score.get());
        redisService.set("question:" + getType() + record.getUserId(), resultDP);
        // 3 account 插入
        HeroLandAccountManageQO heroLandAccountManageQO = new HeroLandAccountManageQO();
        heroLandAccountManageQO.setUserId(record.getUserId());
        heroLandAccountManageQO.setScore(score.get());
        heroLandAccountService.incrDecrUserScore(heroLandAccountManageQO);
        return resultDP;
    }
}
