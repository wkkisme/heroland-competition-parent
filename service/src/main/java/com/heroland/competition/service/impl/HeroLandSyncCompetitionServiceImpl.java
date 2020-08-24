package com.heroland.competition.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constants.HeroLandRedisConstants;
import com.heroland.competition.common.enums.CompetitionResultEnum;
import com.heroland.competition.common.enums.HeroLevelEnum;
import com.heroland.competition.common.enums.RedisRocketmqConstant;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.dto.HeroLandQuestionBankDto;
import com.heroland.competition.domain.qo.HeroLandAccountManageQO;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.service.*;
import com.heroland.competition.service.admin.HeroLandQuestionBankService;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.heroland.competition.common.enums.Constants.CommandReqType.STOP_ANSWER_QUESTIONS;

/**
 * 同步比賽
 *
 * @author mac
 */
@Service("HeroLandSyncCompetitionService")
public class HeroLandSyncCompetitionServiceImpl implements HeroLandCompetitionService {

    @Resource
    private RedisService redisService;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private HeroLandAccountService heroLandAccountService;

    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Resource
    private HeroLandQuestionRecordDetailService heroLandQuestionRecordDetailService;

    @Resource
    private HeroLandQuestionBankService heroLandQuestionBankService;

    /**
     * type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     * 0 1 2 類型比賽都是一樣的，使用同一套積分規則就可以
     *
     * 介紹：做題的同時完成學校佈置功課；作業賽分為同步作業賽、寒假作業賽和暑期作業賽三種賽事。
     * 同步作業賽：根據教材的課節出題，有效地溫習所學內容。
     * 寒假作業賽：以上學期所學知識點相關題為競賽題目，在寒假期間競賽的同時溫故知新。
     * 暑期作業賽：以本年度所學知識點相關題為競賽題目，在暑期期間鞏固過去一年的學習。
     * 規則：答題限時2分鐘；任何一方答完即出結果，對或錯，分數。立即出勝負。
     * 勝負及計分方法：
     * 己方為勝：①  己方早答對，對方無論答對或錯；②  己方遲答對，對方早答錯。
     * 選擇不同英雄等級的對手，勝方計分方法：
     * 勝高兩級對手，得6分；
     * 勝高一級對手，得5分；
     * 勝同級別對手，得4分；
     * 勝低一級對手，得3分；
     * 勝低兩級對手，得2分。
     * 己方為和：己方答錯，對方亦答錯，得0分。
     * 己方為負：① 雙方都答對，己方遲答對，得1分。② 己方答錯，對方答對，得0分。
     * @return
     */
    @Override
    public Integer getType() {
        return 0;
    }

    /**
     * 答题
     *
     * @param record 答题
     * @return 结果
     */
    @Override
    public ResponseBody<HeroLandCompetitionRecordDP> doAnswer(HeroLandCompetitionRecordDP record) {

        // 己方為勝：①  己方早答對，對方無論答對或錯；②  己方遲答對，對方早答錯。

        // 极限情况下，如果两个人同时答题，数据库还没有保存记录时 解决方案对题组+题id+userId 加锁先拿到锁的人先答完题
        //  后面一个人就直接更新
        HeroLandQuestionRecordDetailDP heroLandQuestionRecordDetailDP = record.getDetails().get(0);
        HeroLandQuestionBankDto question = heroLandQuestionBankService.getById(heroLandQuestionRecordDetailDP.getId());
        boolean isRight;
        heroLandQuestionRecordDetailDP.setAnswer(question.getOptionAnswer());
        if (question.getOptionAnswer().equalsIgnoreCase(heroLandQuestionRecordDetailDP.getYourAnswer())) {
            heroLandQuestionRecordDetailDP.setCorrectAnswer(true);
            isRight = true;
        } else {
            heroLandQuestionRecordDetailDP.setAnswer(question.getOptionAnswer());
            heroLandQuestionRecordDetailDP.setScore(0);
            heroLandQuestionRecordDetailDP.setCorrectAnswer(false);
            isRight = false;
        }

        boolean timeout = false;
        //  数据库记录
        HeroLandCompetitionRecordQO heroLandCompetitionRecordQO = new HeroLandCompetitionRecordQO();
        heroLandCompetitionRecordQO.setTopicId(record.getTopicId());
        heroLandCompetitionRecordQO.setQuestionId(String.valueOf(record.getQuestionId()));
        heroLandCompetitionRecordQO.setInviteRecordId(record.getInviteRecordId());
        heroLandCompetitionRecordQO.setRecordId(record.getRecordId());
        ResponseBody<List<HeroLandCompetitionRecordDP>> databaseRecord = heroLandCompetitionRecordService.getCompetitionRecords(heroLandCompetitionRecordQO);
        if (CollectionUtils.isEmpty(databaseRecord.getData())) {
            ResponseBodyWrapper.fail("比赛不存在", "5000");
            // 2分钟
        } else if (System.currentTimeMillis() - databaseRecord.getData().get(0).getInviteStartTime().getTime() > 120000L) {
            timeout = true;
        }
        HeroLandCompetitionRecordDP heroLandCompetitionRecordDP = databaseRecord.getData().get(0);
        record.setInviteId(heroLandCompetitionRecordDP.getInviteId());
        record.setInviteStartTime(heroLandCompetitionRecordDP.getInviteStartTime());
        record.setOpponentId(heroLandCompetitionRecordDP.getOpponentId());
        record.setOpponentEndTime(heroLandCompetitionRecordDP.getOpponentStartTime());
        record.setInviteLevel(heroLandCompetitionRecordDP.getInviteLevel());
        record.setOpponentLevel(heroLandCompetitionRecordDP.getOpponentLevel());
        record.setId(heroLandCompetitionRecordDP.getId());
        String redisKey = record.getTopicId() + record.getQuestionId() + record.getInviteId() + record.getOpponentId();
        boolean lock = redisService.setNx(redisKey+record.getId(), record.getUserId(), "PT2H");
        redisService.set("question:" + redisKey+record.getUserId(), heroLandQuestionRecordDetailDP, 1200000L);
        HeroLandAccountManageQO heroLandAccountManageQO = new HeroLandAccountManageQO();
        if (record.getUserId().equalsIgnoreCase(record.getInviteId())) {
            record.setInviteEndTime(new Date());
            record.setInviteScore(0);
            record.setSenderId(record.getInviteId());
            record.setAddresseeId(record.getOpponentId());
        }else {
            record.setOpponentEndTime(new Date());
            record.setOpponentScore(0);
            record.setSenderId(record.getOpponentId());
            record.setAddresseeId(record.getInviteId());
        }
        if (lock) {
            //  如果正确 且先拿到锁，说明先答题，更新记录为当前人胜 且没有超时 如果第一个都超时，后一个没有必要再更新记录
            if (isRight && !timeout) {

                if (record.getUserId().equalsIgnoreCase(record.getInviteId())) {
                    //  当前人是邀请者
                    record.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                    int levelScore = HeroLevelEnum.getLevelScore(record.getInviteLevel(), record.getOpponentLevel());
                    // 增加分数
                    record.setInviteScore(levelScore);
                    heroLandQuestionRecordDetailDP.setScore(levelScore);
                    heroLandAccountManageQO.setUserId(record.getUserId());
                    heroLandAccountManageQO.setScore(levelScore);

                } else {
                    // 当前人是被邀请者
                    int levelScore = HeroLevelEnum.getLevelScore(record.getOpponentLevel(), record.getInviteLevel());
                    // 增加分数
                    heroLandAccountManageQO.setUserId(record.getUserId());
                    heroLandAccountManageQO.setScore(levelScore);
                    record.setOpponentScore(levelScore);
                    heroLandQuestionRecordDetailDP.setScore(levelScore);
                    record.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                }
                heroLandAccountService.incrDecrUserScore(heroLandAccountManageQO);

            } else if (timeout) {
                heroLandQuestionRecordDetailDP.setScore(0);
                record.setOpponentScore(0);
                record.setInviteScore(0);
                record.setResult(CompetitionResultEnum.DRAW.getResult());

            }
            heroLandQuestionRecordDetailService.addQuestionRecords(record.record2Detail());
            heroLandCompetitionRecordService.updateCompetitionRecord(record);

            //  后一个答题者
        } else {
            // 前一个答题者答案
            HeroLandQuestionRecordDetailDP preAnswer ;
            if (record.getUserId().equalsIgnoreCase(record.getInviteId())) {
                preAnswer = (HeroLandQuestionRecordDetailDP) redisService.get("question:" + redisKey+record.getInviteId());
            }else {
                preAnswer = (HeroLandQuestionRecordDetailDP) redisService.get("question:" + redisKey+record.getOpponentId());
            }
            // 如果正确 且未超时 则看前一个答题者答案正确与否
            if (isRight && !timeout) {
                // 如果前一个人答案正确则直接不管，因为前面已经判赢了，如果前面答题错了则本人胜利
                if (!preAnswer.getCorrectAnswer()) {
                    // 如果我是邀请人
                    if (record.getUserId().equalsIgnoreCase(record.getInviteId())) {
                        record.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                        int levelScore = HeroLevelEnum.getLevelScore(record.getInviteLevel(), record.getOpponentLevel());
                        // 增加分数

                        heroLandAccountManageQO.setUserId(record.getUserId());
                        heroLandQuestionRecordDetailDP.setScore(levelScore);
                        heroLandAccountManageQO.setScore(levelScore);
                    }else {
                        // 如果我是被邀请人
                        record.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                        int levelScore = HeroLevelEnum.getLevelScore(record.getOpponentLevel(), record.getInviteLevel());
                        // 增加分数
                        heroLandQuestionRecordDetailDP.setScore(levelScore);
                        heroLandAccountManageQO.setUserId(record.getUserId());
                        heroLandAccountManageQO.setScore(levelScore);
                    }
                    heroLandQuestionRecordDetailDP.setCorrectAnswer(true);
                    // 如果两个人都答错 则都不加分 平局
                }else {
                    heroLandQuestionRecordDetailDP.setScore(0);
                    heroLandQuestionRecordDetailDP.setCorrectAnswer(false);
                    record.setInviteScore(0);
                    record.setOpponentScore(0);
                    record.setResult(CompetitionResultEnum.DRAW.getResult());
                }

            }else {
                // 若两人都超时 则也是平局
                record.setResult(CompetitionResultEnum.DRAW.getResult());
            }

            // 如果是后面再答题者，不需要更新状态，前一个已经更新为平局
            heroLandQuestionRecordDetailService.addQuestionRecords(record.record2Detail());
            heroLandCompetitionRecordService.updateCompetitionRecord(record);
            redisService.del(redisKey);

            record.setType(STOP_ANSWER_QUESTIONS.getCode());

            rocketMQTemplate.syncSend(RedisRocketmqConstant.IM_SINGLE, JSON.toJSONString(record));
        }
        return ResponseBodyWrapper.successWrapper(record);
    }
}
