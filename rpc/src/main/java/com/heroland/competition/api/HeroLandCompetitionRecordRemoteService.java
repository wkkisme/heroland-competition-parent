package com.heroland.competition.api;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;

import java.util.List;

/**
 * 比赛记录
 * @author wangkai
 */
public interface HeroLandCompetitionRecordRemoteService {

    /**
     * 比赛记录增加
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> addCompetitionRecord(HeroLandCompetitionRecordDP dp);

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



}
