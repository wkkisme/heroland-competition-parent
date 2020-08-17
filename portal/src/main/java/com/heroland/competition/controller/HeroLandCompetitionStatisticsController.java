package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.AnswerCompetitionResultDP;
import com.heroland.competition.domain.dp.AnswerQuestionRecordStatisticDP;
import com.heroland.competition.domain.dp.CompetitionCourseFinishStatisticDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dto.HeroLandQuestionListForTopicDto;
import com.heroland.competition.domain.qo.*;
import com.heroland.competition.domain.request.HeroLandTopicQuestionForCourseRequest;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
     * 查询比赛列表统计列表,根据传不同的type来区分，切type不能为空 需要什么类型的数据传什么字段
     * 例子：
     * 1 查询当前科目  根据科目查 ：传参为userId、和subject_code 即可查询当前人下当前科目下
     * 2 查询全科 传userId就可以查全科
     *
     * @param qo qo
     * @return HeroLandStatisticsTotalDPs
     */
    @PostMapping("/getStatisticsCompetitions")
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSyncCompetitions(@RequestBody HeroLandStatisticsTotalQO qo) {
        return heroLandCompetitionStatisticsService.getCompetitionsDetail(qo);
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
    ResponseBody<List<AnswerQuestionRecordStatisticDP>> getAnswerQuestionRecordStatistic(@RequestBody HeroLandTopicQuestionsPageRequest qo) {
        return heroLandCompetitionStatisticsService.getAnswerQuestionRecordStatistic(qo);
    }

    /**
     * 获取比赛结果
     *
     * @param qo
     * @return
     */
    @PostMapping("/getAnswerResult")
    ResponseBody<AnswerCompetitionResultDP> getAnswerResult(@RequestBody AnswerResultQO qo) {
        return heroLandCompetitionStatisticsService.getAnswerResult(qo);
    }
}
