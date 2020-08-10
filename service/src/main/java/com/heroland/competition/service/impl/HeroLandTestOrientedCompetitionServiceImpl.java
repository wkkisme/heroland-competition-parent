package com.heroland.competition.service.impl;

import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.enums.CompetitionResultEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dto.HeroLandQuestionListForTopicDto;
import com.heroland.competition.domain.dto.HeroLandTopicDto;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;
import com.heroland.competition.service.HeroLandAccountService;
import com.heroland.competition.service.HeroLandCompetitionService;
import com.heroland.competition.service.HeroLandQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

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
public class HeroLandTestOrientedCompetitionServiceImpl implements HeroLandCompetitionService {

    @Resource
    private HeroLandQuestionService heroLandQuestionService;

    @Resource
    private HeroLandAccountService heroLandAccountService;

    @Resource
    private RedisService redisService;

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
        heroLandTopicQuestionsPageRequest.setTopicId(Long.valueOf(record.getTopicId()));
        PageResponse<HeroLandQuestionListForTopicDto> topicQuestions = heroLandQuestionService.getTopicQuestions(heroLandTopicQuestionsPageRequest);
        if (CollectionUtils.isEmpty(topicQuestions.getItems())) {
            ResponseBodyWrapper.fail("比赛不存在", "50003");
        }
        int questionCount = topicQuestions.getItems().size();

        if (record.getDetails().size() != questionCount) {
            ResponseBodyWrapper.fail("请完整答题", "50004");
        }
        // 判断是否超时

        HeroLandTopicQuestionsPageRequest topicQuestionsPageRequest = new HeroLandTopicQuestionsPageRequest();
        topicQuestionsPageRequest.setTopicId(Long.valueOf(record.getTopicId()));
        // 先查出该场比赛的是否在比赛时间内

        HeroLandTopicDto topic = heroLandQuestionService.getTopic(topicQuestionsPageRequest);
        // 如果小于0 则说明结束时间比当前时间小，超时
        if (topic.getEndTime().getTime() - System.currentTimeMillis() < 0) {
            ResponseBodyWrapper.fail("请在规定时间范围内答题", "50005");
        }

        String redisKey = record.getTopicId() + record.getInviteId() + record.getOpponentId();
        boolean lock = redisService.setNx(redisKey, record.getTopicId(), "PT24H");

        // 谁先拿到锁 谁先答完题
        if (lock) {
            // 先拿到锁的人进入
            // 返回答对几道题
            int rightCount = judgeAnswerAndCalculateScore();
            // 如果全部答对 直接判胜利
            if (rightCount == questionCount) {
                //  如果是邀请人
                if (record.getUserId().equalsIgnoreCase(record.getInviteId())) {
                    record.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                } else if (record.getUserId().equalsIgnoreCase(record.getOpponentId())) {
                    // 如果是被邀请人
                    record.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                }
                //
            }else {


            }


        } else {
            // 后答题的人进入


            redisService.del(redisKey);
        }


        return null;
    }

    private int judgeAnswerAndCalculateScore() {


        return 0;
    }
}
