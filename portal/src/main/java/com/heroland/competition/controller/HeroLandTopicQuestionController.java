package com.heroland.competition.controller;


import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import com.google.common.collect.Lists;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.dp.HerolandTopicGroupPartDP;
import com.heroland.competition.domain.dto.HeroLandQuestionListForTopicDto;
import com.heroland.competition.domain.dto.HeroLandTopicDto;
import com.heroland.competition.domain.dto.HerolandTopicCanSeeDto;
import com.heroland.competition.domain.dto.TopicQuestionsForSDto;
import com.heroland.competition.domain.qo.HerolandTopicCanSeeQO;
import com.heroland.competition.domain.request.*;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.HerolandTopicGroupPartService;
import com.heroland.competition.service.HerolandTopicJoinUserService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private HerolandTopicGroupPartService herolandTopicGroupPartService;

    @Resource
    private HerolandTopicJoinUserService herolandTopicJoinUserService;


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
     * 校际赛的赛事列表
     * todo biz逻辑未完善，需要具体页面具体对待
     * @param request
     * @return
     */
    @RequestMapping("/queryTopicsForS")
    public ResponseBody<HerolandTopicCanSeeDto> getTopicsForS(@RequestBody HerolandTopicCanSeeQO request){
        ResponseBody<HerolandTopicCanSeeDto> result = new ResponseBody<>();
        HerolandTopicCanSeeDto herolandTopicCanSeeDto = herolandTopicJoinUserService.canOperableTopics(request);
        result.setData(herolandTopicCanSeeDto);
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
        HerolandTopicCanSeeDto herolandTopicCanSeeDto = herolandTopicJoinUserService.canOperableTopics(request);
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
    public ResponseBody<Boolean> addDepartmentForTopic(@RequestBody HeroLandTopicAddDepartmentRequest request){
        ResponseBody<Boolean> result = new ResponseBody<>();
        if (CollectionUtils.isEmpty(request.getSchoolCourses())){
            List<HerolandTopicGroupPartDP> list = Lists.newArrayList();
            request.getSchoolCourses().stream().forEach(e -> {
                HerolandTopicGroupPartDP dp = BeanCopyUtils.copyByJSON(request, HerolandTopicGroupPartDP.class);
                dp.setTopicId(request.getTopicId());
                list.add(dp);
            });
            result.setData(herolandTopicGroupPartService.addBatchDepartment(list));
        }
        result.setData(false);
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

    /**
     * 教师能看到的当前的校际赛事
     * 教师选择
     * @param
     * @return
     */
    @RequestMapping("/qualifiedStudent")
    public ResponseBody<Boolean> qualifiedTopic(QualifiedTopicForSchoolRequest request){
        ResponseBody<Boolean> result = new ResponseBody<>();

        result.setData(true);
        return result;
    }

    /**
     * 学生能看到的校际赛的题目
     * 教师选择
     * @param
     * @return
     */
    @RequestMapping("/questionsAvailableForS")
    public ResponseBody<TopicQuestionsForSDto> questionsAvailableForS(TopicQuestionsForSRequest request){
        ResponseBody<TopicQuestionsForSDto> result = new ResponseBody<>();
        TopicQuestionsForSDto topicQuestionsForSDto = heroLandQuestionService.questionsAvailableForS(request);
        result.setData(topicQuestionsForSDto);
        return result;
    }




}
