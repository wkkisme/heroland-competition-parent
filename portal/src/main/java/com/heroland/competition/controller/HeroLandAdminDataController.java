package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.expception.AppSystemException;
import com.anycommon.response.page.Pagination;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.request.HerolandBasicDataPageRequest;
import com.heroland.competition.domain.request.HerolandBasicDataRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandSchoolService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 字典数据
 */
@RestController
@RequestMapping("/heroland/admin")
public class HeroLandAdminDataController {

    @Resource
    private HeroLandAdminService heroLandAdminService;

    @Resource
    private HeroLandSchoolService heroLandSchoolService;

    /**
     * 增加或编辑字典数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/addAndEdit", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> addDict(@RequestBody HerolandBasicDataRequest request) {
        ResponseBody<Boolean> result = new ResponseBody<Boolean>();
        //如果是学校的
        HerolandBasicDataDP dp = new HerolandBasicDataDP();
        dp.setCode(request.getCode());
        dp.setDictValue(request.getDictValue());
        dp.setId(request.getId());
        if (NumberUtils.nullOrZeroLong(dp.getId())){
            heroLandAdminService.addDict(dp);
        }else {
            heroLandAdminService.editDict(dp);
        }
        result.setData(true);
        return result;
    }


    /**
     * 批量删除某字典数据
     */
    @RequestMapping(value = "/deleteDict", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> deleteDict(@RequestParam("dataKeys") String  dataKeys) {
        ResponseBody<Boolean> result = new ResponseBody<Boolean>();
        if (!StringUtils.isEmpty(dataKeys)){
            List<String> keys = Arrays.asList(dataKeys.split(","));
             heroLandAdminService.deleteDict(keys);
        }
        result.setData(true);
        return result;
    }

    /**
     * 分页查询
     * @param
     * @return
     */
    @RequestMapping(value = "/pageQueryDict", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandBasicDataDP>> pageQueryDict(@RequestBody HerolandBasicDataPageRequest request) {
        ResponseBody<List<HerolandBasicDataDP>> result = new ResponseBody<>();
        PageResponse<HerolandBasicDataDP> pageResponse = heroLandAdminService.pageQueryDict(request);
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
     * 查看字典数据详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getDictInfo", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandBasicDataDP>> getDictInfoById(@RequestParam("keys") String keys) {
        ResponseBody<List<HerolandBasicDataDP>> responseBody = new ResponseBody<List<HerolandBasicDataDP>>();
        List<String> strings = Arrays.asList(keys.split(","));
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(strings);
        responseBody.setData(dictInfoByKeys);
        return responseBody;

    }


}
