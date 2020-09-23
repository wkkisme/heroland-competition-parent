package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.domain.dp.HerolandQuestionBankDP;
import com.heroland.competition.domain.dp.HerolandQuestionBankImportDP;
import com.heroland.competition.domain.dto.HeroLandQuestionBankDto;
import com.heroland.competition.domain.dto.HeroLandQuestionBankListForTopicDto;
import com.heroland.competition.domain.dto.HeroLandQuestionBankSimpleDto;
import com.heroland.competition.domain.request.HerolandQuestionBankListForChapterRequest;
import com.heroland.competition.domain.request.HerolandQuestionBankPageRequest;
import com.heroland.competition.domain.request.HerolandQuestionBankRequest;
import com.heroland.competition.service.admin.HeroLandQuestionBankService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 题库
 */
@RestController
@RequestMapping("/heroland/quesBank")
public class HeroLandQuestionBankController {

    @Resource
    private HeroLandQuestionBankService heroLandQuestionBankService;

    /**
     * 后台创建和修改题目
     * @param request
     * @return
     */
    @RequestMapping(value = "/addAndEdit", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> createQuestion(@RequestBody HerolandQuestionBankRequest request) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        HerolandQuestionBankDP bankDP = BeanCopyUtils.copyByJSON(request, HerolandQuestionBankDP.class);
        if (StringUtils.isBlank(request.getQtId())){
            //新增
            heroLandQuestionBankService.createQuestion(bankDP);
        }else {
            // 编辑
            heroLandQuestionBankService.editQuestion(bankDP);
        }
        result.setData(true);
        return result;
    }

    /**
     * 后台根据题目id查询
     * 非主键id
     * @param id
     * @return
     */
    @RequestMapping(value = "/qtId", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<HeroLandQuestionBankDto> createQuestion(@RequestParam("qtId") String id) {
        ResponseBody<HeroLandQuestionBankDto> result = new ResponseBody<>();
        result.setData(heroLandQuestionBankService.getByQtId(id));
        return result;
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> delete(@RequestParam("ids") String ids) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        List<Long> idList = Lists.newArrayList();
        try {
            idList = Arrays.asList(ids.split(",")).stream().map(NumberUtils::parseLong).distinct().collect(Collectors.toList());
        }catch (Exception ex){
            ResponseBodyWrapper.failException("题目id"+ HerolandErrMsgEnum.ERROR_PARSE.getErrorMessage());
        }
        if (idList.size() > 20){
            ResponseBodyWrapper.failException("一次性只能删除20个");
        }
        idList.stream().forEach(e -> {
            heroLandQuestionBankService.deleteById(e);
        });
        result.setData(true);
        return result;
    }

    /**
     * 查看详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<HeroLandQuestionBankDto> getById(@RequestParam("id") Long id) {
        ResponseBody<HeroLandQuestionBankDto> result = new ResponseBody<>();
        result.setData(heroLandQuestionBankService.getById(id));
        return result;
    }

    /**
     * 分页查询
     * 供后台查询的
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
     * 获取题目进行分配
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
    /**
     * 题库导入 入参为文件
     * @param request
     * @return
     */
    @RequestMapping(value = "/importQuestionBank")
    public ResponseBody<Boolean> importQuestionBank(MultipartHttpServletRequest request,@RequestParam("bankType") Integer bankType) throws Exception {


        return heroLandQuestionBankService.importQuestions(request,bankType);

    }

    /**
     * 题库导入 入参为文件
     * @return
     */
    @RequestMapping(value = "/importQuestions")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> importQuestions(@RequestBody List< HerolandQuestionBankImportDP > importDPS) throws Exception {

        heroLandQuestionBankService.importQuestion(importDPS);
        return null;

    }

}
