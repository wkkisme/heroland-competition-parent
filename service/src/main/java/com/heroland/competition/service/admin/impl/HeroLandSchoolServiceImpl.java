package com.heroland.competition.service.admin.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.heroland.competition.common.constants.AdminFieldEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandSchoolMapper;
import com.heroland.competition.dal.pojo.HerolandSchool;
import com.heroland.competition.dal.pojo.basic.HerolandBasicData;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandSchoolDP;
import com.heroland.competition.domain.dto.HerolandSchoolDto;
import com.heroland.competition.domain.dto.HerolandSchoolSimpleDto;
import com.heroland.competition.domain.request.HerolandSchoolPageRequest;
import com.heroland.competition.domain.request.HerolandSchoolRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandSchoolService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Component
@Slf4j
public class HeroLandSchoolServiceImpl implements HeroLandSchoolService {

    @Resource
    private HerolandSchoolMapper herolandSchoolMapper;

    @Resource
    private HeroLandAdminService heroLandAdminService;

    @Override
    @Transactional
    public Boolean addNode(HerolandSchoolDP schoolDP) {
        HerolandBasicDataDP dataDP = convertToHerolandBasicDataDP(schoolDP);
        HerolandBasicData basicData = heroLandAdminService.addDict(dataDP);
        if (Objects.isNull(basicData) || NumberUtils.nullOrZeroLong(basicData.getId())){
            ResponseBodyWrapper.failSysException();
        }
        schoolDP = schoolDP.checkAndBuildBeforeCreate();
        HerolandSchool herolandSchool = new HerolandSchool();
        //如果节点已经添加过则可以修改名称
        List<HerolandSchool> byParentAndKey = herolandSchoolMapper.getByParentAndKey(schoolDP.getParentKey(), basicData.getDictKey());
        //说明已经添加过
        if (!CollectionUtils.isEmpty(byParentAndKey)){
            //更新 主要是更新名称 和字典数据保持一致
            HerolandSchool hasCreate = byParentAndKey.get(0);
            hasCreate.setName(schoolDP.getName());
            return herolandSchoolMapper.updateByPrimaryKeySelective(hasCreate) > 0;
        }
        herolandSchool.setKey(basicData.getDictKey());
        herolandSchool.setCode(schoolDP.getCode());
        herolandSchool.setName(schoolDP.getName());
        herolandSchool.setParentKey(schoolDP.getParentKey());
        herolandSchool.setHasParent(schoolDP.getHasParent());
        herolandSchool.setLinkedMan(schoolDP.getLinkedMan());
        herolandSchool.setMobile(schoolDP.getMobile());
        herolandSchool.setEmail(schoolDP.getEmail());
        herolandSchool.setAxis(schoolDP.getAxis());
        herolandSchool.setDesc(schoolDP.getDesc());
        //如果父节点在school表中没有则也需要增加上
        if (Objects.equals(AdminFieldEnum.SCHOOL.getCode(),schoolDP.getCode())){
            HerolandSchool area = herolandSchoolMapper.getByKey(schoolDP.getParentKey());
            if (Objects.isNull(area)){
                List<HerolandBasicDataDP> areaData = heroLandAdminService.getDictInfoByKeys(Lists.newArrayList(schoolDP.getParentKey()));
                if (CollectionUtils.isEmpty(areaData)){
                    ResponseBodyWrapper.failException("无地区数据，请联系管理人员");
                }
                HerolandSchool areaNode = new HerolandSchool();
                areaNode.setKey(areaData.get(0).getDictKey());
                areaNode.setCode(areaData.get(0).getCode());
                areaNode.setName(areaData.get(0).getDictValue());
                areaNode.setHasParent(false);
                herolandSchoolMapper.insertSelective(areaNode);
            }
        }
        return herolandSchoolMapper.insertSelective(herolandSchool) > 0;

    }

    @Override
    @Transactional
    public Boolean updateNode(HerolandSchoolDP schoolDP) {
        AssertUtils.notNull(schoolDP.getId());
        HerolandSchool herolandSchool = new HerolandSchool();
        herolandSchool.setId(schoolDP.getId());
        herolandSchool.setName(schoolDP.getName());
        herolandSchool.setLinkedMan(schoolDP.getLinkedMan());
        herolandSchool.setMobile(schoolDP.getMobile());
        herolandSchool.setEmail(schoolDP.getEmail());
        herolandSchool.setAxis(schoolDP.getAxis());
        herolandSchool.setDesc(schoolDP.getDesc());
        herolandSchoolMapper.updateByPrimaryKeySelective(herolandSchool);
        HerolandSchool school = herolandSchoolMapper.selectByPrimaryKey(herolandSchool.getId());
        List<HerolandBasicDataDP> data = heroLandAdminService.getDictInfoByKeys(Lists.newArrayList(school.getKey()));
        if (CollectionUtils.isEmpty(data)){
            //如果没有这个数据，理论上应该向字典库里新加一个
            HerolandBasicDataDP insert = new HerolandBasicDataDP();
            insert.setCode(school.getCode());
            insert.setDictKey(school.getKey());
            insert.setDictValue(schoolDP.getName());
            insert.setBizNo(schoolDP.getBizNo());
            insert.setBizI18N(schoolDP.getBizI18N());
            heroLandAdminService.addDict(insert);
            return true;
        }
        HerolandBasicDataDP dataDP = data.get(0);
        dataDP.setBizI18N(schoolDP.getBizI18N());
        dataDP.setDictValue(schoolDP.getName());
        dataDP.setBizNo(schoolDP.getBizNo());
        heroLandAdminService.editDict(dataDP);
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteNode(String key) {
        if (StringUtils.isBlank(key)){
            return true;
        }
        HerolandSchool herolandSchool = herolandSchoolMapper.getByKey(key);
        if (Objects.isNull(herolandSchool)){
            return true;
        }
        //如果是非叶子节点，则下面的所有子节点都需要删除
        List<HerolandSchool> list = herolandSchoolMapper.getByParent(herolandSchool.getKey());
        herolandSchoolMapper.deleteByPrimaryKey(herolandSchool.getId());
        heroLandAdminService.deleteDict(Lists.newArrayList(herolandSchool.getKey()));
        if (CollectionUtils.isEmpty(list)){
           return true;
        }
        List<HerolandSchool> children = Lists.newArrayList();
        getChildren(herolandSchool.getKey(), children);
        List<Long> ids = children.stream().map(HerolandSchool::getId).distinct().collect(Collectors.toList());
        herolandSchoolMapper.batchDeleteByIds(ids);
        List<String> keys = children.stream().map(HerolandSchool::getKey).distinct().collect(Collectors.toList());
        heroLandAdminService.deleteDict(keys);
        return true;

    }

    @Override
    public List<HerolandSchoolDto> queryChild(HerolandSchoolRequest qo) {
        List<HerolandSchoolDto> result = new ArrayList<>();
        //如果为空查询所有的地区
        List<HerolandSchool> herolandSchool = Lists.newArrayList();
        if (StringUtils.isBlank(qo.getParentKey())){
            herolandSchool = herolandSchoolMapper.getByKeyAndCode(null,AdminFieldEnum.AREA.getCode());
        }else {
            herolandSchool = herolandSchoolMapper.getByParentAndName(qo.getParentKey(),null);
        }
        if (CollectionUtils.isEmpty(herolandSchool)){
            return result;
        }
        result = BeanCopyUtils.copyArrayByJSON(herolandSchool, HerolandSchoolDto.class);
        //如果是查询的地区，则不再查询他的子节点
        List<String> keys = herolandSchool.stream().map(HerolandSchool::getKey).distinct().collect(Collectors.toList());
        //如果是非叶子节点，则下面的所有子节点都需要删除
        return result;
    }

    @Override
    public PageResponse<HerolandSchoolSimpleDto> pageQuery(HerolandSchoolPageRequest request) {
        List<HerolandSchoolSimpleDto> result = new ArrayList<>();
        PageResponse<HerolandSchoolSimpleDto> pageResult = new PageResponse<>();
        Page<HerolandSchool> dataPage = null;
        if (StringUtils.isBlank(request.getParentKey())){
            dataPage= PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                    () -> herolandSchoolMapper.getByCodeAndName(AdminFieldEnum.SCHOOL.getCode(),request.getName()));
        }else {
            dataPage= PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                    () -> herolandSchoolMapper.getByParentAndName(request.getParentKey(),request.getName()));
        }

        if (CollectionUtils.isEmpty(dataPage)){
            return  pageResult;
        }

        List<String> allKeys = Lists.newArrayList();
        dataPage.getResult().stream().forEach(e -> allKeys.add(e.getKey()));
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(allKeys);
        if (CollectionUtils.isEmpty(dictInfoByKeys)){
            log.error("query DictInfoByKeys error", JSON.toJSONString(allKeys));
            return pageResult;
        }
        //如果是学校列表则需要返回地区的key和name
        Map<String, HerolandBasicDataDP> areaMap = Maps.newHashMap();
        if (AdminFieldEnum.SCHOOL.getCode().equalsIgnoreCase(dataPage.getResult().get(0).getCode())){
            Set<String> parentKeys = Sets.newHashSet();
            dataPage.getResult().stream().forEach(e -> parentKeys.add(e.getParentKey()));
            List<HerolandBasicDataDP> area = heroLandAdminService.getDictInfoByKeys(new ArrayList<>(parentKeys));
            if (!CollectionUtils.isEmpty(area)){
                area.stream().forEach(e -> areaMap.put(e.getDictKey(),e));
            }
        }

        Map<String, List<HerolandBasicDataDP>> basic = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        for (HerolandSchool dto : dataPage.getResult()){
            HerolandSchoolSimpleDto simpleDto = new HerolandSchoolSimpleDto();
            simpleDto.setName(dto.getName());
            simpleDto.setCode(dto.getCode());
            simpleDto.setId(dto.getId());
            simpleDto.setKey(dto.getKey());
            simpleDto.setLinkedMan(dto.getLinkedMan());
            simpleDto.setMobile(dto.getMobile());
            simpleDto.setEmail(dto.getEmail());
            simpleDto.setAxis(dto.getAxis());
            simpleDto.setDesc(dto.getDesc());
            if (basic.keySet().contains(dto.getKey())){
                simpleDto.setBizNo(basic.get(dto.getKey()).get(0).getBizNo());
                simpleDto.setBizI18N(basic.get(dto.getKey()).get(0).getBizI18N());
            }
            if (AdminFieldEnum.SCHOOL.getCode().equalsIgnoreCase(dataPage.getResult().get(0).getCode()) && areaMap.containsKey(dto.getParentKey())){
                simpleDto.setAreaName(areaMap.get(dto.getParentKey()).getDictValue());
                simpleDto.setAreaKey(areaMap.get(dto.getParentKey()).getDictKey());
            }
            result.add(simpleDto);
        }
        pageResult.setItems(result);
        pageResult.setPageSize(dataPage.getPageSize());
        pageResult.setPage(dataPage.getPageNum());
        pageResult.setTotal((int) dataPage.getTotal());
        return pageResult;
    }

    private void getChildren(String parent, List<HerolandSchool> children){
        List<HerolandSchool> list = herolandSchoolMapper.getByParent(parent);
        if (!CollectionUtils.isEmpty(list)){
            children.addAll(list);
            for (HerolandSchool sh : list){
                getChildren(sh.getKey(), children);
            }
        }
    }

    private HerolandBasicDataDP convertToHerolandBasicDataDP(HerolandSchoolDP schoolDP){
        HerolandBasicDataDP dataDP = new HerolandBasicDataDP();
        dataDP.setCode(schoolDP.getCode());
        dataDP.setDictValue(schoolDP.getName());
        dataDP.setBizI18N(schoolDP.getBizI18N());
        dataDP.setBizNo(schoolDP.getBizNo());
        return dataDP;
    }


}
