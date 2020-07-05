package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.common.contants.AdminFieldEnum;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandLocationDP;
import com.heroland.competition.domain.qo.HeroLandClassQO;
import com.heroland.competition.domain.qo.HerolandBasicDataQO;
import com.heroland.competition.service.admin.HeroLandAdminService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 字典数据
 */
@RestController
@RequestMapping("/heroland/admin")
public class HeroLandAdminDataController {

    @Resource
    private HeroLandAdminService heroLandAdminService;

    /**
     * 增加字典数据
     * @param qo
     * @return
     */
    @RequestMapping(value = "/addDict", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> addDict(HeroLandClassQO qo) {
        //如果是学校的
        if (!Objects.equals(AdminFieldEnum.CLASS.getCode(),qo.getCode())){
            HerolandBasicDataDP herolandBasicDataDP = new HerolandBasicDataDP();
            herolandBasicDataDP.setDictValue(qo.getDictValue());
            herolandBasicDataDP.setDictKey(qo.getDictKey());
            herolandBasicDataDP.setField(qo.getField());
            herolandBasicDataDP.setCode(qo.getCode());
            return heroLandAdminService.addDict(herolandBasicDataDP);
        }
        HerolandLocationDP locationDP = new HerolandLocationDP();
        locationDP.setSchool(qo.getSchool());
        locationDP.setArea(qo.getArea());
        locationDP.setGrade(qo.getGrade());
        locationDP.setClas(qo.getClas());
        return heroLandAdminService.addClass(locationDP);
    }

    /**
     * 编辑字典数据
     * @param dp
     * @return
     */
    @RequestMapping(value = "/editDict", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> editDict(HerolandBasicDataDP dp) {
        return heroLandAdminService.editDict(dp);
    }

    /**
     * 删除某一字典数据
     * @param dp
     * @return
     */
    @RequestMapping(value = "/deleteDict", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> deleteDict(HerolandBasicDataDP dp) {
        return heroLandAdminService.deleteDict(dp);
    }

    /**
     * 查询某一字典组下面的所有字典详情数据
     * 比如地区管路下的所有地区名称
     * @param qo
     * @return
     */
    @RequestMapping(value = "/pageQueryDict", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandBasicDataDP>> pageQueryDict(HerolandBasicDataQO qo) {
        return heroLandAdminService.pageQueryDict(qo);
    }

    /**
     * 查看某一空间物理结构下的层级结构
     * 比如某一地区下的学校，年级，班级
     * @param qo
     * @return
     */
    @RequestMapping(value = "/listQueryLocale", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandLocationDP>> listQueryLocale(HerolandBasicDataQO qo) {
        return heroLandAdminService.listQueryLocale(qo);
    }

    /**
     * 查看字典数据详情
     * @param qo
     * @return
     */
    @RequestMapping(value = "/getDictInfo", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<HerolandBasicDataDP> getDictInfoById(HerolandBasicDataQO qo) {
        return heroLandAdminService.getDictInfoById(qo);
    }


}
