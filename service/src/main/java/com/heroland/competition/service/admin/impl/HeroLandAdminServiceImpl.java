package com.heroland.competition.service.admin.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.cache.service.RedisService;
import com.anycommon.poi.word.BasicData;
import com.anycommon.poi.word.Question;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.expception.AppSystemException;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constant.RedisConstant;
import com.heroland.competition.common.constants.AdminFieldEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.ExcelFileUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandBasicDataMapper;
import com.heroland.competition.dal.mapper.HerolandLocationMapper;
import com.heroland.competition.dal.pojo.basic.HerolandBasicData;
import com.heroland.competition.dal.pojo.basic.HerolandLocation;
import com.heroland.competition.domain.dp.BasicDataMappingDP;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandLocationDP;
import com.heroland.competition.domain.request.HerolandBasicDataPageRequest;
import com.heroland.competition.domain.request.HerolandDataPageRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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

    @Resource
    private RedisService redisService;

    @Override
    @Transactional
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
            if (!Objects.equals(dp.getCode(), AdminFieldEnum.CLASS.getCode())){
                List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCodesAndValue(Lists.newArrayList(dp.getCode()), dp.getDictValue());
                if (!CollectionUtils.isEmpty(herolandBasicData)){
                    ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_NAME.getErrorMessage());
                }
            }
            HerolandBasicData basicData = BeanUtil.insertConversion(dp, new HerolandBasicData());
            herolandBasicDataMapper.insertSelective(basicData);
            String key = String.format(RedisConstant.ADMIN_KEY, basicData.getDictKey());
            redisService.set(key, basicData, RedisConstant.EXPIRE_SECONDS);
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
    @Transactional
    public ResponseBody<Boolean> editDict(HerolandBasicDataDP dp) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        if (StringUtils.isNotBlank(dp.getBizNo())){
            HerolandBasicData basicData = herolandBasicDataMapper.selectByPrimaryKey(dp.getId());
            List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCodeAndBizNo(basicData.getCode(), dp.getBizNo());
            if (!CollectionUtils.isEmpty(herolandBasicData) && !herolandBasicData.get(0).getId().equals(dp.getId())){
                ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_PARAM.getErrorMessage());
            }
        }
        try {
            List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCodesAndValue(Lists.newArrayList(dp.getCode()), dp.getDictValue());
//            if (!CollectionUtils.isEmpty(herolandBasicData)){
//                ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_NAME.getErrorMessage());
//            }

            HerolandBasicData basicData = BeanUtil.updateConversion(dp.checkAndBuildBeforeUpdate(), new HerolandBasicData());
            result.setData(herolandBasicDataMapper.updateByPrimaryKeySelective(basicData) > 0);
            String key = String.format(RedisConstant.ADMIN_KEY, basicData.getDictKey());
            redisService.del(key);
            redisService.set(key, basicData, RedisConstant.EXPIRE_SECONDS);
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
    @Transactional
    public Boolean deleteDict(List<String> keys) {
       if(!CollectionUtils.isEmpty(keys)){
          herolandBasicDataMapper.deleteByDictKey(keys);
           List<String> redisKeys = keys.stream().map(key -> String.format(RedisConstant.ADMIN_KEY, key)).collect(Collectors.toList());
           redisService.del(redisKeys);
        }
        return true;
    }

    @Override
    public PageResponse<HerolandBasicDataDP> pageQueryDict(HerolandBasicDataPageRequest qo) {
        PageResponse<HerolandBasicDataDP> pageResult = new PageResponse<>();
        Page<HerolandBasicData> dataPage= PageHelper.startPage(qo.getPageIndex(), qo.getPageSize(), true).doSelectPage(
                () -> herolandBasicDataMapper.selectByCodesAndValue(qo.getCode(), qo.getName()));
        List<HerolandBasicDataDP> dpList = dataPage.getResult().stream().map(this::convertToDP).collect(Collectors.toList());
        pageResult.setItems(dpList);
        pageResult.setPageSize(dataPage.getPageSize());
        pageResult.setPage(dataPage.getPageNum());
        pageResult.setTotal((int) dataPage.getTotal());
        return pageResult;
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
        List<String> newKeys = new ArrayList<>(keys);
        List<String> redisKeys = newKeys.stream().map(key -> String.format(RedisConstant.ADMIN_KEY, key)).collect(Collectors.toList());
        List<Object> redisValue = redisService.getKeys(redisKeys);
        redisValue = redisValue.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(redisValue)){
            herolandBasicData = herolandBasicDataMapper.selectByDictKeys(newKeys);
            batchSetRedis(herolandBasicData);
            list = BeanCopyUtils.copyArrayByJSON(herolandBasicData,HerolandBasicDataDP.class);
        }else {
            List<String> exist = Lists.newArrayList();
            list = redisValue.stream().map(e -> {
                HerolandBasicData data = (HerolandBasicData)e;
                exist.add(data.getDictKey());
                HerolandBasicDataDP dataDP = convertToDP(data);
                return dataDP;
            }).collect(Collectors.toList());
            newKeys.removeAll(exist);
            List<String> notExist = newKeys;
            if (!CollectionUtils.isEmpty(notExist)){
                herolandBasicData = herolandBasicDataMapper.selectByDictKeys(notExist);
                batchSetRedis(herolandBasicData);
                List notExistList = BeanCopyUtils.copyArrayByJSON(herolandBasicData,HerolandBasicDataDP.class);
                list.addAll(notExistList);
            }
        }
        return list;
    }

    private void batchSetRedis(List<HerolandBasicData> herolandBasicData){
        herolandBasicData.forEach(e -> {
            String key = String.format(RedisConstant.ADMIN_KEY, e.getDictKey());
            redisService.set(key, e, RedisConstant.EXPIRE_SECONDS);
        });
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


    @Override
    public ResponseBody<Boolean> importBasicDataExcel(MultipartHttpServletRequest request) {

        Map<String, MultipartFile> fileMap = request.getFileMap();
        for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
            MultipartFile multipartFile = entry.getValue();

            if (multipartFile instanceof CommonsMultipartFile) {

                List<BasicData> basicData = ExcelFileUtils.importExcel(multipartFile, 0, 1, BasicData.class);
                if (CollectionUtils.isEmpty(basicData)){
                    return null;
                }
                check(basicData);
                List<BasicDataMappingDP> mapping = basicData.stream().map(e -> {
                    BasicDataMappingDP basicDataMappingDP = new BasicDataMappingDP();
                    basicDataMappingDP.setDataTypeId(e.getDataTypeId());
                    basicDataMappingDP.setId(e.getId());
                    basicDataMappingDP.setName(e.getName());
                    basicDataMappingDP.setCode(e.getCode());
                    return basicDataMappingDP;
                }).collect(Collectors.toList());
                mapping.stream().forEach(e -> {
                    List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCodesAndValue(Lists.newArrayList(e.getCode()), e.getName());
                    if (!CollectionUtils.isEmpty(herolandBasicData)){
                        herolandBasicData.stream().forEach(data -> {
                            data.setMappingId(e.getId());
                            data.setMappingKey(e.getDataTypeId());
                            herolandBasicDataMapper.updateByPrimaryKeySelective(data);
                        });
                        //如果不存在，手动添加一条数据
                    }else {
                        HerolandBasicDataDP dataDP = new HerolandBasicDataDP();
                        dataDP.setDictValue(e.getName());
                        dataDP.setCode(e.getCode());
                        dataDP.setMappingId(e.getId());
                        dataDP.setMappingKey(e.getDataTypeId());
                        addDict(dataDP);
                    }
                });
            }

        }
        return new ResponseBody<>();
    }

    private void check(List<BasicData> basicData){
        basicData.stream().forEach(e -> {
            AssertUtils.notBlank(e.getId(),e.getCode(),e.getDataTypeId(),e.getName());
            if (e.getCode().equalsIgnoreCase("GA")){
                if (!e.getDataTypeId().toLowerCase().equalsIgnoreCase("gradeid")){
                    ResponseBodyWrapper.failException("格式不对");
                }
            }else
            if (e.getCode().equalsIgnoreCase("CU")){
                if (!e.getDataTypeId().toLowerCase().equalsIgnoreCase("subjectid")){
                    ResponseBodyWrapper.failException("格式不对");
                }
            }else
            if (e.getCode().equalsIgnoreCase("PA")){
                if (!e.getDataTypeId().toLowerCase().equalsIgnoreCase("pharseid")){
                    ResponseBodyWrapper.failException("格式不对");
                }
            }else
            if (e.getCode().equalsIgnoreCase("ED")){
                if (!e.getDataTypeId().toLowerCase().equalsIgnoreCase("edtionid")){
                    ResponseBodyWrapper.failException("格式不对");
                }
            }else {
                ResponseBodyWrapper.failException("不支持的基础类型数据");
            }

        });
    }
}
