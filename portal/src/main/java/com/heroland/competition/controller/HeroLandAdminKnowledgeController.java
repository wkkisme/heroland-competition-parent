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
 * @author smjyouzan
 * @date 2020/7/5
 */
@RestController
@RequestMapping("/heroland/knowledge")
public class HeroLandAdminKnowledgeController {

    @Resource
    private HerolandKnowledgeService herolandKnowledgeService;

    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<Boolean> add(HerolandKnowledgeDP dp){
        return herolandKnowledgeService.add(dp);
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<Boolean> edit(HerolandKnowledgeDP dp){
        return herolandKnowledgeService.edit(dp);
    }

    @RequestMapping(value = "/deleteAllByNode", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<Boolean> deleteAllByNode(HerolandKnowledgeQO qo){
        return null;
    }

    @RequestMapping(value = "/deleteOneNode", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<Boolean> deleteOneNode(HerolandKnowledgeQO qo){
        return herolandKnowledgeService.deleteOneNode(qo);
    }

    @RequestMapping(value = "/getById", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<HerolandKnowledgeDP> getById(HerolandKnowledgeQO qo){
        return herolandKnowledgeService.getById(qo);
    }


    @RequestMapping(value = "/pageQuery", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody<List<HerolandKnowledgeDP>> pageQuery(HerolandKnowledgeQO qo){
        return herolandKnowledgeService.pageQuery(qo);
    }
}
