package com.heroland.competition.service;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsAllQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;

import java.util.List;

/**
 * 比赛记录
 * @author wangkai
 */
public interface HeroLandCompetitionRecordService {

    /**
     * 比赛记录增加
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<String> addCompetitionRecord(HeroLandCompetitionRecordDP dp);

    /**
     * 比赛记录增加 和详细记录
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> addCompetitionAndDetail(HeroLandCompetitionRecordDP dp);

    /**
     * 比赛记录更新
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> updateCompetitionRecord(HeroLandCompetitionRecordDP dp);
    /**
     * 比赛记录更新
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> updateCompetitionRecordByTopicId(HeroLandCompetitionRecordDP dp);

    /**
     * 比赛记录删除
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> deleteCompetitionRecord(HeroLandCompetitionRecordDP dp);


    /**
     * 获取比赛记录和题
     * @param qo 对象
     * @return 值
     */
    ResponseBody<List<HeroLandCompetitionRecordDP>> getCompetitionRecordsAndQuestions(HeroLandCompetitionRecordQO qo);



    /**
     * 获取多次比赛记录
     * @param qo 对象
     * @return 值
     */
    ResponseBody<List<HeroLandCompetitionRecordDP>> getCompetitionRecords(HeroLandCompetitionRecordQO qo);
    /**
     * 获取多次比赛记录 和详情
     * @param qo 对象
     * @return 值
     */
    ResponseBody<List<HeroLandCompetitionRecordDP>> getCompetitionRecordsAndDetail(HeroLandCompetitionRecordQO qo);


    /**
     * 获取单次比赛记录
     * @param recordId recordId
     * @return 值
     */
    ResponseBody<HeroLandCompetitionRecordDP> getCompetitionRecordByRecordId(HeroLandCompetitionRecordQO recordId);

    /**
     * 根据邀请id获取
     * @param recordId
     * @return
     */
    ResponseBody<HeroLandCompetitionRecordDP> getCompetitionRecordByInviteRecordId(HeroLandCompetitionRecordQO recordId);


    List<HeroLandStatisticsDetailDP> getTotalScore(HeroLandStatisticsAllQO qo);


    List<HeroLandStatisticsDetailDP> getAnswerRightRate(HeroLandStatisticsAllQO heroLandStatisticsTotalQO);

    List<HeroLandStatisticsDetailDP> getCompleteRate(HeroLandStatisticsAllQO heroLandStatisticsTotalQO);

    List<HeroLandStatisticsDetailDP> getWinRate(HeroLandStatisticsTotalQO heroLandStatisticsTotalQO);

    List<HeroLandStatisticsDetailDP> getTotalTime(HeroLandStatisticsAllQO heroLandStatisticsTotalQO);

    HeroLandCompetitionRecordDP getRecordInfo(String topicId, String inviteId, String opponentId);

    ResponseBody<HeroLandCompetitionRecordDP> getLatestCompetitionRecord(HeroLandCompetitionRecordQO qo);
}
