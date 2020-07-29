package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandClassDP;
import com.heroland.competition.domain.qo.HeroLandClassManageQO;
import com.heroland.competition.service.classmanage.HeroLandClassService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 班级管理
 * @author  wangkai
 * @date 2020-07-29
 */
@RestController
@RequestMapping("/heroland/class")
public class HeroLandClassController {

    @Resource
    private HeroLandClassService heroLandClassService;

    /**
     * 新增班级
     * @param dp
     * @return
     */
    @RequestMapping(value = "/addClass")
    public ResponseBody<Boolean> addClass(@RequestBody HeroLandClassDP dp) {
        return heroLandClassService.addClass(dp);
    }

    /**
     * 编辑班级
     * @param dp
     * @return
     */
    @RequestMapping(value = "/updateClass")
    public ResponseBody<Boolean> updateClass(@RequestBody HeroLandClassDP dp) {
        return heroLandClassService.updateClass(dp);
    }

    /**
     * 删除班级
     * @param dp
     * @return
     */
    @RequestMapping(value = "/delClass")
    public ResponseBody<Boolean> delClass(@RequestBody HeroLandClassManageQO dp) {
        return heroLandClassService.deleteClassList(dp);
    }

    /**
     * 查询班级列表
     * @param dp
     * @return
     */
    @RequestMapping(value = "/getClassList")
    public ResponseBody<List<HeroLandClassDP>> getClass(@RequestBody HeroLandClassManageQO dp) {
        return heroLandClassService.getClassList(dp);
    }

}
