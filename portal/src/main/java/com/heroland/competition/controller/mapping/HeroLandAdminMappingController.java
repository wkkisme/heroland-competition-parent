package com.heroland.competition.controller.mapping;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import com.heroland.competition.common.constants.ChapterEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandBasicDataMapper;
import com.heroland.competition.dal.mapper.MappingChapterMapper;
import com.heroland.competition.dal.mapper.MappingKnowledgeMapper;
import com.heroland.competition.dal.pojo.MappingChapter;
import com.heroland.competition.dal.pojo.MappingChapterExample;
import com.heroland.competition.dal.pojo.MappingKnowledge;
import com.heroland.competition.dal.pojo.MappingKnowledgeExample;
import com.heroland.competition.dal.pojo.basic.HerolandBasicData;
import com.heroland.competition.domain.dp.HerolandChapterDP;
import com.heroland.competition.domain.dp.HerolandKnowledgeDP;
import com.heroland.competition.domain.dto.HerolandChapterDto;
import com.heroland.competition.domain.dto.HerolandChapterSimpleDto;
import com.heroland.competition.domain.dto.HerolandKnowledgeSimpleDto;
import com.heroland.competition.domain.request.HerolandChapterKnowledgeRequest;
import com.heroland.competition.domain.request.HerolandChapterPageRequest;
import com.heroland.competition.domain.request.HerolandChapterRequest;
import com.heroland.competition.domain.request.HerolandPreChapterRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandChapterService;
import com.heroland.competition.service.admin.HerolandKnowledgeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 章节管理
 */
@RestController
@RequestMapping("/heroland/chapter")
public class HeroLandAdminMappingController {

    @Resource
    private HeroLandChapterService heroLandChapterService;

    @Resource
    private HerolandBasicDataMapper herolandBasicDataMapper;

    @Resource
    private MappingChapterMapper mappingChapterMapper;

    @Resource
    private MappingKnowledgeMapper mappingKnowledgeMapper;

    @Resource
    private HerolandKnowledgeService herolandKnowledgeService;

    /**
     * 写入章节
     *
     */
    public Boolean writeChapter(){
        MappingChapterExample example = new MappingChapterExample();
        MappingChapterExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<MappingChapter> mappingChapters = mappingChapterMapper.selectByExample(example);
        List<MappingChapter> chapters = mappingChapters.stream().filter(e -> StringUtils.isBlank(e.getChapter())).collect(Collectors.toList());
        List<MappingChapter> units = mappingChapters.stream().filter(e -> StringUtils.isBlank(e.getUnit())).collect(Collectors.toList());
        List<MappingChapter> sections = mappingChapters.stream().filter(e -> StringUtils.isBlank(e.getSection())).collect(Collectors.toList());
        Map<String, List<MappingChapter>> chapterMap = chapters.stream().collect(Collectors.groupingBy(MappingChapter::getChapter));
        for (Map.Entry<String, List<MappingChapter>> entry : chapterMap.entrySet()){
            HerolandChapterDP dp = new HerolandChapterDP();
            dp.setContentType(ChapterEnum.ZHANG.getType());
            dp.setContent(entry.getKey());
            dp.setParentId(0L);
            dp.setOrder(entry.getValue().get(0).getChapterorder());
            List<Integer> konwledges = entry.getValue().stream().map(MappingChapter::getKnowledgeid).distinct().collect(Collectors.toList());
            dp.setKnowledges(konwledges.stream().map(Integer::longValue).collect(Collectors.toList()));
            Integer editionid = entry.getValue().get(0).getEditionid();
            Integer gradeid = entry.getValue().get(0).getGradeid();
            Integer subjectid = entry.getValue().get(0).getSubjectid();
            dp.setGrade(transfer("GA", gradeid));
            dp.setEdition(transfer("ED", editionid));
            dp.setCourse(transfer("CU", subjectid));
            heroLandChapterService.add(dp);
        }
        return true;
    }



    public String transfer(String code, Integer mappingId){
        if (mappingId == null){
            return "";
        }

        if (code.equalsIgnoreCase("GA")){
            String str = mappingId+"";
            List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCodeAndMappingId(code, null);
            if (CollectionUtils.isEmpty(herolandBasicData)){
                return "";
            }
            Optional<HerolandBasicData> first = herolandBasicData.stream().filter(e -> {
                if (e.getMappingId().charAt(0) == str.charAt(0) && e.getMappingId().charAt(1) == str.charAt(1)) {
                    return true;
                } else {
                    return false;
                }
            }).findFirst();
            if (first.isPresent()){
                return first.get().getDictKey();
            }else {
                return "";
            }
        }
        List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCodeAndMappingId(code, mappingId + "");
        if (CollectionUtils.isEmpty(herolandBasicData) || herolandBasicData.size() > 1){
          return "";
        }else {
            return herolandBasicData.get(0).getDictKey();
        }
    }

    /**
     * 写入知识点
     * 但是知识点没有年级
     * 要从题目中抠出年级|从章节中抠出年级
     */
    public Boolean writeKnowledge(){
        MappingKnowledgeExample example = new MappingKnowledgeExample();
        MappingKnowledgeExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<MappingKnowledge> mappingKnowledges = mappingKnowledgeMapper.selectByExample(example);
        mappingKnowledges.stream().forEach(e -> {
            HerolandKnowledgeDP dp = new HerolandKnowledgeDP();
            dp.setId(e.getId().longValue());
            dp.setKnowledge(e.getKnowledgename());
//            dp.setGrade(transfer("GA", e.));
            dp.setCourse(transfer("CU", e.getSubjectid()));
            herolandKnowledgeService.add(dp);
        });
        return true;
    }


    /**
     * 写入题目
     *
     */
    public Boolean writeQusetion(){

        return true;
    }
}
