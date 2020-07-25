package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HerolandKnowledgeDP;
import com.heroland.competition.domain.dto.HerolandKnowledgeDto;
import com.heroland.competition.domain.qo.HerolandKnowledgeQO;
import com.heroland.competition.domain.request.HerolandKnowledgeAddRequest;
import com.heroland.competition.service.admin.HerolandKnowledgeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 知识点管理
 */
@RestController
@RequestMapping("/heroland/knowledge")
public class HeroLandAdminKnowledgeController {

    @Resource
    private HerolandKnowledgeService herolandKnowledgeService;

    /**
     * 增加|编辑知识点
     * @param request
     * @return
     */
    @RequestMapping(value = "/addAndEdit", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<Boolean> add(@RequestBody HerolandKnowledgeAddRequest request){
        ResponseBody<Boolean> result = new ResponseBody<>();
        HerolandKnowledgeDP herolandKnowledgeDP = new HerolandKnowledgeDP();
        herolandKnowledgeDP.setDiff(request.getDiff());
        herolandKnowledgeDP.setKnowledge(request.getKnowledge());
        herolandKnowledgeDP.setCourse(request.getCourse());
        herolandKnowledgeDP.setGrade(request.getGrade());
        herolandKnowledgeDP.setId(request.getId());
        herolandKnowledgeService.edit(herolandKnowledgeDP);
        return result;
    }



    /**
     * 删除知识点
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<Boolean> delete(@RequestParam("id") Long id){
        return herolandKnowledgeService.deleteOneNode(id);
    }



    /**
     * 查看单个知识点
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<HerolandKnowledgeDto> getById(@RequestParam("id") Long id){
        ResponseBody<HerolandKnowledgeDto> result = new ResponseBody<HerolandKnowledgeDto>();
        result.setData(herolandKnowledgeService.getById(id));
        return result;
    }


    /**
     * 分页查询
     * @param qo
     * @return
     */
    @RequestMapping(value = "/pageQuery", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<List<HerolandKnowledgeDto>> pageQuery(@RequestBody HerolandKnowledgeQO qo){
        ResponseBody<List<HerolandKnowledgeDto>> result = new ResponseBody<>();
        PageResponse<HerolandKnowledgeDto> pageResponse = herolandKnowledgeService.pageQuery(qo);
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
