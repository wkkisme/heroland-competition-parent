package com.heroland.competition.service.statistics;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;

import java.util.List;

/**
 * 统计相关服务
 *
 * @author wangkai
 */
public interface HeroLandCompetitionStatisticsService {

    /**
     * 插入比赛总记录
     *
     * @param dp dp
     * @return Boolean
     */
    ResponseBody<Boolean> saveStatisticsTotal(List<HeroLandStatisticsTotalDP> dp);

    /**
     * 查找比赛总记录
     *
     * @param qo dp
     * @return Boolean
     */
    ResponseBody<List<HeroLandStatisticsTotalDP>> getCompetitionsTatal(HeroLandStatisticsTotalQO qo);

    /**
     * 插入比赛总记录以及详情
     *
     * @param dp dp
     * @return Boolean
     */
    ResponseBody<Boolean> saveStatisticsTotalAndDetail(List<HeroLandStatisticsTotalDP> totalDPS,List<HeroLandStatisticsDetailDP> detailDPS);

    /**
     * 更新比赛总记录
     *
     * @param dp dp
     * @return Boolean
     */
    ResponseBody<Boolean> updateStatisticsTotal(List<HeroLandStatisticsTotalDP> dp);

    /**
     * 更新比赛总记录 根据条件
     *
     * @param qo dp
     * @return Boolean
     */
    ResponseBody<Boolean> updateHistoryStatisticsTotalAndDetailByQO(HeroLandStatisticsTotalQO qo);

    /**
     * 更新比赛总记录以及详情
     *
     * @param dp dp
     * @return Boolean
     */
    ResponseBody<Boolean> updateStatisticsTotalAndDetail(List<HeroLandStatisticsTotalDP> dp);

    /**
     * 查询同步作业赛列表
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsTotalDP>> getSyncCompetitions(HeroLandStatisticsTotalQO qo);

    /**
     * 查询同步作业赛详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSyncCompetitionsDetail(HeroLandStatisticsTotalQO qo);

    /**
     * 查询某人比赛详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsDetailDP>> getCompetitionsDetail(HeroLandStatisticsTotalQO qo);

    /**
     * 查询寒假作业赛详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsDetailDP>> getWinterVacationCompetitionsDetail(HeroLandStatisticsTotalQO qo);

    /**
     * 查询寒假作业赛
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsTotalDP>> getWinterVacationCompetitions(HeroLandStatisticsTotalQO qo);

    /**
     * 查询暑假作业赛详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSummerVacationCompetitionsDetail(HeroLandStatisticsTotalQO qo);

    /**
     * 查询暑假作业赛
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsTotalDP>> getSummerVacationCompetitions(HeroLandStatisticsTotalQO qo);

    /**
     * 查询世界赛详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsDetailDP>> getWorldCompetitionsDetail(HeroLandStatisticsTotalQO qo);

    /**
     * 查询世界赛
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsTotalDP>> getWorldCompetitions(HeroLandStatisticsTotalQO qo);

    /**
     * 查询校际赛-学校排行榜
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsTotalDP>> getSchoolsCompetitions(HeroLandStatisticsTotalQO qo);

    /**
     * 查询校际赛-某个学校里学生排行榜
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSchoolCompetitions(HeroLandStatisticsTotalQO qo);

}
