package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HerolandKnowledgeDP;
import com.heroland.competition.domain.qo.HerolandKnowledgeQO;
import com.heroland.competition.domain.request.HerolandKnowledgeAddRequest;
import com.heroland.competition.domain.request.HerolandKnowledgeEditRequest;
import com.heroland.competition.service.admin.HerolandKnowledgeService;
import org.springframework.beans.BeanUtils;
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
     * 增加知识点
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<Boolean> add(@RequestBody HerolandKnowledgeAddRequest request){
        HerolandKnowledgeDP herolandKnowledgeDP = new HerolandKnowledgeDP();
        herolandKnowledgeDP.setDiff(request.getDiff());
        herolandKnowledgeDP.setKnowledge(request.getKnowledge());
        herolandKnowledgeDP.setCourse(request.getCourse());
        herolandKnowledgeDP.setGrade(request.getGrade());
        return herolandKnowledgeService.add(herolandKnowledgeDP);
    }

    /**
     * 编辑知识点
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<Boolean> edit(@RequestBody HerolandKnowledgeEditRequest request){
        HerolandKnowledgeDP herolandKnowledgeDP = new HerolandKnowledgeDP();
        herolandKnowledgeDP.setDiff(request.getDiff());
        herolandKnowledgeDP.setKnowledge(request.getKnowledge());
        herolandKnowledgeDP.setCourse(request.getCourse());
        herolandKnowledgeDP.setGrade(request.getGrade());
        herolandKnowledgeDP.setId(request.getId());
        return herolandKnowledgeService.edit(herolandKnowledgeDP);
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
     * @param qo
     * @return
     */
    @RequestMapping(value = "/getById", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<HerolandKnowledgeDP> getById(@RequestBody HerolandKnowledgeQO qo){
        return herolandKnowledgeService.getById(qo);
    }


    /**
     * 分页查询
     * @param qo
     * @return
     */
    @RequestMapping(value = "/pageQuery", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<List<HerolandKnowledgeDP>> pageQuery(@RequestBody HerolandKnowledgeQO qo){
        return herolandKnowledgeService.pageQuery(qo);
    }
}
