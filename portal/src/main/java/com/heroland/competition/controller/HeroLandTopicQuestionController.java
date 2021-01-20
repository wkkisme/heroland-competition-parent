package com.heroland.competition.controller;


import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.constant.ErrMsgEnum;
import com.anycommon.response.page.Pagination;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.dal.mapper.HeroLandTopicGroupMapper;
import com.heroland.competition.dal.pojo.HeroLandTopicGroup;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.dp.HerolandTopicGroupPartDP;
import com.heroland.competition.domain.dto.*;
import com.heroland.competition.domain.qo.HerolandTopicCanSeeQO;
import com.heroland.competition.domain.qo.HerolandTopicHeaderTeacherCanAssignQO;
import com.heroland.competition.domain.request.*;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.HeroLandTopicGroupService;
import com.heroland.competition.service.HerolandTopicGroupPartService;
import com.heroland.competition.service.HerolandTopicJoinUserService;
import com.platform.sso.client.sso.util.CookieUtils;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 題目組
 *
 * @author wangkai
 * @date 2020/6/19
 * @module 題目組
 */

@RestController
@RequestMapping("/heroland/topic")
public class HeroLandTopicQuestionController {

    @Resource
    private HeroLandQuestionService heroLandQuestionService;

    @Resource
    private HeroLandTopicGroupMapper heroLandTopicGroupMapper;

    @Resource
    private HerolandTopicGroupPartService herolandTopicGroupPartService;

    @Resource
    private HerolandTopicJoinUserService herolandTopicJoinUserService;

    @Resource
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;


    /**
     * 查询某个题组下的所有题目
     * @return e
     * @module 題目組
     */
    @RequestMapping("/queryQuestions")
    public ResponseBody<List<HeroLandQuestionListForTopicDto>> getTopicQuestion(@RequestBody HeroLandTopicQuestionsPageRequest request){
        ResponseBody<List<HeroLandQuestionListForTopicDto>> result = new ResponseBody<>();
        PageResponse<HeroLandQuestionListForTopicDto> pageResponse = heroLandQuestionService.getTopicQuestions(request);
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
     * 校际赛|世界赛的赛事列表
     * @param request
     * @return
     */
    @RequestMapping("/queryTopicsForS")
    public ResponseBody<List<HeroLandTopicForSDto>> getTopicsForS(@RequestBody HerolandTopicCanSeeQO request){
        ResponseBody<List<HeroLandTopicForSDto>> result = new ResponseBody<>();
        PageResponse<HeroLandTopicForSDto> pageResponse = herolandTopicJoinUserService.canOperableTopics(request);
        if (Objects.isNull(pageResponse)){
            return result;
        }
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
     * 查询班主任可推荐参赛的校际赛
     * @param request
     * @return
     */
    @RequestMapping("/schoolTopicsForTeacher")
    public ResponseBody<List<HeroLandTopicForSDto>> getSchoolTopicsForHeaderTeacher(@RequestBody HerolandTopicHeaderTeacherCanAssignQO request){
        ResponseBody<List<HeroLandTopicForSDto>> result = new ResponseBody<>();
        List<HeroLandTopicForSDto> topics = herolandTopicJoinUserService.getSchoolTopicsForHeaderTeacher(request);
        result.setData(topics);
        return result;
    }

    /**
     * 某一校际赛的题目信息
     * @param request
     * @return
     */
    @RequestMapping("/queryTopicQuestionsForS")
    public ResponseBody<List<HeroLandQuestionListForTopicDto>> getTopicQuestionsForS(@RequestBody HerolandTopicCanSeeQO request){
        ResponseBody<List<HeroLandQuestionListForTopicDto>> result = new ResponseBody<>();
//        HerolandTopicCanSeeDto herolandTopicCanSeeDto = herolandTopicJoinUserService.canOperableTopics(request);
        result.setData(null);
        return result;
    }






    /**
     * 查询某个题组信息
     * @return e
     * @module 題目組
     */
    @RequestMapping("/getTopic")
    public ResponseBody<HeroLandTopicDto> getTopic(@RequestBody HeroLandTopicPageRequest request){
        ResponseBody<HeroLandTopicDto> result = new ResponseBody<>();
        HeroLandTopicDto topic = heroLandQuestionService.getTopic(request);
        result.setData(topic);
        return result;
    }

    /**
     * 增加或者编辑比赛
     * @param request
     * @return e
     * @module 題目組
     */
    @RequestMapping("/addAndEdit")
    public ResponseBody<Long> addTopicQuestions(@RequestBody HeroLandTopicGroupRequest request){
        ResponseBody<Long> result = new ResponseBody<>();
        HeroLandTopicGroupDP dp = BeanCopyUtils.copyByJSON(request, HeroLandTopicGroupDP.class);
        result.setData(heroLandQuestionService.addTopic(dp));
        return result;
    }

    /**
     * 给某一比赛分配题目
     * @param request
     * @return e
     * @module 題目組
     */
    @RequestMapping("/assign")
    public ResponseBody<Boolean> assign(@RequestBody HeroLandTopicAssignRequest request){
        ResponseBody<Boolean> result = new ResponseBody<>();
        result.setData(heroLandQuestionService.saveAssign(request));
        return result;
    }

    /**
     * 删除某一比赛
     * @param
     * @return e
     * @module 題目組
     */
    @RequestMapping("/delete")
    public ResponseBody<Boolean> deleteTopic(@RequestParam("topicId") Long id){
        ResponseBody<Boolean> result = new ResponseBody<>();
        result.setData(heroLandQuestionService.deleteTopic(id));
        return result;
    }

    /**
     * 为某项赛事选择机构
     * 目前只是针对校际赛
     * 创建一个校际赛后需要选择学校，年级和科目
     * 校际赛的规则里题目不是固定的，是后台随机自己选择的
     * @param request
     * @return e
     * @module 題目組
     */
    @RequestMapping("/addDepartment")
    public ResponseBody<Boolean> addDepartmentForTopic(HttpServletRequest servletRequest,@RequestBody HeroLandTopicAddDepartmentRequest request){
        ResponseBody<Boolean> result = new ResponseBody<>();
        roleCheck(servletRequest, 4);
        if (CollectionUtils.isEmpty(request.getSchoolCourses())){
            List<HerolandTopicGroupPartDP> list = Lists.newArrayList();
            if (Objects.isNull(request.getType())){
                HeroLandTopicGroup heroLandTopicGroup = heroLandTopicGroupMapper.selectByPrimaryKey(request.getId());
                if (Objects.nonNull(heroLandTopicGroup)){
                    request.setType(heroLandTopicGroup.getType());
                }
            }
            request.getSchoolCourses().stream().forEach(e -> {
                HerolandTopicGroupPartDP dp = BeanCopyUtils.copyByJSON(request, HerolandTopicGroupPartDP.class);
                dp.setTopicType(request.getType());
                dp.setTopicId(request.getId());
                list.add(dp);
            });
            result.setData(herolandTopicGroupPartService.addBatchDepartment(list));
        }
        result.setData(false);
        return result;
    }

    /**
     * 新增一个校际赛|世界赛
     * @param request
     * @return e
     * @module 題目組
     */
    @RequestMapping("/schoolTopic")
    public ResponseBody<Boolean> addSchoolTopic(HttpServletRequest servletRequest, @RequestBody HeroLandTopicAddDepartmentRequest request){
        ResponseBody<Boolean> result = new ResponseBody<>();
        roleCheck(servletRequest, 4);
        heroLandQuestionService.addTopicForS(request);
        result.setData(true);
        return result;
    }

    /**
     * 批量删除某项赛事的参赛机构
     * @param ids
     * @return
     */
    @RequestMapping("/deleteDepartment")
    public ResponseBody<Boolean> deleteDepartmentForTopic(@RequestParam("ids") String ids){
        ResponseBody<Boolean> result = new ResponseBody<>();
        List<Long> list = Arrays.asList(ids.split(",")).stream().map(Long::parseLong).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(list)){
            result.setData(herolandTopicGroupPartService.deleteDepartment(list));
        }
        result.setData(true);
        return result;
    }

//    /**
//     * 教师能看到的当前的校际赛事
//     * 教师选择
//     * @param
//     * @return
//     */
//    @RequestMapping("/qualifiedStudent")
//    public ResponseBody<Boolean> qualifiedTopic(QualifiedTopicForSchoolRequest request){
//        ResponseBody<Boolean> result = new ResponseBody<>();
//
//        result.setData(true);
//        return result;
//    }

    /**
     * 学生能看到的校际赛的题目
     * @param
     * @return
     */
    @RequestMapping("/questionsAvailableForS")
    public ResponseBody<TopicQuestionsForSDto> questionsAvailableForS(@RequestBody TopicQuestionsForSRequest request){
        ResponseBody<TopicQuestionsForSDto> result = new ResponseBody<>();
        TopicQuestionsForSDto topicQuestionsForSDto = heroLandQuestionService.questionsAvailableForS(request);
        result.setData(topicQuestionsForSDto);
        return result;
    }

    /**
     * 获取某校际赛的科目
     * @param
     * @return
     */
    @RequestMapping("/courseAvailableForS")
    public ResponseBody<List<HerolandCourseSimpleDto>> courseAvailableForS(@RequestBody TopicSCourseForSRequest request){
        ResponseBody<List<HerolandCourseSimpleDto>> result = new ResponseBody<>();
        List<HerolandCourseSimpleDto> herolandCourseSimpleDtos = heroLandQuestionService.courseAvailableForS(request);
        result.setData(herolandCourseSimpleDtos);
        return result;
    }

    /**
     * 根据当前时间是推荐报名世界赛还是弹报过名且即将要比赛的世界赛
     * action = REGISTER 表示推荐报名的比赛
     * action = BATTLE 表示弹出已经报过名的比赛
     * @param
     * @return
     */
    @RequestMapping("/topicWForStudent")
    public ResponseBody<HeroLandTopicForWDto> topicWForStudent(@RequestBody TopicWForStudentRequest request){
        ResponseBody<HeroLandTopicForWDto> result = new ResponseBody<>();
        HeroLandTopicForWDto heroLandTopicForWDto = heroLandQuestionService.topicWForStudent(request);
        result.setData(heroLandTopicForWDto);
        return result;
    }

    /**
     * 用户id最近一次报名的赛事
     * 有可能是过期的，还未开始的或者已经结束的
     * 注：已结束过滤掉
     * @param
     * @return
     */
    @RequestMapping("/topicWForStudentLastJoined")
    public ResponseBody<HeroLandTopicForWDto> topicWForStudentJoined(@RequestBody TopicWForStudentJoinedRequest request){
        ResponseBody<HeroLandTopicForWDto> result = new ResponseBody<>();
        HeroLandTopicForWDto heroLandTopicForWDto = heroLandQuestionService.topicWForStudentLastJoined(request);
        result.setData(heroLandTopicForWDto);
        return result;
    }

    private void roleCheck(HttpServletRequest servletRequest, int roleType){
        PlatformSysUserDP data = platformSsoUserServiceFacade.queryCurrent(CookieUtils.getSessionId(servletRequest)).getData();
        if (data == null){
            ResponseBodyWrapper.failException(ErrMsgEnum.PLEASE_LOGIN.getErrorMessage());
        }
        //4 代表超管
        if (!Objects.equals(data.getType(), roleType)){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ROLE_LIMIT.getErrorMessage());
        }
    }

}
