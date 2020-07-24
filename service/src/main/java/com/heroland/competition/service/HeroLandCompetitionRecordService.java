package com.heroland.competition.service;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.dal.pojo.HeroLandStatisticsTotal;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
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
     * 比赛记录更新
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> updateCompetitionRecord(HeroLandCompetitionRecordDP dp);

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
     * 获取单次比赛记录
     * @param recordId recordId
     * @return 值
     */
    ResponseBody<HeroLandCompetitionRecordDP> getCompetitionRecordById(HeroLandCompetitionRecordQO recordId);


    List<HeroLandStatisticsTotalDP> getSyncTotalScore(HeroLandStatisticsTotalQO qo);

    List<HeroLandStatisticsDetailDP> getSyncTotalScoreDetail(HeroLandStatisticsTotalQO heroLandStatisticsTotalQO);

}
