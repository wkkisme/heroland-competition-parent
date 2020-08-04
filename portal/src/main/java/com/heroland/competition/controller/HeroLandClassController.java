package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandClassDP;
import com.heroland.competition.domain.dp.HeroLandUserClassDP;
import com.heroland.competition.domain.qo.HeroLandClassManageQO;
import com.heroland.competition.service.classmanage.HeroLandClassService;
import com.platform.sso.domain.dp.PlatformSysUserClassDP;
import com.platform.sso.domain.qo.PlatformSysUserClassQO;
import com.platform.sso.facade.PlatformSsoUserClassServiceFacade;
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

    @Resource
    private PlatformSsoUserClassServiceFacade platformSsoUserClassServiceFacade;
    /**
     * 新增班级
     * @param dp
     * @return
     */
    @RequestMapping(value = "/addClassUser")
    public ResponseBody<Boolean> addClass(@RequestBody PlatformSysUserClassDP dp) {
        return platformSsoUserClassServiceFacade.save(dp);
    }

    /**
     * 编辑班级
     * @param dp
     * @return
     */
    @RequestMapping(value = "/updateClass")
    public ResponseBody<Boolean> updateClass(@RequestBody PlatformSysUserClassDP dp) {
        return platformSsoUserClassServiceFacade.update(dp);
    }

    /**
     * 删除班级
     * @param dp
     * @return
     */
    @RequestMapping(value = "/delClass")
    public ResponseBody<Boolean> delClass(@RequestBody PlatformSysUserClassDP dp) {
        return platformSsoUserClassServiceFacade.delete(dp.getId());
    }

    /**
     * 查询班级列表
     * @param dp
     * @return
     */
    @RequestMapping(value = "/getClassList")
    public ResponseBody<List<PlatformSysUserClassDP>> getClassList(@RequestBody PlatformSysUserClassQO dp) {
        return platformSsoUserClassServiceFacade.queryUserClassList(dp);
    }

}