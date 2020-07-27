package com.heroland.competition.service.statistics;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.AnswerQuestionRecordStatisticDP;
import com.heroland.competition.domain.dp.CompetitionCourseFinishStatisticDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.qo.AnswerQuestionRecordStatisticQO;
import com.heroland.competition.domain.qo.CourseFinishStatisticQO;
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

//    /**
//     * 查找比赛总记录
//     *
//     * @param qo dp
//     * @return Boolean
//     */
//    ResponseBody<List<HeroLandStatisticsTotalDP>> getCompetitionsTatal(HeroLandStatisticsTotalQO qo);

    /**
     * 插入比赛总记录以及详情
     *
     * @param dp dp
     * @return Boolean
     */
    ResponseBody<Boolean> saveStatisticsTotalAndDetail(List<HeroLandStatisticsTotalDP> totalDPS, List<HeroLandStatisticsDetailDP> detailDPS);

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


    ResponseBody<List<HeroLandStatisticsDetailDP>> getCompetitionsDetail(HeroLandStatisticsTotalQO qo);

    /**
     * 同步作业赛-查询每个科目比赛完成情况
     *
     * @param qo
     * @return
     */
    ResponseBody<List<CompetitionCourseFinishStatisticDP>> getCourseFinishStatistic(CourseFinishStatisticQO qo);

    /**
     * 查询比赛记录
     *
     * @param qo
     * @return
     */
    ResponseBody<List<AnswerQuestionRecordStatisticDP>> getAnswerQuestionRecordStatistic(AnswerQuestionRecordStatisticQO qo);
}
