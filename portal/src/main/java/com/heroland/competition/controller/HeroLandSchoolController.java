package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.AdminFieldEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandSchoolDP;
import com.heroland.competition.domain.dto.HerolandSchoolDto;
import com.heroland.competition.domain.dto.HerolandSchoolSimpleDto;
import com.heroland.competition.domain.request.*;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandSchoolService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学校管理
 */
@RestController
@RequestMapping("/heroland/school")
public class HeroLandSchoolController {


    @Resource
    private HeroLandSchoolService heroLandSchoolService;

    @Resource
    private HeroLandAdminService heroLandAdminService;

    /**
     * 前台获取学校下拉框
     * 地区--学校--年级--班级
     * @param qo
     * @return
     */
    @RequestMapping(value = "/listQueryLocale", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandSchoolDto>> listQueryLocale(@RequestBody HerolandSchoolRequest qo) {
        ResponseBody<List<HerolandSchoolDto>> result = new ResponseBody<>();
        List<HerolandSchoolDto> herolandSchoolDtos = heroLandSchoolService.queryChild(qo);
        result.setData(herolandSchoolDtos);
        return result;
    }

    /**
     * 后台获取学校|年级|班级分页列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/pageQueryLocale", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandSchoolSimpleDto>> pageQueryLocale(@RequestBody HerolandSchoolPageRequest request) {
        ResponseBody<List<HerolandSchoolSimpleDto>> result = new ResponseBody<>();
        PageResponse<HerolandSchoolSimpleDto> response = heroLandSchoolService.pageQuery(request);
        result.setData(response.getItems());
        Pagination pagination = new Pagination();
        pagination.setPageIndex(response.getPage());
        pagination.setPageSize(response.getPageSize());
        pagination.setTotalCount(response.getTotal());
        pagination.setTotalPage(response.getTotalPages());
        result.setPage(pagination);
        return result;
    }

    /**
     * 编辑学校节点
     * 编辑学校|年级|班级
     * @param request
     * @return
     */
    @RequestMapping(value = "/addNode", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> addNode(@RequestBody HerolandSchoolAddRequest request) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        HerolandSchoolDP herolandSchoolDP = new HerolandSchoolDP();
        herolandSchoolDP.setBizI18N(request.getBizI18N());
        herolandSchoolDP.setCode(request.getCode());
        herolandSchoolDP.setBizNo(request.getBizNo());
        herolandSchoolDP.setName(request.getName());
        herolandSchoolDP.setParentKey(request.getParentKey());
        herolandSchoolDP.setLinkedMan(request.getLinkedMan());
        herolandSchoolDP.setMobile(request.getMobile());
        herolandSchoolDP.setEmail(request.getEmail());
        herolandSchoolDP.setAxis(request.getAxis());
        herolandSchoolDP.setDesc(request.getDesc());
        herolandSchoolDP.setDefaultValue(request.getDefaultValue());
        heroLandSchoolService.addNode(herolandSchoolDP);
        result.setData(true);
        return result;
    }

    /**
     * 删除节点
     * 删除学校|年级|班级
     * 如果是非叶子节点，则所有的子节点多删除
     * @param key
     * @return
     */
    @RequestMapping(value = "/deleteNode", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> deleteNode(String key) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        result.setData(heroLandSchoolService.deleteNode(key));
        return result;
    }

    /**
     * 编辑学校节点
     * 添加学校|年级|班级
     * @param request
     * @return
     */
    @RequestMapping(value = "/editNode", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> editNode(@RequestBody HerolandSchoolEditRequest request) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        HerolandSchoolDP herolandSchoolDP = new HerolandSchoolDP();
        herolandSchoolDP.setBizI18N(request.getBizI18N());
        herolandSchoolDP.setId(request.getId());
        herolandSchoolDP.setBizNo(request.getBizNo());
        herolandSchoolDP.setName(request.getName());
        herolandSchoolDP.setLinkedMan(request.getLinkedMan());
        herolandSchoolDP.setMobile(request.getMobile());
        herolandSchoolDP.setEmail(request.getEmail());
        herolandSchoolDP.setAxis(request.getAxis());
        herolandSchoolDP.setDesc(request.getDesc());
        heroLandSchoolService.updateNode(herolandSchoolDP);
        result.setData(true);
        return result;
    }

    /**
     * 获取固定的地区信息
     * @return
     */
    @RequestMapping(value = "/getArea", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandSchoolSimpleDto>> getArea() {
        ResponseBody<List<HerolandSchoolSimpleDto>> result = new ResponseBody<>();
        List<HerolandSchoolSimpleDto> list = Lists.newArrayList();
        HerolandDataPageRequest request = new HerolandDataPageRequest();
        request.setCode(AdminFieldEnum.AREA.getCode());
        request.setNeedPage(false);
        PageResponse<HerolandBasicDataDP> area = heroLandAdminService.pageDataByCode(request);
        if (!CollectionUtils.isEmpty(area.getItems())){
            area.getItems().stream().forEach(e -> {
                HerolandSchoolSimpleDto simpleDto = new HerolandSchoolSimpleDto();
                simpleDto.setId(e.getId());
                simpleDto.setCode(AdminFieldEnum.AREA.getCode());
                simpleDto.setKey(e.getDictKey());
                simpleDto.setName(e.getDictValue());
                simpleDto.setBizNo(e.getBizNo());
                simpleDto.setBizI18N(e.getBizI18N());
                list.add(simpleDto);
            });
        }
        result.setData(list);
        return result;
    }

}
