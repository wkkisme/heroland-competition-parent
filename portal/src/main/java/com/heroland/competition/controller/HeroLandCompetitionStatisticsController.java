package com.heroland.competition.controller;

import cn.hutool.core.collection.ListUtil;
import com.anycommon.response.common.BaseQO;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.domain.dp.AnswerQuestionRecordStatisticDP;
import com.heroland.competition.domain.dp.CompetitionCourseFinishStatisticDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.qo.AnswerQuestionRecordStatisticQO;
import com.heroland.competition.domain.qo.CourseFinishStatisticQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 比赛统计查询
 *
 * @author wangkai
 * @date 2020-07-23
 */
@RestController
@RequestMapping("/heroland/statistics")
public class HeroLandCompetitionStatisticsController {
    @Resource
    private HeroLandCompetitionStatisticsService heroLandCompetitionStatisticsService;


    /**
     * 查询同步作业赛列表统计列表,
     * 1 查询个人排行榜时或者页面上面的统计信息可根据传userId等信息过来查询
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getSyncCompetitions")
    ResponseBody<List<HeroLandStatisticsTotalDP>> getSyncCompetitions(@RequestBody HeroLandStatisticsTotalQO qo) {
        return heroLandCompetitionStatisticsService.getSyncCompetitions(qo);
    }

    /**
     * 查询同步作业赛统计详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getSyncCompetitionsDetail")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSyncCompetitionsDetail(@RequestBody HeroLandStatisticsTotalQO qo) {
        return heroLandCompetitionStatisticsService.getSyncCompetitionsDetail(qo);
    }

    /**
     * 查询某人比赛统计详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getCompetitionsDetail")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getCompetitionsDetail(@RequestBody HeroLandStatisticsTotalQO qo) {
        return heroLandCompetitionStatisticsService.getCompetitionsDetail(qo);
    }

    /**
     * 查询寒假作业赛统计详情 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getWinterVacationCompetitionsDetail")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getWinterVacationCompetitionsDetail(@RequestBody HeroLandStatisticsTotalQO qo) {
        return heroLandCompetitionStatisticsService.getWinterVacationCompetitionsDetail(qo);
    }

    /**
     * 查询寒假作业赛统计
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getWinterVacationCompetitions")
    ResponseBody<List<HeroLandStatisticsTotalDP>> getWinterVacationCompetitions(@RequestBody HeroLandStatisticsTotalQO qo) {
        return heroLandCompetitionStatisticsService.getWinterVacationCompetitions(qo);
    }

    /**
     * 查询暑假作业赛详情统计 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getSummerVacationCompetitionsDetail")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSummerVacationCompetitionsDetail(@RequestBody HeroLandStatisticsTotalQO qo) {
        return heroLandCompetitionStatisticsService.getSummerVacationCompetitionsDetail(qo);
    }

    /**
     * 查询暑假作业赛统计
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getSummerVacationCompetitions")
    ResponseBody<List<HeroLandStatisticsTotalDP>> getSummerVacationCompetitions(@RequestBody HeroLandStatisticsTotalQO qo) {
        return heroLandCompetitionStatisticsService.getSummerVacationCompetitions(qo);
    }

    /**
     * 查询世界赛详情统计 userid 必传
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getWorldCompetitionsDetail")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getWorldCompetitionsDetail(@RequestBody HeroLandStatisticsTotalQO qo) {
        return heroLandCompetitionStatisticsService.getWorldCompetitionsDetail(qo);
    }

    /**
     * 查询世界赛统计
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getWorldCompetitions")
    ResponseBody<List<HeroLandStatisticsTotalDP>> getWorldCompetitions(@RequestBody HeroLandStatisticsTotalQO qo) {
        return heroLandCompetitionStatisticsService.getWorldCompetitions(qo);
    }

    /**
     * 查询校际赛-学校排行榜统计
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getSchoolsCompetitions")
    ResponseBody<List<HeroLandStatisticsTotalDP>> getSchoolsCompetitions(@RequestBody HeroLandStatisticsTotalQO qo) {
        return heroLandCompetitionStatisticsService.getSchoolsCompetitions(qo);
    }

    /**
     * 查询校际赛-某个学校里学生排行榜统计
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getSchoolCompetitions")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSchoolCompetitions(@RequestBody HeroLandStatisticsTotalQO qo) {
        return heroLandCompetitionStatisticsService.getSchoolCompetitions(qo);
    }

    /**
     * 查询每个科目比赛完成情况
     *
     * @param qo
     * @return
     */
    @PostMapping("/getCourseFinishStatistic")
    ResponseBody<List<CompetitionCourseFinishStatisticDP>> getCourseFinishStatistic(@RequestBody CourseFinishStatisticQO qo) {
//        List<CompetitionCourseFinishStatisticDP> list = new ArrayList<>();
//        AtomicInteger i = new AtomicInteger();
//        ListUtil.toList("中文", "數學", "英文", "常識", "歷史", "電腦", "STEM", "視藝").forEach(s -> {
//            CompetitionCourseFinishStatisticDP dp = new CompetitionCourseFinishStatisticDP();
//            dp.setCourseCode(String.valueOf(i.incrementAndGet()));
//            dp.setCourseName(s);
//            dp.setChapterCount(i.get() + 10);
//            dp.setClassCode("test");
//            dp.setFinishQuestion(i.get());
//            dp.setGradeCode("5");
//            dp.setGradeName("5年級");
//            dp.setOrgCode("1");
//            dp.setQuestionNum(i.get() + 10);
//            dp.setSectionCount(i.get() + 2);
//            dp.setFinishSection(i.get());
//            dp.setWinRate(new BigDecimal(20));
//            list.add(dp);
//        });
//        return ResponseBodyWrapper.successWrapper(list);
        return heroLandCompetitionStatisticsService.getCourseFinishStatistic(qo);
    }

    /**
     * 查询比赛记录
     *
     * @param qo
     * @return
     */
    @PostMapping("/getAnswerQuestionRecordStatistic")
    ResponseBody<List<AnswerQuestionRecordStatisticDP>> getAnswerQuestionRecordStatistic(@RequestBody AnswerQuestionRecordStatisticQO qo) {
//        List<AnswerQuestionRecordStatisticDP> list = new ArrayList<>();
//        for (int i = 1; i < 33; i++) {
//            AnswerQuestionRecordStatisticDP dp = new AnswerQuestionRecordStatisticDP();
//            dp.setDiff(1);
//            boolean b = i % 2 == 0;
//            dp.setIsCorrectAnswer(b);
//            dp.setKnowledge("四則運算");
//            dp.setLevelCode("2");
//            dp.setOpponentLevel(b ? "同級" : b == (i % 3 == 0) ? "高兩級" : "低一級");
//            dp.setResult(b ? 1 : b == (i % 3 == 0) ? 0 : 2);
//            dp.setScore(b ? 4 : b == (i % 3 == 0) ? 6 : 3);
//            dp.setQuestionId(1L);
//            dp.setTopicName("測試" + i);
//            list.add(dp);
//        }
//        BaseQO baseQO = new BaseQO();
//        baseQO.setPageIndex(1);
//        baseQO.setPageSize(40);
//        ResponseBody tResponseBody = new ResponseBody();
//        tResponseBody.setData(list);
//        tResponseBody.setPage(new Pagination(qo.getPageIndex(), qo.getPageSize(), 33));
//        return tResponseBody;
        return heroLandCompetitionStatisticsService.getAnswerQuestionRecordStatistic(qo);
    }
}
