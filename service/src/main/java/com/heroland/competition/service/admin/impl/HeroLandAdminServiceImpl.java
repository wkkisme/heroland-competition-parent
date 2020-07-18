package com.heroland.competition.service.admin.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.expception.AppSystemException;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.contants.AdminFieldEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandBasicDataMapper;
import com.heroland.competition.dal.mapper.HerolandLocationMapper;
import com.heroland.competition.dal.pojo.basic.HerolandBasicData;
import com.heroland.competition.dal.pojo.basic.HerolandLocation;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandLocationDP;
import com.heroland.competition.domain.dto.HerolandSchoolSimpleDto;
import com.heroland.competition.domain.qo.HerolandBasicDataQO;
import com.heroland.competition.domain.qo.HerolandLocationDataQO;
import com.heroland.competition.domain.request.HerolandDataPageRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 */
@Component
@Slf4j
public class HeroLandAdminServiceImpl implements HeroLandAdminService {

    @Resource
    private HerolandBasicDataMapper herolandBasicDataMapper;

    @Resource
    private HerolandLocationMapper herolandLocationMapper;

    @Override
    public HerolandBasicData addDict(HerolandBasicDataDP dp) {
        try {
            // 针对同一个bizNo和code 不能重复添加
            dp = dp.checkAndBuildBeforeCreate();
            if (StringUtils.isNotBlank(dp.getBizNo())){
                List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCodeAndBizNo(dp.getCode(), dp.getBizNo());
                if (!CollectionUtils.isEmpty(herolandBasicData)){
                    ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_PARAM.getErrorMessage());
                }
            }
            HerolandBasicData basicData = BeanUtil.insertConversion(dp, new HerolandBasicData());
            herolandBasicDataMapper.insertSelective(basicData);
            return basicData;
        } catch (Exception e) {
            log.error("addDict error, [{}]", JSON.toJSONString(dp));
            if (e instanceof AppSystemException){
                ResponseBodyWrapper.failException(e.getMessage());
            }else {
                ResponseBodyWrapper.failSysException();
            }
        }
        return null;
    }

    @Override
    public ResponseBody<Boolean> editDict(HerolandBasicDataDP dp) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        if (StringUtils.isNotBlank(dp.getBizNo())){
            HerolandBasicData basicData = herolandBasicDataMapper.selectByPrimaryKey(dp.getId());
            List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCodeAndBizNo(basicData.getCode(), dp.getBizNo());
            if (!CollectionUtils.isEmpty(herolandBasicData) && herolandBasicData.get(0).getId() != dp.getId()){
                ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_PARAM.getErrorMessage());
            }
        }
        try {
            result.setData(herolandBasicDataMapper.updateByPrimaryKeySelective(BeanUtil.updateConversion(dp.checkAndBuildBeforeUpdate(), new HerolandBasicData())) > 0);
        } catch (Exception e) {
            log.error("editDict error", e);
            if (e instanceof AppSystemException){
                ResponseBodyWrapper.failException(e.getMessage());
            }else {
                ResponseBodyWrapper.failSysException();
            }
        }
        return result;
    }

    @Override
    public Boolean deleteDict(List<String> keys) {
       if(!CollectionUtils.isEmpty(keys)){
          return  herolandBasicDataMapper.deleteByDictKey(keys) > 0;
        }
        return true;
    }

    @Override
    public ResponseBody<List<HerolandBasicDataDP>> pageQueryDict(HerolandBasicDataQO qo) {
//        HeroLandBasicDataExample example = new HeroLandBasicDataExample();
//        MybatisCriteriaConditionUtil.createExample(example.createCriteria(), qo);
//        Page<HerolandBasicData> dataPage= PageHelper.startPage(qo.getPageNum(), qo.getPageSize(), true).doSelectPage(
//                () -> herolandBasicDataMapper.selectByExample(example));
        ResponseBody<List<HerolandBasicDataDP>> result = new ResponseBody();
        List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCode(qo.getCode());
        List<HerolandBasicDataDP> dpList = herolandBasicData.stream().map(this::convertToDP).collect(Collectors.toList());
        result.setData(dpList);
        return result;
    }

    @Override
    public ResponseBody<List<HerolandLocationDP>> listQueryLocale(HerolandLocationDataQO qo) {
        ResponseBody<List<HerolandLocationDP>> result = new ResponseBody();
        List<HerolandLocation> locationList = herolandLocationMapper.getLocationByKey(qo.getDictKey(), qo.getCode());
//        List<String> dictKeys = Lists.newArrayList();
//        locationList.stream().forEach(e -> {
//           dictKeys.add(e.getArea());
//            dictKeys.add(e.getSchool());
//            dictKeys.add(e.getGrade());
//            dictKeys.add(e.getClas());
//        });
//        List<String> validDictKeys = dictKeys.stream().filter(e -> StringUtils.isNotBlank(e)).collect(Collectors.toList());
//        ResponseBody<List<HerolandBasicDataDP>> dictInfoByKeys = getDictInfoByKeys(validDictKeys);
//        Map<String, List<HerolandBasicDataDP>> keysMap = dictInfoByKeys.getData().stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        List<HerolandLocationDP> locationDPS = Lists.newArrayList();
        locationList.stream().forEach(e -> {
            HerolandLocationDP locationDP = new HerolandLocationDP();
            locationDP.setArea(e.getArea());
            locationDP.setGrade(e.getGrade());
            locationDP.setSchool(e.getSchool());
            locationDP.setClas(e.getClas());
//            if (keysMap.containsKey(e.getArea())){
//                locationDP.setAreaName(keysMap.get(e.getArea()).get(0).getDictValue());
//            }
//            if (keysMap.containsKey(e.getGrade())){
//                locationDP.setGradeName(keysMap.get(e.getGrade()).get(0).getDictValue());
//            }
//            if (keysMap.containsKey(e.getClas())){
//                locationDP.setClasName(keysMap.get(e.getClas()).get(0).getDictValue());
//            }
//            if (keysMap.containsKey(e.getSchool())){
//                locationDP.setSchoolName(keysMap.get(e.getSchool()).get(0).getDictValue());
//            }
            locationDPS.add(locationDP);
        });
        result.setData(locationDPS);
        return result;
    }

    @Override
    public ResponseBody<List<HerolandBasicDataDP>> listValidLoation(String code) {
        ResponseBody<List<HerolandBasicDataDP>> result = new ResponseBody();
        List<HerolandBasicData> herolandBasicData = Lists.newArrayList();
        List<HerolandLocation> location = herolandLocationMapper.getDistinctLocationByCode(code);
        List<String> keys = Lists.newArrayList();
        if (AdminFieldEnum.AREA.getCode().equals(code)){
            keys = location.stream().map(HerolandLocation::getArea).collect(Collectors.toList());
        }else if (AdminFieldEnum.SCHOOL.getCode().equals(code)){
            keys = location.stream().map(HerolandLocation::getSchool).collect(Collectors.toList());
        }else if(AdminFieldEnum.GRADE.getCode().equals(code)){
            keys = location.stream().map(HerolandLocation::getGrade).collect(Collectors.toList());
        }else if(AdminFieldEnum.CLASS.getCode().equals(code)){
            keys = location.stream().map(HerolandLocation::getClas).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(keys)){
            herolandBasicData = herolandBasicDataMapper.selectByDictKeys(keys);
        }
        List<HerolandBasicDataDP> dpList = herolandBasicData.stream().map(this::convertToDP).collect(Collectors.toList());
        result.setData(dpList);
        return result;
    }

    @Override
    public PageResponse<HerolandBasicDataDP> pageDataByCode(HerolandDataPageRequest request) {
        List<HerolandBasicDataDP> result = new ArrayList<>();
        PageResponse<HerolandBasicDataDP> pageResult = new PageResponse<>();
        if (StringUtils.isBlank(request.getCode())){
            return pageResult;
        }
        List<HerolandBasicData> herolandBasicData = Lists.newArrayList();
        Page<HerolandBasicData> page = new  Page<>();
        if (Boolean.TRUE.equals(request.getNeedPage())){
            page = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                    () -> herolandBasicDataMapper.selectByCode(request.getCode()));
            herolandBasicData = page.getResult();
        }else {
            herolandBasicData = herolandBasicDataMapper.selectByCode(request.getCode());
            page.addAll(herolandBasicData);
            page.setPageNum(1);
            page.setPageSize(herolandBasicData.size());
            page.setTotal(herolandBasicData.size());
        }
        result = BeanCopyUtils.copyArrayByJSON(herolandBasicData, HerolandBasicDataDP.class);
        pageResult.setItems(result);
        pageResult.setPageSize(page.getPageSize());
        pageResult.setPage(page.getPageNum());
        pageResult.setTotal((int) page.getTotal());
        return pageResult;
    }

    @Override
    public ResponseBody<Boolean> addClass(HerolandLocationDP dp) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        try {
            result.setData(herolandLocationMapper.insert(BeanUtil.insertConversion(dp.check(), new HerolandLocation())) > 0);
        } catch (Exception e) {
            log.error("add error, [{}]", JSON.toJSONString(dp));
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }

    @Override
    public ResponseBody<HerolandBasicDataDP> getDictInfoById(Long id) {
        HerolandBasicData herolandBasicData = null;
        if (NumberUtils.nullOrZeroLong(id)){
            ResponseBodyWrapper.failSysException();
        }
        try {
            herolandBasicData = herolandBasicDataMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successWrapper(herolandBasicData, HerolandBasicDataDP.class);
    }

    @Override
    public List<HerolandBasicDataDP> getDictInfoByKeys(List<String> keys) {
        List<HerolandBasicData> herolandBasicData = Lists.newArrayList();
        List<HerolandBasicDataDP> list = Lists.newArrayList();
        if (CollectionUtils.isEmpty(keys)){
            return list;
        }
        try {
            herolandBasicData = herolandBasicDataMapper.selectByDictKeys(keys);
            list = BeanCopyUtils.copyArrayByJSON(herolandBasicData,HerolandBasicDataDP.class);
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return list;
    }

    private HerolandBasicDataDP convertToDP(HerolandBasicData data){
        HerolandBasicDataDP herolandBasicDataDP = new HerolandBasicDataDP();
        herolandBasicDataDP.setId(data.getId());
        herolandBasicDataDP.setCode(data.getCode());
        herolandBasicDataDP.setField(data.getField());
        herolandBasicDataDP.setChName(data.getChName());
        herolandBasicDataDP.setDictKey(data.getDictKey());
        herolandBasicDataDP.setDictValue(data.getDictValue());
        herolandBasicDataDP.setBizNo(data.getBizNo());
        herolandBasicDataDP.setBizI18N(data.getBizI18N());
        return herolandBasicDataDP;
    }
}
