package com.heroland.competition.service.admin.impl;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandCourseMapper;
import com.heroland.competition.dal.mapper.HerolandSchoolCourseMapper;
import com.heroland.competition.dal.pojo.HerolandCourse;
import com.heroland.competition.dal.pojo.HerolandSchoolCourse;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandCourseDP;
import com.heroland.competition.domain.dp.HerolandSchoolCourseDP;
import com.heroland.competition.domain.dto.CourseForTeacherDto;
import com.heroland.competition.domain.dto.HerolandCourseDto;
import com.heroland.competition.domain.dto.HerolandSchoolDto;
import com.heroland.competition.domain.dto.SchoolCourseForTeacherDto;
import com.heroland.competition.domain.request.HerolandCourseForSchoolRequest;
import com.heroland.competition.domain.request.HerolandCourseForTeacherRequest;
import com.heroland.competition.domain.request.HerolandCoursePageRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandCourseService;
import com.platform.sso.domain.dp.PlatformSysUserClassDP;
import com.platform.sso.domain.qo.PlatformSysUserClassQO;
import com.platform.sso.facade.PlatformSsoUserClassServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author smjyouzan
 * @date 2020/7/18
 */
@Component
@Slf4j
public class HeroLandCourseServiceImpl implements HeroLandCourseService {
    @Resource
    private HerolandCourseMapper herolandCourseMapper;

    @Resource
    private HerolandSchoolCourseMapper herolandSchoolCourseMapper;

    @Resource
    private HeroLandAdminService heroLandAdminService;

    @Resource
    private PlatformSsoUserClassServiceFacade platformSsoUserClassServiceFacade;


    @Override
    @Transactional
    public Boolean addCourse(HerolandCourseDP courseDP) {

            if (!NumberUtils.nullOrZeroLong(courseDP.getId())){
                updateCourse(courseDP);
            }else {
                Long id = null;
                courseDP = courseDP.checkAndBuildBeforeCreate();
                HerolandCourse course = new HerolandCourse();
                course.setCourse(courseDP.getCourse());
                course.setEdition(courseDP.getEdition());
                course.setGradeSlice(courseDP.getGradeSlice());
                course.setSubType(courseDP.getSubType());
                course.setGrade(courseDP.getGrade());
                List<HerolandCourse> herolandCourses = herolandCourseMapper.get(courseDP.getGrade(), courseDP.getGradeSlice(), courseDP.getCourse(), courseDP.getEdition(), courseDP.getSubType());
                if (CollectionUtils.isEmpty(herolandCourses)){
                    herolandCourseMapper.insertSelective(course);
                    id = course.getId();
                }else {
                    id = herolandCourses.get(0).getId();
                }
                Long courseId = id;
                List<HerolandSchoolCourse> schoolCourses = Lists.newArrayList();
                if (!CollectionUtils.isEmpty(courseDP.getSchoolCourseDPS())){
                    List<String> schoolCodes = courseDP.getSchoolCourseDPS().stream().map(HerolandSchoolCourseDP::getSchoolCode).collect(Collectors.toList());
                    List<HerolandSchoolCourse> bySchoolListAndCourse = herolandSchoolCourseMapper.getBySchoolListAndCourse(schoolCodes, courseId);
                    Map<String, List<HerolandSchoolCourse>> schoolMap = bySchoolListAndCourse.stream().collect(Collectors.groupingBy(HerolandSchoolCourse::getSchoolKey));
                    courseDP.getSchoolCourseDPS().stream().forEach(e -> {
                        if (!schoolMap.containsKey(e.getSchoolCode())){
                            HerolandSchoolCourse schoolCourse = new HerolandSchoolCourse();
                            schoolCourse.setSchoolKey(e.getSchoolCode());
                            schoolCourse.setCourseId(courseId);
                            schoolCourses.add(schoolCourse);
                        }
                    });
                    if (!CollectionUtils.isEmpty(schoolCourses)){
                        herolandSchoolCourseMapper.batchSave(schoolCourses);
                    }
                }
            }
        return true;
    }

    @Override
    @Transactional
    public Boolean updateCourse(HerolandCourseDP courseDP) {
        AssertUtils.notNull(courseDP.getId());
        HerolandCourse course = new HerolandCourse();
        course.setId(courseDP.getId());
        course.setCourse(courseDP.getCourse());
        course.setEdition(courseDP.getEdition());
        course.setGradeSlice(courseDP.getGradeSlice());
        course.setSubType(courseDP.getSubType());
        course.setGrade(courseDP.getGrade());
        List<HerolandCourse> herolandCourses = herolandCourseMapper.get(courseDP.getGrade(), courseDP.getGradeSlice(), courseDP.getCourse(), courseDP.getEdition(), courseDP.getSubType());
        if (!CollectionUtils.isEmpty(herolandCourses) && !Objects.equals(herolandCourses.get(0).getId(), course.getId())){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.PARAM_DUP.getErrorMessage());
        }
        int i = herolandCourseMapper.updateByPrimaryKeySelective(course);
        if (i == 0){
            return false;
        }
        List<HerolandSchoolCourse> schoolCourses = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(courseDP.getSchoolCourseDPS())){
            courseDP.getSchoolCourseDPS().stream().forEach(e -> {
                HerolandSchoolCourse schoolCourse = new HerolandSchoolCourse();
                schoolCourse.setCourseId(course.getId());
                schoolCourse.setSchoolKey(e.getSchoolCode());
                schoolCourses.add(schoolCourse);
            });
        }
        herolandSchoolCourseMapper.deleteBySchoolAndCourse(null,courseDP.getId());
        if (!CollectionUtils.isEmpty(schoolCourses)){
            herolandSchoolCourseMapper.batchSave(schoolCourses);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteCourse(Long id) {
        if (NumberUtils.nullOrZeroLong(id)){
            return true;
        }
        herolandCourseMapper.deleteByPrimaryKey(id);
        herolandSchoolCourseMapper.deleteBySchoolAndCourse(null,id);
        return true;
    }

    @Override
    public HerolandCourseDto getById(Long id) {
        if (NumberUtils.nullOrZeroLong(id)){
            return null;
        }
        HerolandCourse herolandCourse = herolandCourseMapper.selectByPrimaryKey(id);
        if (Objects.isNull(herolandCourse)){
            return null;
        }
        List<HerolandSchoolCourse> schoolCourses = herolandSchoolCourseMapper.getBySchoolListAndCourse(null, id);
        List<String> schoolKeys = schoolCourses.stream().map(HerolandSchoolCourse::getSchoolKey).collect(Collectors.toList());
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(schoolKeys);
        Map<String, List<HerolandBasicDataDP>> keyMap = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        HerolandCourseDto dto = getAdminData(herolandCourse);
        List<HerolandSchoolDto> schoolDtoList = Lists.newArrayList();
        schoolKeys.stream().forEach(school -> {
            HerolandSchoolDto dto1 = new HerolandSchoolDto();
            dto1.setKey(school);
            dto1.setName(keyMap.get(school).get(0).getDictValue());
            schoolDtoList.add(dto1);
        });
        dto.setSchoolDtoList(schoolDtoList);
        return dto;
    }

    @Override
    public List<HerolandCourseDto> getByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return Lists.newArrayList();
        }
        List<HerolandCourse> herolandCourses = herolandCourseMapper.selectByPrimaryKeys(ids);

        return null;
    }

    private HerolandCourseDto getAdminData(HerolandCourse herolandCourse){
        HerolandCourseDto dto = new HerolandCourseDto();
        dto.setCourse(herolandCourse.getCourse());
        dto.setEdition(herolandCourse.getEdition());
        dto.setEditionType(herolandCourse.getSubType());
        dto.setGrade(herolandCourse.getGrade());
        dto.setUnit(herolandCourse.getGradeSlice());
        dto.setId(herolandCourse.getId());
        List<String> keys = Lists.newArrayList();
        keys.add(herolandCourse.getCourse());
        keys.add(herolandCourse.getGrade());
        keys.add(herolandCourse.getSubType());
        keys.add(herolandCourse.getEdition());
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(keys);
        Map<String, List<HerolandBasicDataDP>> keyMap = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        if (keyMap.containsKey(herolandCourse.getCourse())){
            dto.setCourseName(keyMap.get(herolandCourse.getCourse()).get(0).getDictValue());
        }
        if (keyMap.containsKey(herolandCourse.getEdition())){
            dto.setEditionName(keyMap.get(herolandCourse.getEdition()).get(0).getDictValue());
        }
        if (keyMap.containsKey(herolandCourse.getGrade())){
            dto.setGradeName(keyMap.get(herolandCourse.getGrade()).get(0).getDictValue());
        }
        if (keyMap.containsKey(herolandCourse.getSubType())){
            dto.setEditionTypeName(keyMap.get(herolandCourse.getSubType()).get(0).getDictValue());
        }
       return dto;
    }

    @Override
    public PageResponse<HerolandCourseDto> pageQuery(HerolandCoursePageRequest request) {
        List<HerolandCourseDto> result = new ArrayList<>();
        PageResponse<HerolandCourseDto> pageResult = new PageResponse<>();
        Page<HerolandCourse> data = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                () -> herolandCourseMapper.get(request.getGrade(), request.getUnit(), request.getCourse(), request.getEdition(), request.getEditionType()));
        if (!CollectionUtils.isEmpty(data.getResult())){
            List<Long> courseIds = data.getResult().stream().map(HerolandCourse::getId).collect(Collectors.toList());
            List<HerolandSchoolCourse> schoolCourses = herolandSchoolCourseMapper.getByCourses(courseIds);
            Set<String> schoolKeys = schoolCourses.stream().map(HerolandSchoolCourse::getSchoolKey).collect(Collectors.toSet());
            Map<Long, List<HerolandSchoolCourse>> courseMap = schoolCourses.stream().collect(Collectors.groupingBy(HerolandSchoolCourse::getCourseId));
            List<HerolandBasicDataDP> schoolDatas = heroLandAdminService.getDictInfoByKeys(new ArrayList<>(schoolKeys));
            Map<String, List<HerolandBasicDataDP>> keyMap = schoolDatas.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
            data.getResult().stream().forEach(e -> {
                HerolandCourseDto courseDto = getAdminData(e);
                List<HerolandSchoolDto> schoolDtoList = Lists.newArrayList();
                List<HerolandSchoolCourse> schoolCourses1 = courseMap.get(e.getId());
                if (!CollectionUtils.isEmpty(schoolCourses1)){
                    schoolCourses1.stream().forEach(school -> {
                        HerolandSchoolDto dto1 = new HerolandSchoolDto();
                        dto1.setKey(school.getSchoolKey());
                        List<HerolandBasicDataDP> dpList = keyMap.get(school.getSchoolKey());
                        dto1.setName(CollectionUtils.isEmpty(dpList)? null : dpList.get(0).getDictValue());
                        schoolDtoList.add(dto1);
                    });
                    courseDto.setSchoolDtoList(schoolDtoList);
                }
               result.add(courseDto);
           });
        }
        pageResult.setItems(result);
        pageResult.setPageSize(data.getPageSize());
        pageResult.setPage(data.getPageNum());
        pageResult.setTotal((int) data.getTotal());
        return pageResult;
    }

    @Override
    public List<HerolandCourseDto> courseForSchool(HerolandCourseForSchoolRequest request) {
        List<HerolandSchoolCourse> courses = herolandSchoolCourseMapper.getBySchoolListAndCourse(Lists.newArrayList(request.getSchoolCode()), null);
        if (CollectionUtils.isEmpty(courses)){
            return Lists.newArrayList();
        }
        List<Long> courseIds = courses.stream().map(HerolandSchoolCourse::getCourseId).distinct().collect(Collectors.toList());
        List<HerolandCourse> courseList = herolandCourseMapper.selectByPrimaryKeys(courseIds);
        List<HerolandCourseDto> result = courseList.stream()
                .filter(e -> CollectionUtils.isEmpty(request.getGradeCodeList()) || request.getGradeCodeList().contains(e.getGrade()))
                .map(this::getAdminData).collect(Collectors.toList());
        return result;
    }

    @Override
    public CourseForTeacherDto courseForTeacher(HerolandCourseForTeacherRequest request) {

        CourseForTeacherDto teacherDto = new CourseForTeacherDto();
        PlatformSysUserClassQO platformSysUserQO = new PlatformSysUserClassQO();
        platformSysUserQO.setUserId(request.getUserId());
        //参数为空
        ResponseBody<List<PlatformSysUserClassDP>> listResponseBody = platformSsoUserClassServiceFacade.queryUserClassList(platformSysUserQO);
        if (!listResponseBody.isSuccess() || CollectionUtils.isEmpty(listResponseBody.getData())) {
            return teacherDto;
        }
        List<PlatformSysUserClassDP> userInfo = listResponseBody.getData().
                stream()
                .filter(e -> {
                    if (CollectionUtils.isEmpty(request.getClassCodeList()) && CollectionUtils.isEmpty(request.getGradeCodeList())) {
                        return true;
                    }
                    if (!CollectionUtils.isEmpty(request.getClassCodeList()) && !CollectionUtils.isEmpty(request.getGradeCodeList())) {
                        return request.getClassCodeList().contains(e.getClassCode()) && request.getGradeCodeList().contains(e.getGradeCode());
                    }
                    if (!CollectionUtils.isEmpty(request.getClassCodeList())) {
                        return request.getClassCodeList().contains(e.getClassCode());
                    }
                    if (!CollectionUtils.isEmpty(request.getGradeCodeList())) {
                        return request.getGradeCodeList().contains(e.getClassCode());
                    }
                    return false;
                }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(userInfo)){
            List<String> grade = userInfo.stream().map(PlatformSysUserClassDP::getGradeCode).distinct().collect(Collectors.toList());
            List<String> clazz = userInfo.stream().map(PlatformSysUserClassDP::getClassCode).distinct().collect(Collectors.toList());
            List<String> course = userInfo.stream().map(PlatformSysUserClassDP::getSubjectCode).distinct().collect(Collectors.toList());
            List<String> keys = Lists.newArrayList();
            keys.addAll(grade);
            keys.addAll(clazz);
            keys.addAll(course);
            List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(keys);
            Map<String, List<HerolandBasicDataDP>> keyMap = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
            teacherDto.setGrade(getAdminDataForTeacher(grade, keyMap));
            teacherDto.setClazz(getAdminDataForTeacher(clazz, keyMap));
            teacherDto.setCourse(getAdminDataForTeacher(course, keyMap));
        }

        return teacherDto;
    }

    private List<SchoolCourseForTeacherDto> getAdminDataForTeacher(List<String> keys, Map<String, List<HerolandBasicDataDP>> keyMap){
        if (CollectionUtils.isEmpty(keys)){
            return Lists.newArrayList();
        }
        List<SchoolCourseForTeacherDto> teacherDtos = keys.stream().map(e -> {
            SchoolCourseForTeacherDto course = new SchoolCourseForTeacherDto();
            course.setKey(e);
            if (keyMap.containsKey(e)) {
                course.setName(keyMap.get(e).get(0).getDictValue());
            }
            return course;
        }).collect(Collectors.toList());
        return teacherDtos;
    }
}
