package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import com.heroland.competition.common.contants.ChapterEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.domain.dp.HerolandChapterDP;
import com.heroland.competition.domain.dto.HerolandChapterDto;
import com.heroland.competition.domain.dto.HerolandChapterSimpleDto;
import com.heroland.competition.domain.dto.HerolandKnowledgeDto;
import com.heroland.competition.domain.dto.HerolandKnowledgeSimpleDto;
import com.heroland.competition.domain.qo.HerolandChapterQO;
import com.heroland.competition.domain.request.HerolandChapterKnowledgeRequest;
import com.heroland.competition.domain.request.HerolandChapterPageRequest;
import com.heroland.competition.domain.request.HerolandChapterRequest;
import com.heroland.competition.domain.request.HerolandPreChapterRequest;
import com.heroland.competition.service.admin.HeroLandChapterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 章节管理
 */
@RestController
@RequestMapping("/heroland/chapter")
public class HeroLandAdminChapterController {

    @Resource
    private HeroLandChapterService heroLandChapterService;

    /**
     * 增加章节
     * @return
     */
    @RequestMapping(value = "/addAndEdit", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> add(@RequestBody HerolandChapterRequest request) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        HerolandChapterDP dp = new HerolandChapterDP();
        dp.setId(request.getId());
        dp.setCourse(request.getCourse());
        dp.setEdition(request.getEdition());
        dp.setGrade(request.getGrade());
        dp.setContent(request.getContent());
        dp.setContentType(request.getContentType());
        dp.setOrder(request.getOrder());
        dp.setParentId(request.getParentId());
        dp.setKnowledges(request.getKnowledges());
        result.setData(heroLandChapterService.add(dp));
        return result;
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> delete(@RequestParam("id") Long id) {
        ResponseBody<Boolean> result = new ResponseBody<>();
        result.setData(heroLandChapterService.delete(id));
        return result;
    }

    /**
     * 查看详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<HerolandChapterDto> getById(@RequestParam("id") Long id) {
        ResponseBody<HerolandChapterDto> result = new ResponseBody<HerolandChapterDto>();
        result.setData(heroLandChapterService.getById(id));
        return result;
    }

    /**
     * 分页查询
     * @param qo
     * @return
     */
    @RequestMapping(value = "/pageQuery", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandChapterDto>> pageQuery(@RequestBody HerolandChapterPageRequest qo) {
        if (NumberUtils.nullOrZero(qo.getContentType())){
            qo.setContentType(ChapterEnum.ZHANG.getType());
        }
        ResponseBody<List<HerolandChapterDto>> result = new ResponseBody<>();
        PageResponse<HerolandChapterDto> pageResponse = heroLandChapterService.pageQuery(qo);
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
     * 查询下拉选框的知识点
     * @param request
     * @return
     */
    @RequestMapping(value = "/chapterKnowledge", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandKnowledgeSimpleDto>> chapterKnowledge(@RequestBody HerolandChapterKnowledgeRequest request) {
        ResponseBody<List<HerolandKnowledgeSimpleDto>> result = new ResponseBody<>();
        List<HerolandKnowledgeSimpleDto> chapterKnowledge = heroLandChapterService.getChapterKnowledge(request);
        result.setData(chapterKnowledge);
        return result;
    }

    /**
     * 新增时获取上一级的章节列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/chapteList", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandChapterSimpleDto>> chapteList(@RequestBody HerolandPreChapterRequest request) {
        ResponseBody<List<HerolandChapterSimpleDto>> result = new ResponseBody<>();
        List<HerolandChapterSimpleDto> chapterKnowledge = heroLandChapterService.getChapterList(request);
        result.setData(chapterKnowledge);
        return result;
    }

}
