package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.constant.ErrMsgEnum;
import com.anycommon.response.page.Pagination;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.*;
import com.heroland.competition.domain.dto.*;
import com.heroland.competition.domain.qo.*;
import com.heroland.competition.domain.request.HeroLandTopicQuestionForCourseRequest;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;
import com.heroland.competition.service.HeroLandQuestionRecordDetailService;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import com.platform.sso.client.sso.util.CookieUtils;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Resource
    private HeroLandQuestionRecordDetailService heroLandQuestionRecordDetailService;

    @Resource
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;


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
    ResponseBody<List<HeroLandStatisticsDetailDP>> getSyncCompetitions(HttpServletRequest request, @RequestBody HeroLandStatisticsTotalQO qo) {
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
     * 查询比赛记录（不需要根据topic往下查）
     *
     * @param qo
     * @return
     */
    @PostMapping("/queryCompetitionRecords")
    ResponseBody<List<HeroLandQuestionRecordDetailDP>> queryCompetitionRecords(@RequestBody HeroLandQuestionQO qo) {
        return heroLandQuestionRecordDetailService.getQuestionRecord(qo);
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

    /**
     * 指定某一科的统计结果
     *
     * @param qo
     * @return
     */
    @PostMapping("/getCourseResultForW")
    ResponseBody<List<CourseResultForWDto>> getCourseResultForW(@RequestBody CourseResultForWQO qo) {
        ResponseBody<List<CourseResultForWDto>> result = new ResponseBody<>();
        PageResponse<CourseResultForWDto> pageResponse = heroLandCompetitionStatisticsService.courseResultForWQO(qo);
        result.setData(pageResponse.getItems());
        Pagination pagination = new Pagination();
        pagination.setPageIndex(pageResponse.getPage());
        pagination.setPageSize(pageResponse.getPageSize());
        pagination.setTotalCount(pageResponse.getTotal());
        pagination.setTotalPage(pageResponse.getTotalPages());
        result.setPage(pagination);
        return result;

    }

    /**
     * 所有科的统计结果
     *
     * @param qo
     * @return
     */
    @PostMapping("/getAllCourseResultForW")
    ResponseBody<List<AllCourseResultForWDto>> getAllCourseResultForW(@RequestBody AllCourseResultForWQO qo) {
        ResponseBody<List<AllCourseResultForWDto>> result = new ResponseBody<>();
        List<AllCourseResultForWDto> dtos = heroLandCompetitionStatisticsService.allCourseResultForWQO(qo);
        result.setData(dtos);
        return result;

    }

    /**
     * 指定某一科的不同用户统计结果
     *
     * @param qo
     * @return
     */
    @PostMapping("/getCourseResultForUser")
    ResponseBody<List<CourseResultForUserDto>> getAllCourseResultForUser(@RequestBody CourseResultForUserQO qo) {
        ResponseBody<List<CourseResultForUserDto>> result = new ResponseBody<>();
        PageResponse<CourseResultForUserDto> pageResponse = heroLandCompetitionStatisticsService.getAllCourseResultForUser(qo);
        result.setData(pageResponse.getItems());
        Pagination pagination = new Pagination();
        pagination.setPageIndex(pageResponse.getPage());
        pagination.setPageSize(pageResponse.getPageSize());
        pagination.setTotalCount(pageResponse.getTotal());
        pagination.setTotalPage(pageResponse.getTotalPages());
        result.setPage(pagination);
        return result;

    }

    /**
     * 指定某一项比赛的参赛结果
     * @param qo
     * @return
     */
    @PostMapping("/worldStatisticResult")
    ResponseBody<List<WorldStatisticResultDto>> worldStatisticResult(@RequestBody WorldStatisticQO qo) {
        return heroLandCompetitionStatisticsService.worldStatisticResult(qo);
    }


}
