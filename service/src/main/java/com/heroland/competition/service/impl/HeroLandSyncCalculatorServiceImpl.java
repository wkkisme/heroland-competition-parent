package com.heroland.competition.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constants.TopicTypeConstants;
import com.heroland.competition.common.enums.CompetitionResultEnum;
import com.heroland.competition.common.enums.HeroLevelEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.HeroLevelUtils;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.dp.HeroLandCalculatorResultDP;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.dto.HeroLandQuestionBankDto;
import com.heroland.competition.domain.qo.HeroLandAccountManageQO;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;
import com.heroland.competition.service.HeroLandAccountService;
import com.heroland.competition.service.HeroLandCalculatorService;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.HeroLandQuestionRecordDetailService;
import com.heroland.competition.service.admin.HeroLandQuestionBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 同步作业赛
 * 計分規則
 * <p>
 * 每个回合积分规则：
 * 己方為勝：①  己方早答對，對方無論答對或錯；②  己方遲答對，對方早答錯。
 * 選擇不同英雄等級的對手，勝方計分方法：
 * 勝高兩級對手，得6分；
 * 勝高一級對手，得5分；
 * 勝同級別對手，得4分；
 * 勝低一級對手，得3分；
 * 勝低兩級對手，得2分。
 * 己方為和：己方答錯，對方亦答錯，得0分。
 * 己方為負：① 雙方都答對，己方遲答對，得1分。② 己方答錯，對方答對，得0分。
 * <p>
 * 作业赛规则：
 * 整盤勝負及計分方法：
 * 賽事類型：作業賽分為同步作業賽、寒假作業賽和暑期作業賽三種賽事。
 * 規則：答題限時2分鐘；任何一方答完即出結果，對或錯，分數。立即出勝負。
 * <p>
 * 应试赛规则：
 * 整盤勝負及計分方法：
 * 賽事規則：每盤比賽有多條題目，答對題數多，又快者，勝。
 * 整盤勝負：整盤贏多局者為勝，相同局數計和局，相同和局計總時數，快者勝。
 * 計分方法：各回合按勝負和對手等級高低計分，然後加總。勝方總分再 x 2，負方總分 x 1
 *
 * @author wangkai
 * @date 2020-7-25
 */
@Component
@Slf4j
public class HeroLandSyncCalculatorServiceImpl implements HeroLandCalculatorService {

    @Resource
    private HeroLandAccountService heroLandAccountService;

    @Resource
    private HeroLandQuestionRecordDetailService heroLandQuestionRecordDetailService;

    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Resource
    private RedisService redisService;

    @Resource
    private HeroLandQuestionBankService heroLandQuestionBankService;

    @Override
    public Integer getType() {
        return 0;
    }

    /**
     * 結束比賽，計算分數
     *
     * @param record
     * @return
     */
    @Override
    public HeroLandCompetitionRecordDP calculate(HeroLandCompetitionRecordDP record) {
        // 己方為勝：①  己方早答對，對方無論答對或錯；②  己方遲答對，對方早答錯。

        // 极限情况下，如果两个人同时答题，数据库还没有保存记录时 解决方案对题组+题id+userId 加锁先拿到锁的人先答完题
        //  后面一个人就直接更新
        HeroLandQuestionRecordDetailDP heroLandQuestionRecordDetailDP = record.getDetails().get(0);
        HeroLandQuestionBankDto question = heroLandQuestionBankService.getById(heroLandQuestionRecordDetailDP.getId());
        boolean isRight;
        if (question.getOptionAnswer().equalsIgnoreCase(heroLandQuestionRecordDetailDP.getYourAnswer())) {
            heroLandQuestionRecordDetailDP.setCorrectAnswer(true);
            isRight = true;
        } else {
            heroLandQuestionRecordDetailDP.setCorrectAnswer(false);
            isRight = false;
        }
        boolean timeout = false;
        //  数据库记录
        HeroLandCompetitionRecordQO heroLandCompetitionRecordQO = new HeroLandCompetitionRecordQO();
        heroLandCompetitionRecordQO.setTopicId(record.getTopicId());
        heroLandCompetitionRecordQO.setQuestionId(String.valueOf(record.getQuestionId()));
        ResponseBody<List<HeroLandCompetitionRecordDP>> databaseRecord = heroLandCompetitionRecordService.getCompetitionRecords(heroLandCompetitionRecordQO);
        if (CollectionUtils.isEmpty(databaseRecord.getData())) {
            ResponseBodyWrapper.fail("比赛不存在", "5000");
            // 2分钟
        } else if (System.currentTimeMillis() - databaseRecord.getData().get(0).getInviteStartTime().getTime() > 120000L) {
            timeout = true;
        }

        String redisKey = record.getTopicId() + record.getQuestionId() + record.getInviteId() + record.getOpponentId();
        boolean lock = redisService.setNx(redisKey, record, "PT2H");
        redisService.set("question:" + redisKey, heroLandQuestionRecordDetailDP, 1200000L);
        HeroLandAccountManageQO heroLandAccountManageQO = new HeroLandAccountManageQO();
        if (lock) {
            //  如果正确 且先拿到锁，说明先答题，更新记录为当前人胜 且没有超时 如果第一个都超时，后一个没有必要再更新记录
            if (isRight && !timeout) {

                if (record.getUserId().equalsIgnoreCase(record.getInviteId())) {
                    //  当前人是邀请者
                    record.setResult(CompetitionResultEnum.INVITE_WIN.getResult());
                    int levelScore = HeroLevelEnum.getLevelScore(record.getInviteLevel(), record.getOpponentLevel());
                    // 增加分数

                    heroLandAccountManageQO.setUserId(record.getUserId());
                    heroLandAccountManageQO.setScore(levelScore);

                } else {
                    // 当前人是被邀请者
                    int levelScore = HeroLevelEnum.getLevelScore(record.getOpponentLevel(), record.getInviteLevel());
                    // 增加分数
                    heroLandAccountManageQO.setUserId(record.getUserId());
                    heroLandAccountManageQO.setScore(levelScore);
                    record.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                }
                heroLandAccountService.incrDecrUserScore(heroLandAccountManageQO);

            } else if (timeout) {
                record.setResult(CompetitionResultEnum.DRAW.getResult());

            }
            heroLandQuestionRecordDetailService.addQuestionRecords(record.getDetails());
            heroLandCompetitionRecordService.updateCompetitionRecord(record);

            //  后一个答题者
        } else {
            // 前一个答题者答案
            HeroLandQuestionRecordDetailDP preAnswer = (HeroLandQuestionRecordDetailDP) redisService.get("question:" + redisKey);
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
                        heroLandAccountManageQO.setScore(levelScore);
                    }else {
                        // 如果我是被邀请人
                        record.setResult(CompetitionResultEnum.BE_INVITE_WIN.getResult());
                        int levelScore = HeroLevelEnum.getLevelScore(record.getOpponentLevel(), record.getInviteLevel());
                        // 增加分数
                        heroLandAccountManageQO.setUserId(record.getUserId());
                        heroLandAccountManageQO.setScore(levelScore);
                    }
                    // 如果两个人都答错 则都不加分 平局
                }else {
                    record.setResult(CompetitionResultEnum.DRAW.getResult());
                }

            }
            // 如果是后面再答题者，不需要更新状态，前一个已经更新为平局
            heroLandQuestionRecordDetailService.addQuestionRecords(record.getDetails());
            heroLandCompetitionRecordService.updateCompetitionRecord(record);
        }
        return record;
    }
}
