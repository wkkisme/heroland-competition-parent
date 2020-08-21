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
import com.heroland.competition.domain.dto.HeroLandUserClassInfoDto;
import com.heroland.competition.domain.dto.HeroLandUserDepartmentDto;
import com.heroland.competition.domain.qo.HeroLandClassManageQO;
import com.heroland.competition.domain.qo.HeroLandUserClassQO;
import com.heroland.competition.domain.request.HerolandDataPageRequest;
import com.heroland.competition.domain.request.UserClassRequest;
import com.heroland.competition.domain.request.UserDepartmentRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandSchoolService;
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
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

    @Resource
    private HeroLandSchoolService heroLandSchoolService;

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
    @Deprecated
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
    public ResponseBody<List<HeroLandUserClassInfoDto>> getClassInfoList(UserClassRequest request) {
        ResponseBody<List<HeroLandUserClassInfoDto>> result = new ResponseBody();
        List<HeroLandUserClassInfoDto> list = Lists.newArrayList();
        result.setData(list);
        PlatformSysUserClassQO platformSysUserQO = new PlatformSysUserClassQO();
        platformSysUserQO.setUserId(request.getUserId());
        //参数为空
        ResponseBody<List<PlatformSysUserClassDP>> listResponseBody = platformSsoUserClassServiceFacade.queryUserClassList(platformSysUserQO);
        if (!listResponseBody.isSuccess() || CollectionUtils.isEmpty(listResponseBody.getData())) {
            return result;
        }
        if (CollectionUtils.isEmpty(request.getDepartmentCode())) {
            List<PlatformSysUserClassDP> allClassData = listResponseBody.getData();
            list = processClassInfo(allClassData, request);
        } else {
            //默认以年级作为参数
            if (StringUtils.isEmpty(request.getDepartmentType())) {
                request.setDepartmentType("GA");
            }
            //查询参数不为空
            switch (request.getDepartmentType()){
                //暂时不根据班级去查
                case "CA":
                    List<PlatformSysUserClassDP> classInfoByClass = listResponseBody.getData().stream().filter(e -> request.getDepartmentCode().contains(e.getClassCode())).collect(Collectors.toList());
                    list = processClassInfo(classInfoByClass, request);

                    break;
                case "GA":
                    List<PlatformSysUserClassDP> classInfoByGrades = listResponseBody.getData().stream().filter(e -> request.getDepartmentCode().contains(e.getGradeCode())).collect(Collectors.toList());
                    list = processClassInfo(classInfoByGrades, request);
                    break;

                    //暂时不根据学校去查
                case "SH":
                    List<PlatformSysUserClassDP> classInfoByOrgs = listResponseBody.getData().stream().filter(e -> request.getDepartmentCode().contains(e.getOrgCode())).collect(Collectors.toList());
                    list = processClassInfo(classInfoByOrgs, request);
                    break;
            }
        }
        result.setData(list);
        return result;

    }

    private List<HeroLandUserClassInfoDto> processClassInfo(List<PlatformSysUserClassDP> classDPS, UserClassRequest request){
        List<HeroLandUserClassInfoDto> list = Lists.newArrayList();
        List<String> classCodeList = classDPS.stream().map(PlatformSysUserClassDP::getClassCode).distinct().collect(Collectors.toList());;
        List<String> gradeCodeList = classDPS.stream().map(PlatformSysUserClassDP::getGradeCode).distinct().collect(Collectors.toList());;
        List<String> orgCodeList = classDPS.stream().map(PlatformSysUserClassDP::getOrgCode).distinct().collect(Collectors.toList());;

        //批量查询班级人数
        List<String> departmentCode =  Lists.newArrayList();
        departmentCode.addAll(classCodeList);
        departmentCode.addAll(gradeCodeList);
        departmentCode.addAll(orgCodeList);
        List<HerolandBasicDataDP> adminDataList = heroLandAdminService.getDictInfoByKeys(departmentCode);
        Map<String, HerolandBasicDataDP> adminDataMap = adminDataList.stream().collect(Collectors.toMap(HerolandBasicDataDP::getDictKey, Function.identity()));

        Map<String, Integer> ca = heroLandSchoolService.listCountByKeys(classCodeList, "CA");
        classDPS.stream().forEach(e -> {
            HeroLandUserClassInfoDto infoDto = new HeroLandUserClassInfoDto();
            infoDto.setUserId(request.getUserId());
            infoDto.setUserType(e.getUserType());
            infoDto.setClassCode(e.getClassCode());
            infoDto.setGradeCode(e.getGradeCode());
            infoDto.setOrgCode(e.getOrgCode());
            infoDto.setClassName(adminDataMap.containsKey(infoDto.getClassCode()) ? adminDataMap.get(infoDto.getClassCode()).getDictValue() : "");
            infoDto.setGradeName(adminDataMap.containsKey(infoDto.getGradeCode()) ? adminDataMap.get(infoDto.getGradeCode()).getDictValue() : "");
            infoDto.setOrgName(adminDataMap.containsKey(infoDto.getOrgCode()) ? adminDataMap.get(infoDto.getOrgCode()).getDictValue() : "");
            infoDto.setClassDefaultStudentCount(ca.containsKey(infoDto.getClassCode()) ? ca.get(infoDto.getClassCode()) : 0);
            //todo
            infoDto.setClassHasStudentCount(98);
            list.add(infoDto);
        });
        return list;
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
    public ResponseBody<List<PlatformSysUserDP>> getClassUser(PlatformSysUserClassQO qo) {
        ResponseBody<List<PlatformSysUserDP>> result = new ResponseBody<>();
        ResponseBody<List<PlatformSysUserClassDP>> classListWrapper = platformSsoUserClassServiceFacade.queryUserClassList(qo);

        List<PlatformSysUserClassDP> classList = classListWrapper.getData();
        if (CollectionUtils.isEmpty(classList)) {
            return result;
        }
        PlatformSysUserQO userQO = new PlatformSysUserQO();
        List<String> userIds = classList.stream().map(PlatformSysUserClassDP::getUserId).collect(Collectors.toList());
        userQO.setUserIds(userIds);
        RpcResult<List<PlatformSysUserDP>> listRpcResult = platformSsoUserServiceFacade.queryUserList(userQO);
        result.setData(listRpcResult.getData());
        result.setPage(classListWrapper.getPage());
        return result;
    }

    @Override
    public ResponseBody<List<HeroLandUserDepartmentDto>> getClassUserDepartment(UserDepartmentRequest request) {
        ResponseBody<List<HeroLandUserDepartmentDto>> responseBody = new ResponseBody<>();
        PlatformSysUserClassQO qo = new PlatformSysUserClassQO();
        List<HeroLandUserDepartmentDto> list = Lists.newArrayList();
        qo.setUserId(request.getUserId());
        ResponseBody<List<PlatformSysUserClassDP>> listResponseBody = platformSsoUserClassServiceFacade.queryUserClassList(qo);
        if (!listResponseBody.isSuccess() || CollectionUtils.isEmpty(listResponseBody.getData())) {
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
        responseBody.setData(list);
        return responseBody;
    }
}
