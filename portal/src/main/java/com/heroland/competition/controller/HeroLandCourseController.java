package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.AdminFieldEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandCourseDP;
import com.heroland.competition.domain.dp.HerolandSchoolCourseDP;
import com.heroland.competition.domain.dto.HerolandAdminDataDto;
import com.heroland.competition.domain.dto.HerolandCourseDto;
import com.heroland.competition.domain.request.HerolandCourseAddRequest;
import com.heroland.competition.domain.request.HerolandCoursePageRequest;
import com.heroland.competition.domain.request.HerolandDataPageRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandCourseService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 科目管理
 */
@RestController
@RequestMapping("/heroland/course")
public class HeroLandCourseController {


    @Resource
    private HeroLandCourseService heroLandCourseService;

    @Resource
    private HeroLandAdminService heroLandAdminService;


    /**
     * 获取科目列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/pageQuery", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandCourseDto>> pageQuery(@RequestBody HerolandCoursePageRequest request) {
        ResponseBody<List<HerolandCourseDto>> result = new ResponseBody<>();
        PageResponse<HerolandCourseDto> pageResponse = heroLandCourseService.pageQuery(request);
        result.setData(pageResponse.getItems());
        Pagination pagination = new Pagination();
        pagination.setPageIndex(pageResponse.getPage());
        pagination.setPageSize(pageResponse.getPageSize());
        pagination.setTotalCount(pageResponse.getTotal());
        pagination.setTotalPage(pageResponse.getTotalPages());
        result.setPage(pagination);
        return result;
    }

    /**
     * 添加|更新科目，及分配的学校
     * @param request
     * @return
     */
    @RequestMapping(value = "/createCourse", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> createCourse(@RequestBody HerolandCourseAddRequest request) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        HerolandCourseDP courseDP = new HerolandCourseDP();
        courseDP.setCourse(request.getCourse());
        courseDP.setGrade(request.getGrade());
        courseDP.setEdition(request.getEdition());
        courseDP.setGradeSlice(request.getUnit());
        courseDP.setSubType(request.getEditionType());
        courseDP.setId(request.getId());
        courseDP.setSchoolCourseDPS(BeanCopyUtils.copyArrayByJSON(request.getSchoolList(), HerolandSchoolCourseDP.class));
        heroLandCourseService.addCourse(courseDP);
        result.setData(true);
        return result;
    }

    /**
     * 删除某一版本科目
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteNode", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> deleteNode(@RequestParam("id")Long id) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        result.setData(heroLandCourseService.deleteCourse(id));
        return result;
    }

    /**
     * 获取科目信息及分配的学校信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/courseInfo", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<HerolandCourseDto> get(@RequestParam("id")Long id) {
        ResponseBody<HerolandCourseDto> result = new ResponseBody<>();
        result.setData(heroLandCourseService.getById(id));
        return result;
    }

    /**
     * 获取下拉选框
     * code
     * 年级 GA
     * 科目 CU
     * 版本 ED
     * 版本的必|选修类别 BX
     * @return
     */
    @RequestMapping(value = "/courseCode", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandAdminDataDto>> courseCode(@RequestParam("code")String code) {
        ResponseBody<List<HerolandAdminDataDto>> result = new ResponseBody<>();
        List<HerolandAdminDataDto> list = Lists.newArrayList();
        HerolandDataPageRequest request = new HerolandDataPageRequest();
        request.setCode(code);
        request.setNeedPage(false);
        PageResponse<HerolandBasicDataDP> area = heroLandAdminService.pageDataByCode(request);
        if (!CollectionUtils.isEmpty(area.getItems())){
            area.getItems().stream().forEach(e -> {
                HerolandAdminDataDto simpleDto = new HerolandAdminDataDto();
                simpleDto.setId(e.getId());
                simpleDto.setCode(AdminFieldEnum.AREA.getCode());
                simpleDto.setKey(e.getDictKey());
                simpleDto.setName(e.getDictValue());
                list.add(simpleDto);
            });
        }
        result.setData(list);
        return result;
    }

}
