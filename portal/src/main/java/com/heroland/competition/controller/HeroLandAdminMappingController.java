package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import com.anycommon.response.utils.BeanUtil;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.BankTypeEnum;
import com.heroland.competition.common.constants.ChapterEnum;

import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.*;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.dal.pojo.basic.HerolandBasicData;
import com.heroland.competition.dal.pojo.basic.HerolandKnowledge;
import com.heroland.competition.domain.dp.HerolandChapterDP;
import com.heroland.competition.domain.dp.HerolandKnowledgeDP;
import com.heroland.competition.domain.dp.HerolandQuestionBankImportDP;
import com.heroland.competition.domain.dto.HerolandChapterDto;
import com.heroland.competition.domain.dto.HerolandChapterSimpleDto;
import com.heroland.competition.domain.dto.HerolandKnowledgeSimpleDto;
import com.heroland.competition.domain.qo.HerolandKnowledgeQO;

import com.heroland.competition.service.admin.HeroLandChapterService;
import com.heroland.competition.service.admin.HeroLandQuestionBankService;
import com.heroland.competition.service.admin.HerolandKnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 章节管理
 */
@RestController
@RequestMapping("/mapping/chapter")
@Slf4j
public class HeroLandAdminMappingController {

    @Resource
    private HeroLandChapterService heroLandChapterService;

    @Resource
    private HerolandBasicDataMapper herolandBasicDataMapper;

    @Resource
    private MappingChapterMapper mappingChapterMapper;

    @Resource
    private HerolandChapterMapper herolandChapterMapper;

    @Resource
    private MappingKnowledgeMapper mappingKnowledgeMapper;

    @Resource
    private HerolandKnowledgeService herolandKnowledgeService;

    @Resource
    private HerolandKnowledgeMapper herolandKnowledgeMapper;

    @Resource
    private MappingQuestionsMapper mappingQuestionsMapper;

    @Resource
    private HeroLandQuestionBankService heroLandQuestionBankService;


    /***
     * 第二步刷数据：
     *
     *
     * 以下是处理章节的东西，
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */

    /**
     * 写入章节01
     *
     */
    @RequestMapping(value = "/writeChapter")
    public Boolean writeChapter(){
        MappingChapterExample example = new MappingChapterExample();
        MappingChapterExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<MappingChapter> mappingChapters = mappingChapterMapper.selectByExample(example);
        List<MappingChapter> chapters = mappingChapters.stream().filter(e -> !StringUtils.isBlank(e.getChapter())).collect(Collectors.toList());
        Map<String, List<MappingChapter>> chapterMap = chapters.stream().collect(Collectors.groupingBy(e -> e.getGradeid()+"_"+e.getSubjectid()+"_"+e.getEditionid()+"*"+e.getChapter() + "_"+e.getChapterorder()));
        for (Map.Entry<String, List<MappingChapter>> entry : chapterMap.entrySet()){
            HerolandChapterDP dp = new HerolandChapterDP();
            dp.setContentType(ChapterEnum.ZHANG.getType());
            dp.setContent(entry.getValue().get(0).getChapter());
            dp.setParentId(0L);
            dp.setOrder(entry.getValue().get(0).getChapterorder());
            Integer editionid = entry.getValue().get(0).getEditionid();
            Integer gradeid = entry.getValue().get(0).getGradeid();
            Integer subjectid = entry.getValue().get(0).getSubjectid();
            dp.setGrade(transfer("GA", gradeid));
            dp.setEdition(transfer("ED", editionid));
            dp.setCourse(transfer("CU", subjectid));
            dp.setGradeUnit(transferGradeUnit(gradeid));

            List<String> konwledges = entry.getValue().stream().map(MappingChapter::getKnowledgeid).map(String::valueOf).distinct().collect(Collectors.toList());
            List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectBymMappingIdsAndGrade(konwledges, dp.getGrade());
            if (!CollectionUtils.isEmpty(herolandKnowledges)){
                dp.setKnowledges(herolandKnowledges.stream().map(HerolandKnowledge::getId).collect(Collectors.toList()));
            }
            heroLandChapterService.add(dp);
        }
        return true;
    }

    /**
     * 写入章节02
     *
     */
    @RequestMapping(value = "/writeUnit")
    public Boolean writeUnit(){
        MappingChapterExample example = new MappingChapterExample();
        MappingChapterExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<MappingChapter> mappingChapters = mappingChapterMapper.selectByExample(example);
        List<MappingChapter> units = mappingChapters.stream().filter(e ->!StringUtils.isBlank(e.getUnit())).collect(Collectors.toList());
        Map<String, List<MappingChapter>> unitsMap = units.stream().collect(Collectors.groupingBy(e -> e.getGradeid()+"_"+e.getSubjectid()+"_"+e.getEditionid()+"*"+e.getChapter()+"*"+e.getUnit()+"_"+e.getChapterorder()+"_"+e.getUnitorder()));
        for (Map.Entry<String, List<MappingChapter>> entry : unitsMap.entrySet()){
            HerolandChapterDP dp = new HerolandChapterDP();
            dp.setContentType(ChapterEnum.KEJIE.getType());
            dp.setContent(entry.getValue().get(0).getUnit());
//            dp.setParentId(0L);
            dp.setOrder(entry.getValue().get(0).getUnitorder());
            Integer editionid = entry.getValue().get(0).getEditionid();
            Integer gradeid = entry.getValue().get(0).getGradeid();
            Integer subjectid = entry.getValue().get(0).getSubjectid();
            dp.setGrade(transfer("GA", gradeid));
            dp.setEdition(transfer("ED", editionid));
            dp.setCourse(transfer("CU", subjectid));
            dp.setGradeUnit(transferGradeUnit(gradeid));
            List<String> konwledges = entry.getValue().stream().map(MappingChapter::getKnowledgeid).map(String::valueOf).distinct().collect(Collectors.toList());
            List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectBymMappingIdsAndGrade(konwledges, dp.getGrade());
            if (!CollectionUtils.isEmpty(herolandKnowledges)){
                dp.setKnowledges(herolandKnowledges.stream().map(HerolandKnowledge::getId).collect(Collectors.toList()));
            }
            //从名字中找出chapter对应的名称
            List<HerolandChapter> chapters = herolandChapterMapper.getChapters(dp.getGrade(),dp.getGradeUnit(), dp.getCourse(), dp.getEdition(), 1, entry.getValue().get(0).getChapter(), entry.getValue().get(0).getChapterorder());
            if (!CollectionUtils.isEmpty(chapters)){
                dp.setParentId(chapters.get(0).getId());
            }
            heroLandChapterService.add(dp);
        }
        return true;
    }

    /**
     * 写入章节03
     *
     */
    @RequestMapping(value = "/writeSection")
    public Boolean writeSection(){
        MappingChapterExample example = new MappingChapterExample();
        MappingChapterExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<MappingChapter> mappingChapters = mappingChapterMapper.selectByExample(example);
        List<MappingChapter> sections = mappingChapters.stream().filter(e ->!StringUtils.isBlank(e.getSection())).collect(Collectors.toList());
        Map<String, List<MappingChapter>> sectionMap = sections.stream().collect(Collectors.groupingBy(e -> e.getGradeid()+"_"+e.getSubjectid()+"_"+e.getEditionid()+"*"+e.getChapter()+"*"+e.getUnit()+"*"+e.getSection()+"_"+e.getChapterorder()+"_"+e.getUnitorder() + "_"+e.getSectionorder()));
        for (Map.Entry<String, List<MappingChapter>> entry : sectionMap.entrySet()){
            HerolandChapterDP dp = new HerolandChapterDP();
            dp.setContentType(ChapterEnum.JIE.getType());
            dp.setContent(entry.getValue().get(0).getSection());
            dp.setOrder(entry.getValue().get(0).getSectionorder());
            Integer editionid = entry.getValue().get(0).getEditionid();
            Integer gradeid = entry.getValue().get(0).getGradeid();
            Integer subjectid = entry.getValue().get(0).getSubjectid();
            dp.setGrade(transfer("GA", gradeid));
            dp.setEdition(transfer("ED", editionid));
            dp.setCourse(transfer("CU", subjectid));
            dp.setGradeUnit(transferGradeUnit(gradeid));
            List<String> konwledges = entry.getValue().stream().map(MappingChapter::getKnowledgeid).map(String::valueOf).distinct().collect(Collectors.toList());
            List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectBymMappingIdsAndGrade(konwledges, dp.getGrade());
            if (!CollectionUtils.isEmpty(herolandKnowledges)){
                dp.setKnowledges(herolandKnowledges.stream().map(HerolandKnowledge::getId).collect(Collectors.toList()));
            }

            //从名字中找出chapter对应的名称
            List<HerolandChapter> chapters = herolandChapterMapper.getChapters(dp.getGrade(),dp.getGradeUnit(), dp.getCourse(), dp.getEdition(), 2, entry.getValue().get(0).getUnit(), entry.getValue().get(0).getUnitorder());
            if (!CollectionUtils.isEmpty(chapters)){
                Optional<HerolandChapter> first = chapters.stream().filter(e -> !NumberUtils.nullOrZeroLong(e.getParentId())).filter(e -> {
                    HerolandChapter chapter = herolandChapterMapper.selectByPrimaryKey(e.getParentId());
                    if (chapter != null && chapter.getOrder() != null && chapter.getOrder() == entry.getValue().get(0).getChapterorder()) {
                        return true;
                    }
                    return false;
                }).findFirst();
                if (first.isPresent()){
                    dp.setParentId(first.get().getId());
                }
            }
            heroLandChapterService.add(dp);
        }
        return true;
    }


    /***
     * 第一步刷数据：
     *
     *
     * 以下是处理知识点的东西，
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */

    /**
     * 01 写入知识点
     * 但是知识点没有年级
     * 要从题目中抠出年级|从章节中抠出年级
     */
    @RequestMapping(value = "/writeKnowledge")
    public Boolean writeKnowledge(){
        MappingKnowledgeExample example = new MappingKnowledgeExample();
        MappingKnowledgeExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<MappingKnowledge> mappingKnowledges = mappingKnowledgeMapper.selectByExample(example);
        mappingKnowledges.stream().forEach(e -> {
            HerolandKnowledge dp = new HerolandKnowledge();
            dp.setMappingId(e.getId()+"");
            dp.setKnowledge(e.getKnowledgename());
//            dp.setGrade(transfer("GA", e.));
            dp.setCourse(transfer("CU", e.getSubjectid()));
            herolandKnowledgeMapper.insertSelective(dp);
        });
        return true;
    }


    /**
     * 02 抠出年级和知识点的对应关系
     * 直接以章为依据
     *
     */
    @RequestMapping(value = "/digGradeAndKnowledge")
    public Boolean digGradeAndKnowledge() {
        MappingChapterExample example = new MappingChapterExample();
        MappingChapterExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<MappingChapter> mappingChapters = mappingChapterMapper.selectByExample(example);

        for (MappingChapter e : mappingChapters) {
            Integer knowledgeid = e.getKnowledgeid();
            String grade = transfer("GA", e.getGradeid());
            List<HerolandKnowledge> knowledges = herolandKnowledgeMapper.selectBymMappingId(knowledgeid + "");
            if (CollectionUtils.isEmpty(knowledges)) {
                continue;
            }
            Optional<HerolandKnowledge> first = knowledges.stream().filter(k -> Objects.equals(k.getGrade(), grade)).findFirst();
            if (first.isPresent()) {
                continue;
            }

            Optional<HerolandKnowledge> first1 = knowledges.stream().filter(k -> StringUtils.isBlank(k.getGrade())).findFirst();
            if (first1.isPresent()) {
                HerolandKnowledge knowledge = first1.get();
                knowledge.setGrade(grade);
                herolandKnowledgeMapper.updateByPrimaryKeySelective(knowledge);
            } else {
                HerolandKnowledge knowledge = knowledges.get(0);
                HerolandKnowledge knowledge1 = BeanCopyUtils.copyByJSON(knowledge, HerolandKnowledge.class);
                knowledge1.setId(null);
                knowledge1.setGrade(grade);
                herolandKnowledgeMapper.insertSelective(knowledge1);

            }

        }
        return true;
    }

    /**
     * 03 抠出年级和知识点的对应关系
     * 直接以题库为依据
     *
     */
    @RequestMapping(value = "/digGradeAndKnowledgeFromQues")
    public Boolean digGradeAndKnowledgeFromQues() {
        MappingQuestionsExample example = new MappingQuestionsExample();
        MappingQuestionsExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<MappingQuestionsWithBLOBs> mappingQuestions = mappingQuestionsMapper.selectByExampleWithBLOBs(example);
        mappingQuestions.stream().forEach(e -> {
            List<String> knowledges = Arrays.asList(e.getKnowledges().split(","));
            Byte subjectid = e.getSubjectid();
            Integer gradeid = e.getGradeid();
            HerolandKnowledgeQO qo = new HerolandKnowledgeQO();
            qo.setCourse(transfer("CU", subjectid.intValue()));
            qo.setGrade(transfer("GA", gradeid));
            knowledges.stream().forEach(k -> {
                qo.setKnowledge(k);
                List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByQuery2(qo);
                if (CollectionUtils.isEmpty(herolandKnowledges)){
                    HerolandKnowledge knowledge = new HerolandKnowledge();
                    knowledge.setGrade(qo.getGrade());
                    knowledge.setCourse(qo.getCourse());
                    knowledge.setKnowledge(k);
                    knowledge.setDiff(e.getDiff().intValue());
                    herolandKnowledgeMapper.insertSelective(knowledge);
                }else {
                    herolandKnowledges.stream().forEach(kk -> {
                        kk.setDiff(e.getDiff().intValue());
                        herolandKnowledgeMapper.updateByPrimaryKeySelective(kk);
                    });
                }
            });
        });
        return true;
    }


    /**
     *
     *
     *
     * 第三步刷数据：
     *      *
     *      *
     *      * 以下是处理题目的东西，
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */


    /**
     * 抠出题目中的知识点
     * 并将数据库中不存在的知识点保存进表中
     * 直接以章为依据
     *
     */
    @RequestMapping(value = "/writeQuestions")
    public Boolean writeQuestions(){
        MappingQuestionsExample example = new MappingQuestionsExample();
        MappingQuestionsExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<MappingQuestionsWithBLOBs> mappingQuestions = mappingQuestionsMapper.selectByExampleWithBLOBs(example);
        mappingQuestions.stream().forEach(e -> {
            HerolandQuestionBankImportDP importDP1 = build(e);
            HerolandQuestionBankImportDP importDP2 = BeanCopyUtils.copyByJSON(importDP1, HerolandQuestionBankImportDP.class);
            HerolandQuestionBankImportDP importDP3 = BeanCopyUtils.copyByJSON(importDP1, HerolandQuestionBankImportDP.class);
            HerolandQuestionBankImportDP importDP4 = BeanCopyUtils.copyByJSON(importDP1, HerolandQuestionBankImportDP.class);
            importDP2.setBankType(2);
            importDP3.setBankType(3);
            importDP4.setBankType(4);
            heroLandQuestionBankService.importQuestion(Lists.newArrayList(importDP1,importDP2,importDP3,importDP4));
        });
        return true;
    }


    private HerolandQuestionBankImportDP build(MappingQuestionsWithBLOBs e){
        HerolandQuestionBankImportDP importDP = new HerolandQuestionBankImportDP();
        List<Long> knowledgeIds = getKnowledgeIds(e);
        importDP.setBankType(1);
        importDP.setDiff(e.getDiff().intValue());
        importDP.setQtype(1);
        importDP.setAnswer1(e.getAnswer1());
        importDP.setAnswer2(e.getAnswer2());
        importDP.setParse(e.getParse());
        importDP.setSubjectid(transfer("CU", e.getSubjectid().intValue()));
        importDP.setGradeid(transfer("GA", e.getGradeid()));
        importDP.setOption_a(e.getOptionA());
        importDP.setOption_b(e.getOptionB());
        importDP.setOption_c(e.getOptionC());
        importDP.setOption_d(e.getOptionD());
        importDP.setOption_e(e.getOptionE());
        importDP.setQuestion(e.getTitle());
        importDP.setKnowledgeIds(knowledgeIds);
        return importDP;
    }


    private List<Long> getKnowledgeIds(MappingQuestionsWithBLOBs e){
        List<Long> knowledgeIds = Lists.newArrayList();
        List<String> knowledges = Arrays.asList(e.getKnowledges().split(","));
        Byte subjectid = e.getSubjectid();
        Integer gradeid = e.getGradeid();
        HerolandKnowledgeQO qo = new HerolandKnowledgeQO();
        qo.setCourse(transfer("CU", subjectid.intValue()));
        qo.setGrade(transfer("GA", gradeid));
        knowledges.stream().forEach(k -> {
            qo.setKnowledge(k);
            List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByQuery2(qo);
            if (!CollectionUtils.isEmpty(herolandKnowledges)){
                Optional<HerolandKnowledge> first = herolandKnowledges.stream().filter(kk -> Objects.equals(kk.getDiff(), e.getDiff().intValue())).findFirst();
                if (first.isPresent()){
                    knowledgeIds.add(first.get().getId());
                }
            }
        });

        return knowledgeIds;
    }














    //    @RequestMapping(value = "/mapping")
    public String transfer(String code, Integer mappingId){
        if (mappingId == null){
            return "";
        }

        if (code.equalsIgnoreCase("GA")){
            String str = mappingId+"";
            //说明是必修选修
            if (str.length() > 3){
                log.info("必修选修的年级");
                List<HerolandBasicData> herolandBasicData = herolandBasicDataMapper.selectByCodeAndMappingId(code, str);
                if (CollectionUtils.isEmpty(herolandBasicData)){
                    return "";
                }else {
                    return herolandBasicData.get(0).getDictKey();
                }
            }
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

    public Integer transferGradeUnit(Integer gradeid){
        if (gradeid == null){
            return null;
        }
        String str = gradeid+"";
        //说明是必修选修
        if (str.length() > 3){
            log.info("必修选修的年级");
            return null;
        }
        if (str.charAt(str.length()-1) == '1'){
            return 1;
        }
        if (str.charAt(str.length()-1) == '2'){
            return 2;
        }
        return null;
    }

}
