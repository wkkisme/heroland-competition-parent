package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HerolandKnowledgeDP;
import com.heroland.competition.domain.qo.HerolandKnowledgeQO;
import com.heroland.competition.service.admin.HerolandKnowledgeService;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * @param dp
     * @return
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<Boolean> add(HerolandKnowledgeDP dp){
        return herolandKnowledgeService.add(dp);
    }

    /**
     * 编辑知识点
     * @param dp
     * @return
     */
    @RequestMapping(value = "/edit", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<Boolean> edit(HerolandKnowledgeDP dp){
        return herolandKnowledgeService.edit(dp);
    }

    /**
     * 删除知识点
     * 并且删除该节点下的所有知识点
     * @param qo
     * @return
     */
    @RequestMapping(value = "/deleteAllByNode", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<Boolean> deleteAllByNode(HerolandKnowledgeQO qo){
        return null;
    }

    /**
     * 删除知识点
     * 将该节点下的所有知识点数结构向上挂
     * @param qo
     * @return
     */
    @RequestMapping(value = "/deleteOneNode", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<Boolean> deleteOneNode(HerolandKnowledgeQO qo){
        return herolandKnowledgeService.deleteOneNode(qo);
    }

    /**
     * 查看单个知识点
     * @param qo
     * @return
     */
    @RequestMapping(value = "/getById", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<HerolandKnowledgeDP> getById(HerolandKnowledgeQO qo){
        return herolandKnowledgeService.getById(qo);
    }


    /**
     * 分页查询
     * @param qo
     * @return
     */
    @RequestMapping(value = "/pageQuery", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<List<HerolandKnowledgeDP>> pageQuery(HerolandKnowledgeQO qo){
        return herolandKnowledgeService.pageQuery(qo);
    }
}
