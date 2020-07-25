package com.heroland.competition.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constants.TopicTypeConstants;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.HeroLevelUtils;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.dp.HeroLandCalculatorResultDP;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandQuestionRecordDetailDP;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;
import com.heroland.competition.service.HeroLandAccountService;
import com.heroland.competition.service.HeroLandCalculatorService;
import com.heroland.competition.service.HeroLandQuestionRecordDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 作業賽計分規則
 * <p>
 * 答題限時2分鐘；任何一方答完即出結果，對或錯，分數。立即出勝負。
 * 己方為勝：①  己方早答對，對方無論答對或錯；②  己方遲答對，對方早答錯。
 * 選擇不同英雄等級的對手，勝方計分方法：
 * 勝高兩級對手，得6分；
 * 勝高一級對手，得5分；
 * 勝同級別對手，得4分；
 * 勝低一級對手，得3分；
 * 勝低兩級對手，得2分。
 * 己方為和：己方答錯，對方亦答錯，得0分。
 * 己方為負：① 雙方都答對，己方遲答對，得1分。② 己方答錯，對方答對，得0分。
 */
@Component
public class HeroLandCalculatorServiceImpl implements HeroLandCalculatorService {

    @Resource
    private HeroLandAccountService heroLandAccountService;

    @Resource
    private HeroLandQuestionRecordDetailService questionRecordDetailService;

    @Resource
    private HeroLevelUtils heroLevelUtils;

    /**
     * 每道题初试分数
     */
    @Value("hero.init.answerScore")
    private Integer initAnswerScore;

    /**
     * 結束比賽，計算分數
     *
     * @param dp
     * @return
     */
    @Override
    public HeroLandCalculatorResultDP calculate(HeroLandCompetitionRecordDP dp, String userId) {

        // 獲取雙方比賽用戶信息
        ResponseBody<HeroLandAccountDP> inviteUserRes = heroLandAccountService.getAccountByUserId(dp.getInviteId());
        ResponseBody<HeroLandAccountDP> opponentUserRes = heroLandAccountService.getAccountByUserId(dp.getOpponentId());
        HeroLandAccountDP inviteUser = inviteUserRes.getData();
        HeroLandAccountDP opponentUser = opponentUserRes.getData();
        if (ObjectUtil.isNull(inviteUser) || ObjectUtil.isNull(opponentUser)) {
            ResponseBodyWrapper.failException("用户不存在");
        }

        // 獲取邀請人的答題記錄
        HeroLandQuestionQO questionQo = new HeroLandQuestionQO();
        questionQo.setCompetitionRecordId(dp.getRecordId());
        questionQo.setUserId(dp.getInviteId());
        ResponseBody<List<HeroLandQuestionRecordDetailDP>> inviteQuestionRecordRes = questionRecordDetailService.getQuestionRecord(questionQo);

        // 获取被邀请人答题记录
        questionQo.setCompetitionRecordId(dp.getRecordId());
        questionQo.setUserId(dp.getOpponentId());
        ResponseBody<List<HeroLandQuestionRecordDetailDP>> beInviteQuestionRecordRes = questionRecordDetailService.getQuestionRecord(questionQo);

        // 当前用户是否为邀请人
        boolean isInvite = StrUtil.equals(inviteUser.getUserId(), userId);
        if (isInvite) {
            // 计算当前用户的分数
            Integer answerScore = calculateAnswerScore(inviteQuestionRecordRes.getData(), beInviteQuestionRecordRes.getData(), inviteUser, opponentUser);
            dp.setInviteScore(answerScore);
        } else {
            // 计算当前用户的分数
            Integer answerScore = calculateAnswerScore(beInviteQuestionRecordRes.getData(), inviteQuestionRecordRes.getData(), opponentUser, inviteUser);
            dp.setOpponentScore(answerScore);
        }

        Integer inviteScore = dp.getInviteScore();
        Integer opponentScore = dp.getOpponentScore();

        // 如果是应试赛，获胜者分数X2
        if (TopicTypeConstants.TEST_COMPETITION.equals(dp.getTopicType()) &&
                ObjectUtil.isNotNull(inviteScore) &&
                ObjectUtil.isNotNull(opponentScore)) {
            if (dp.getInviteScore() > dp.getOpponentScore()) {
                dp.setInviteScore(inviteScore * 2);
            } else {
                dp.setOpponentScore(opponentScore * 2);
            }
        }

        HeroLandCalculatorResultDP resultDP = new HeroLandCalculatorResultDP();
        resultDP.setInviteLevel(inviteUser.getLevelName());
        resultDP.setInviteScore(dp.getInviteScore());
        resultDP.setOpponentLevel(dp.getOpponentLevel());
        resultDP.setOpponentScore(dp.getOpponentScore());
        return resultDP;
    }

    /**
     * 计算每道题分数总和
     *
     * @param currentUserQuestionRecords 当前用户的答题记录
     * @param opponentQuestionRecords    对手的答题记录
     * @return
     */
    private Integer calculateAnswerScore(List<HeroLandQuestionRecordDetailDP> currentUserQuestionRecords,
                                         List<HeroLandQuestionRecordDetailDP> opponentQuestionRecords,
                                         HeroLandAccountDP currentUser,
                                         HeroLandAccountDP opponentUser) {

        if (CollUtil.isEmpty(currentUserQuestionRecords)) {
            return 0;
        }

        // 当前用户的答题记录map
        Map<String, HeroLandQuestionRecordDetailDP> currentUserQuestionRecordMap =
                currentUserQuestionRecords
                        .stream()
                        .collect(
                                Collectors.toMap(HeroLandQuestionRecordDetailDP::getQuestionId,
                                        Function.identity(),
                                        (o, n) -> n));

        // 对手的答题记录
        Map<String, HeroLandQuestionRecordDetailDP> opponentQuestionRecordMap =
                opponentQuestionRecords
                        .stream()
                        .collect(
                                Collectors.toMap(HeroLandQuestionRecordDetailDP::getQuestionId,
                                        Function.identity(),
                                        (o, n) -> n));

        AtomicInteger atomicInteger = new AtomicInteger(0);
        currentUserQuestionRecordMap.forEach((questionId, questionRecord) -> {

            Date beginDate = questionRecord.getBeginDate();
            Date endDate = questionRecord.getEndDate();

            // 用户答题还未答完，报错
            AssertUtils.assertThat(ObjectUtil.isNull(beginDate) || ObjectUtil.isNull(endDate), "答题未完成，无法计算分数。");

            // 判断用户是否答对
            boolean isCorrect = StrUtil.equals(questionRecord.getAnswer(), questionRecord.getYourAnswer());

            // 得到当前用户和对手的等级差异
            Integer compare = heroLevelUtils.compare(currentUser.getLevelScore(), opponentUser.getLevelScore());

            // 如果答题正确计算分数
            if (isCorrect) {
                atomicInteger.addAndGet(initAnswerScore + compare);
            }

            // 判断用户答题时间是否小于对手的答题时间
            HeroLandQuestionRecordDetailDP opponentQuestionRecord = opponentQuestionRecordMap.get(questionId);

            // 如果对手的question记录是空，说明用户还没有完成答该场比赛。并且当前用户答对，当前用户得分+1
            if (ObjectUtil.isNull(opponentQuestionRecord) && isCorrect) {
                atomicInteger.incrementAndGet();
            }
            // 如果对手的question记录不是空，则需要对比答题用时
            else if (ObjectUtil.isNotNull(opponentQuestionRecord)) {
                Date opponentBeginDate = opponentQuestionRecord.getBeginDate();
                Date opponentEndDate = opponentQuestionRecord.getEndDate();
                // 对手没有答完，并且当前用户答对，当前用户+1分
                if (ObjectUtil.isNull(opponentEndDate) && isCorrect) {
                    atomicInteger.incrementAndGet();
                }
                // 对手已经答完，对比时间
                else if (ObjectUtil.isNotNull(opponentEndDate)) {
                    long opponentBeginDateTime = opponentBeginDate.getTime();
                    long opponentEndDateTime = opponentEndDate.getTime();
                    long beginDateTime = beginDate.getTime();
                    long endDateTime = endDate.getTime();
                    // 对手耗时
                    long opponentTimeConsuming = opponentEndDateTime - opponentBeginDateTime;
                    // 当前用户耗时
                    long currentUserTimeConsuming = endDateTime - beginDateTime;
                    // 如果当前用户耗时比对手耗时短，则+1分
                    if (currentUserTimeConsuming > opponentTimeConsuming) {
                        atomicInteger.incrementAndGet();
                    }
                }
            }
        });

        return atomicInteger.get();
    }
}
