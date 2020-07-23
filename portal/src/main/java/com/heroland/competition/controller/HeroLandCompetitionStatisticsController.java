package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 比赛统计查询
 * @author  wangkai
 * @date 2020-07-23
 */
@RestController
@RequestMapping("/heroland/statistics")
public class HeroLandCompetitionStatisticsController {
    @Resource
    private HeroLandCompetitionStatisticsService heroLandCompetitionStatisticsService;


    /**
     * 查询同步作业赛列表
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsTotalDP>> getSyncCompetitions(HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getSyncCompetitions(qo);
    }

    /**
     * 查询同步作业赛详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSyncCompetitionsDetail(HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getSyncCompetitionsDetail(qo);
    }

    /**
     * 查询某人比赛详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsDetailDP>> getCompetitionsDetail(HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getCompetitionsDetail(qo);
    }

    /**
     * 查询寒假作业赛详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsDetailDP>> getWinterVacationCompetitionsDetail(HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getWinterVacationCompetitionsDetail(qo);
    }

    /**
     * 查询寒假作业赛
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsTotalDP>> getWinterVacationCompetitions(HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getWinterVacationCompetitions(qo);
    }

    /**
     * 查询暑假作业赛详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSummerVacationCompetitionsDetail(HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getSummerVacationCompetitionsDetail(qo);
    }

    /**
     * 查询暑假作业赛
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsTotalDP>> getSummerVacationCompetitions(HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getSummerVacationCompetitions(qo);
    }

    /**
     * 查询世界赛详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsDetailDP>> getWorldCompetitionsDetail(HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getWorldCompetitionsDetail(qo);
    }

    /**
     * 查询世界赛
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsTotalDP>> getWorldCompetitions(HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getWorldCompetitions(qo);
    }

    /**
     * 查询校际赛-学校排行榜
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsTotalDP>> getSchoolsCompetitions(HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getSchoolsCompetitions(qo);
    }

    /**
     * 查询校际赛-某个学校里学生排行榜
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSchoolCompetitions(HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getSchoolCompetitions(qo);
    }
}
