package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HerolandChapterDP;
import com.heroland.competition.domain.qo.HerolandChapterQO;
import com.heroland.competition.service.admin.HeroLandChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 章节管理
 */
@RestController
@RequestMapping("/heroland/chapter")
public class HeroLandAdminChapterController {

    @Resource
    private HeroLandChapterService heroLandChapterService;

    /**
     * 增加章节
     * @param dp
     * @return
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> add(HerolandChapterDP dp) {
        return heroLandChapterService.add(dp);
    }

    /**
     * 编辑
     * @param dp
     * @return
     */
    @RequestMapping(value = "/edit", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> edit(HerolandChapterDP dp) {
        return heroLandChapterService.edit(dp);
    }

    /**
     * 删除
     * @param dp
     * @return
     */
    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> delete(HerolandChapterDP dp) {

        return heroLandChapterService.delete(dp);
    }

    /**
     * 查看详情
     * @param qo
     * @return
     */
    @RequestMapping(value = "/getById", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<HerolandChapterDP> getById(HerolandChapterQO qo) {
        return heroLandChapterService.getById(qo);
    }

    /**
     * 分页查询
     * @param qo
     * @return
     */
    @RequestMapping(value = "/pageQuery", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandChapterDP>> pageQuery(HerolandChapterQO qo) {
       return heroLandChapterService.pageQuery(qo);
    }


}
