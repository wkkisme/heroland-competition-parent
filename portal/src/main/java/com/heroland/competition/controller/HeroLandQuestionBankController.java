package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.domain.dp.HerolandQuestionBankDP;
import com.heroland.competition.domain.dto.HeroLandQuestionBankListForTopicDto;
import com.heroland.competition.domain.dto.HeroLandQuestionBankSimpleDto;
import com.heroland.competition.domain.request.HerolandQuestionBankListForChapterRequest;
import com.heroland.competition.domain.request.HerolandQuestionBankPageRequest;
import com.heroland.competition.domain.request.HerolandQuestionBankRequest;
import com.heroland.competition.service.order.HerolandPayService;
import com.heroland.competition.service.admin.HeroLandQuestionBankService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 题库
 */
@RestController
@RequestMapping("/heroland/quesBank")
public class HeroLandQuestionBankController {

    @Resource
    private HeroLandQuestionBankService heroLandQuestionBankService;

    @Resource
    private HerolandPayService herolandPayService;

    /**
     * 创建题目
     * @param request
     * @return
     */
    @RequestMapping(value = "/addAndEdit", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> createQuestion(@RequestBody HerolandQuestionBankRequest request) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        HerolandQuestionBankDP bankDP = BeanCopyUtils.copyByJSON(request, HerolandQuestionBankDP.class);
        heroLandQuestionBankService.createQuestion(bankDP);
        result.setData(true);
        return result;
    }

//    /**
//     * 删除
//     * @return
//     */
//    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
//    @org.springframework.web.bind.annotation.ResponseBody
//    public ResponseBody<Boolean> delete(@RequestParam("id") Long id) {
//        ResponseBody<Boolean> result = new ResponseBody<>();
//        result.setData(heroLandQuestionBankService.delete(id));
//        return result;
//    }

//    /**
//     * 查看详情
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/getById", produces = "application/json;charset=UTF-8")
//    @org.springframework.web.bind.annotation.ResponseBody
//    public ResponseBody<HeroLandQuestionBankDto> getById(@RequestParam("id") Long id) {
//        ResponseBody<HeroLandQuestionBankDto> result = new ResponseBody<>();
//        result.setData(heroLandQuestionBankService.getById(id));
//        return result;
//    }

    /**
     * 分页查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/pageQuery", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HeroLandQuestionBankSimpleDto>> pageQuery(@RequestBody HerolandQuestionBankPageRequest request) {

        ResponseBody<List<HeroLandQuestionBankSimpleDto>> result = new ResponseBody<>();
        PageResponse<HeroLandQuestionBankSimpleDto> pageResponse = heroLandQuestionBankService.pageQuery(request);
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
     * 前台获取题目进行分配
     * @param request
     * @return
     */
    @RequestMapping(value = "/question", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HeroLandQuestionBankListForTopicDto>> getQuestionList(@RequestBody HerolandQuestionBankListForChapterRequest request) {

        ResponseBody<List<HeroLandQuestionBankListForTopicDto>> result = new ResponseBody<>();
        PageResponse<HeroLandQuestionBankListForTopicDto> pageResponse = heroLandQuestionBankService.getQuestionList(request);
        result.setData(pageResponse.getItems());
        Pagination pagination = new Pagination();
        pagination.setPageIndex(pageResponse.getPage());
        pagination.setPageSize(pageResponse.getPageSize());
        pagination.setTotalCount(pageResponse.getTotal());
        pagination.setTotalPage(pageResponse.getTotalPages());
        result.setPage(pagination);
        return result;
    }

}
