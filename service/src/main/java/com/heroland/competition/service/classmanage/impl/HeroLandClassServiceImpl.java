package com.heroland.competition.service.classmanage.impl;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.AdminFieldEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.dal.mapper.HeroLandClassMapper;
import com.heroland.competition.dal.mapper.HeroLandUserClassMapper;
import com.heroland.competition.dal.pojo.HeroLandClass;
import com.heroland.competition.dal.pojo.HeroLandUserClass;
import com.heroland.competition.dal.pojo.HeroLandUserClassExample;
import com.heroland.competition.domain.dp.HeroLandClassDP;
import com.heroland.competition.domain.dp.HeroLandUserClassDP;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dto.HeroLandUserDepartmentDto;
import com.heroland.competition.domain.qo.HeroLandClassManageQO;
import com.heroland.competition.domain.qo.HeroLandUserClassQO;
import com.heroland.competition.domain.request.HerolandDataPageRequest;
import com.heroland.competition.domain.request.UserDepartmentRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.classmanage.HeroLandClassService;
import com.platform.sso.domain.dp.PlatformSysUserClassDP;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.domain.qo.PlatformSysUserClassQO;
import com.platform.sso.domain.qo.PlatformSysUserQO;
import com.platform.sso.facade.PlatformSsoServiceFacade;
import com.platform.sso.facade.PlatformSsoUserClassServiceFacade;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platform.sso.facade.result.RpcResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 班级管理
 *
 * @author wangkai
 */
@Service
@Slf4j
public class HeroLandClassServiceImpl implements HeroLandClassService {

    @Resource
    private HeroLandClassMapper heroLandClassMapper;

    @Resource
    private HeroLandUserClassMapper heroLandUserClassMapper;

    @Resource
    private PlatformSsoServiceFacade platformSsoServiceFacade;

    @Resource
    private PlatformSsoUserClassServiceFacade platformSsoUserClassServiceFacade;

    @Resource
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;

    @Resource
    private HeroLandAdminService heroLandAdminService;

    @Override
    public ResponseBody<Boolean> addClass(HeroLandClassDP dp) {

        try {
            heroLandClassMapper.insert(BeanUtil.insertConversion(dp.addCheck(), new HeroLandClass()));
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<Boolean> updateClass(HeroLandClassDP dp) {

        try {
            heroLandClassMapper.updateByPrimaryKeySelective(BeanUtil.updateConversion(dp.addCheck(), new HeroLandClass()));
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<List<HeroLandUserClassDP>> getClassList(PlatformSysUserClassQO qo) {
        PlatformSysUserQO platformSysUserQO = new PlatformSysUserQO();
        BeanUtil.copyProperties(qo, platformSysUserQO);
        RpcResult<List<PlatformSysUserDP>> responseBody = platformSsoUserServiceFacade.queryUserList(platformSysUserQO);
        ResponseBody<List<HeroLandUserClassDP>> result = new ResponseBody<>();
        if (!CollectionUtils.isEmpty(responseBody.getData())) {
            ArrayList<HeroLandUserClassDP> heroLandUserClasses = new ArrayList<>();
            responseBody.getData().parallelStream().forEach(v -> {
                HeroLandUserClassDP heroLandUserClass = new HeroLandUserClassDP();
                /*
                 * 查询年级name
                 */
                List<String> keys = new ArrayList<>();
                keys.add(v.getOrgCode());
                keys.add(v.getClassCode());
                keys.add(v.getGradeCode());
                List<HerolandBasicDataDP> orgCode = heroLandAdminService.getDictInfoByKeys(keys);
                BeanUtil.copyProperties(v, heroLandUserClass);
                if (!CollectionUtils.isEmpty(orgCode)) {
                    orgCode.forEach(code -> {
                        String dictKey = code.getDictKey();
                        if (dictKey.equals(v.getOrgCode())) {
                            heroLandUserClass.setOrgName(code.getDictValue());
                        } else if (dictKey.equalsIgnoreCase(v.getClassCode())) {
                            heroLandUserClass.setClassName(code.getDictValue());
                        } else {
                            heroLandUserClass.setGradeName(code.getDictValue());
                        }
                    });
                }
                BeanUtil.copyProperties(v, heroLandUserClass);
                heroLandUserClasses.add(heroLandUserClass);
            });
            result.setData(heroLandUserClasses);
            return ResponseBodyWrapper.successListWrapper(result.getData(), platformSsoUserClassServiceFacade.queryUserClassCount(qo).getData(), qo, HeroLandUserClassDP.class);
        }
        return result;

    }

    @Override
    public ResponseBody<Boolean> deleteClassList(HeroLandClassManageQO qo) {

        try {
            AssertUtils.assertThat(qo.getId() != null);
            HeroLandClass heroLandClass = new HeroLandClass();
            heroLandClass.setIsDeleted(true);
            heroLandClass.setId(qo.getId());
            heroLandClass.beforeDelete();
            heroLandClassMapper.updateByPrimaryKeySelective(heroLandClass);
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<Boolean> addClassUser(HeroLandUserClassDP dp) {
        try {
            heroLandUserClassMapper.insertSelective(BeanUtil.insertConversion(dp.addCheck(), new HeroLandUserClass()));
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<Boolean> updateClassUser(HeroLandUserClassDP dp) {
        try {
            heroLandUserClassMapper.insertSelective(BeanUtil.insertConversion(dp.updateCheck(), new HeroLandUserClass()));
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<Boolean> deleteClassUser(HeroLandUserClassDP dp) {
        try {
            dp.setIsDeleted(true);
            heroLandUserClassMapper.updateByPrimaryKeySelective(BeanUtil.insertConversion(dp.updateCheck(), new HeroLandUserClass()));
        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return new ResponseBody<>();
    }

    @Override
    public ResponseBody<List<HeroLandUserClassDP>> getClassUser(HeroLandUserClassQO qo) {

        List<HeroLandUserClassDP> heroLandUserClassDPS=new ArrayList<>();
        long count = 0;
        try {
            HeroLandUserClassExample heroLandAccountExample = new HeroLandUserClassExample();
            MybatisCriteriaConditionUtil.createExample(heroLandAccountExample.createCriteria(), qo);
            List<HeroLandUserClass> heroLandAccounts = heroLandUserClassMapper.selectByExample(heroLandAccountExample);
            count = heroLandUserClassMapper.countByExample(heroLandAccountExample);
            heroLandUserClassDPS = BeanUtil.queryListConversion(heroLandAccounts, HeroLandUserClassDP.class);
            if (!CollectionUtils.isEmpty(heroLandUserClassDPS)) {
                heroLandUserClassDPS.parallelStream().forEach(v -> {
                    HeroLandUserClassDP heroLandUserClass = new HeroLandUserClassDP();
                    /*
                     * 查询年级name
                     */
                    List<String> keys = new ArrayList<>();
                    keys.add(v.getOrgCode());
                    keys.add(v.getClassCode());
                    keys.add(v.getGradeCode());
                    List<HerolandBasicDataDP> orgCode = heroLandAdminService.getDictInfoByKeys(keys);
                    BeanUtil.copyProperties(v, heroLandUserClass);
                    if (!CollectionUtils.isEmpty(orgCode)) {
                        orgCode.forEach(code -> {
                            String dictKey = code.getDictKey();
                            if (dictKey.equals(v.getOrgCode())) {
                                v.setOrgName(code.getDictValue());
                            } else if (dictKey.equalsIgnoreCase(v.getClassCode())) {
                                v.setClassName(code.getDictValue());
                            } else {
                                v.setGradeName(code.getDictValue());
                            }


                        });

                    }

                });
            }

        } catch (Exception e) {
            log.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return ResponseBodyWrapper.successListWrapper(heroLandUserClassDPS, count, qo, HeroLandUserClassDP.class);
    }

    @Override
    public ResponseBody<List<HeroLandUserDepartmentDto>> getClassUserDepartment(UserDepartmentRequest request) {
        ResponseBody<List<HeroLandUserDepartmentDto>> responseBody = new ResponseBody<>();
        PlatformSysUserClassQO qo = new PlatformSysUserClassQO();
        List<HeroLandUserDepartmentDto> list = Lists.newArrayList();
        qo.setUserId(request.getUserId());
        ResponseBody<List<PlatformSysUserClassDP>> listResponseBody = platformSsoUserClassServiceFacade.queryUserClassList(qo);
        if (!listResponseBody.isSuccess() || CollectionUtils.isEmpty(listResponseBody.getData())){
            responseBody.setData(list);
            return responseBody;
        }
        List<String> departmentCode = Lists.newArrayList();
        if (AdminFieldEnum.SCHOOL.getCode().equalsIgnoreCase(request.getDepartmentType())){
            departmentCode = listResponseBody.getData().stream().map(PlatformSysUserClassDP::getOrgCode).distinct().collect(Collectors.toList());
        }else if (AdminFieldEnum.GRADE.getCode().equalsIgnoreCase(request.getDepartmentType())){
            departmentCode = listResponseBody.getData().stream().map(PlatformSysUserClassDP::getGradeCode).distinct().collect(Collectors.toList());
        }else {
            departmentCode = listResponseBody.getData().stream().map(PlatformSysUserClassDP::getClassCode).distinct().collect(Collectors.toList());
        }
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(departmentCode);
        dictInfoByKeys.stream().forEach(e -> {
            HeroLandUserDepartmentDto departmentDto = new HeroLandUserDepartmentDto();
            departmentDto.setDepartmentCode(e.getDictKey());
            departmentDto.setDepartmentName(e.getDictValue());
            departmentDto.setDepartmentType(e.getCode());
            departmentDto.setUserId(request.getUserId());
            departmentDto.setUserType(listResponseBody.getData().get(0).getUserType());
            list.add(departmentDto);
        });
        return responseBody;
    }
}
