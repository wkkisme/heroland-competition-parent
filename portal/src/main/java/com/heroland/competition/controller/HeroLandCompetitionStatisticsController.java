package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

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
     * 查询同步作业赛列表统计列表,
     *  1 查询个人排行榜时或者页面上面的统计信息可根据传userId等信息过来查询
     *
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getSyncCompetitions")
    ResponseBody<List<HeroLandStatisticsTotalDP>> getSyncCompetitions(@RequestBody HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getSyncCompetitions(qo);
    }

    /**
     * 查询同步作业赛统计详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getSyncCompetitionsDetail")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSyncCompetitionsDetail(@RequestBody HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getSyncCompetitionsDetail(qo);
    }

    /**
     * 查询某人比赛统计详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getCompetitionsDetail")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getCompetitionsDetail(@RequestBody HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getCompetitionsDetail(qo);
    }

    /**
     * 查询寒假作业赛统计详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getWinterVacationCompetitionsDetail")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getWinterVacationCompetitionsDetail(@RequestBody HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getWinterVacationCompetitionsDetail(qo);
    }

    /**
     * 查询寒假作业赛统计
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getWinterVacationCompetitions")
    ResponseBody<List<HeroLandStatisticsTotalDP>> getWinterVacationCompetitions(@RequestBody HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getWinterVacationCompetitions(qo);
    }

    /**
     * 查询暑假作业赛详情统计 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getSummerVacationCompetitionsDetail")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSummerVacationCompetitionsDetail(@RequestBody HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getSummerVacationCompetitionsDetail(qo);
    }

    /**
     * 查询暑假作业赛统计
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getSummerVacationCompetitions")
    ResponseBody<List<HeroLandStatisticsTotalDP>> getSummerVacationCompetitions(@RequestBody HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getSummerVacationCompetitions(qo);
    }

    /**
     * 查询世界赛详情统计 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getWorldCompetitionsDetail")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getWorldCompetitionsDetail(@RequestBody HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getWorldCompetitionsDetail(qo);
    }

    /**
     * 查询世界赛统计
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getWorldCompetitions")
    ResponseBody<List<HeroLandStatisticsTotalDP>> getWorldCompetitions(@RequestBody HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getWorldCompetitions(qo);
    }

    /**
     * 查询校际赛-学校排行榜统计
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getSchoolsCompetitions")
    ResponseBody<List<HeroLandStatisticsTotalDP>> getSchoolsCompetitions(@RequestBody HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getSchoolsCompetitions(qo);
    }

    /**
     * 查询校际赛-某个学校里学生排行榜统计
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getSchoolCompetitions")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSchoolCompetitions(@RequestBody HeroLandStatisticsTotalQO qo){
        return heroLandCompetitionStatisticsService.getSchoolCompetitions(qo);
    }
}
