package com.heroland.competition.service.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.ChapterEnum;
import com.heroland.competition.common.constants.KnowledgeReferEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.DateUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.*;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.dal.pojo.basic.HerolandKnowledge;
import com.heroland.competition.domain.dp.HeroLandQuestionDP;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandQuestionUniqDP;
import com.heroland.competition.domain.dto.*;
import com.heroland.competition.domain.qo.HeroLandTopicGroupQO;
import com.heroland.competition.domain.request.HeroLandTopicAssignRequest;
import com.heroland.competition.domain.request.HeroLandTopicQuestionForCourseRequest;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.admin.HeroLandAdminService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mac
 */
@Service
public class HeroLandQuestionServiceImpl implements HeroLandQuestionService {
    private final Logger logger = LoggerFactory.getLogger(HeroLandTopicGroupServiceImpl.class);

    @Resource
    private HeroLandTopicGroupMapper heroLandTopicGroupMapper;

    @Resource
    private HerolandTopicQuestionMapper herolandTopicQuestionMapper;

    @Resource
    private HerolandQuestionBankMapper herolandQuestionBankMapper;

    @Resource
    private HerolandKnowledgeReferMapper herolandKnowledgeReferMapper;

    @Resource
    private HerolandKnowledgeMapper herolandKnowledgeMapper;

    @Resource
    private HeroLandAdminService heroLandAdminService;

    @Resource
    private HerolandChapterMapper heroLandChapterMapper;

    @Resource
    private HerolandQuestionBankDetailMapper herolandQuestionBankDetailMapper;


    @Override
    public Boolean addQuestion(HeroLandQuestionDP dp) {
        return null;
    }

    @Override
    @Transactional
    public Boolean editTopic(HeroLandTopicGroupDP dp) {
        if (NumberUtils.nullOrZeroLong(dp.getId())){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        if (StringUtils.isAnyBlank(dp.getOrgCode(), dp.getTopicName())) {
            ResponseBodyWrapper.failParamException();
        }
        if (dp.getStartTime() == null || dp.getEndTime() == null){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_TIME.getErrorMessage());
        }
        if (dp.getStartTime().after(dp.getEndTime())){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_TIME.getErrorMessage());
        }

        if (dp.getType() == null) {
            ResponseBodyWrapper.failParamException();
        }
        HeroLandTopicGroup heroLandTopicGroup = BeanCopyUtils.copyByJSON(dp, HeroLandTopicGroup.class);
        return heroLandTopicGroupMapper.updateByPrimaryKeySelective(heroLandTopicGroup) > 0;
    }

    @Override
    @Transactional
    public Boolean deleteTopic(Long id) {
        heroLandTopicGroupMapper.deleteByPrimaryKey(id);
        herolandTopicQuestionMapper.deleteByTopicIds(Lists.newArrayList(id));
        return true;
    }

    @Override
    public Long addTopic(HeroLandTopicGroupDP dp) {
        if (!NumberUtils.nullOrZeroLong(dp.getId())){
            editTopic(dp);
            return dp.getId();
        }
        dp = dp.addCheckAndInit();
        HeroLandTopicGroup heroLandTopicGroup = BeanCopyUtils.copyByJSON(dp, HeroLandTopicGroup.class);
        heroLandTopicGroupMapper.insertSelective(heroLandTopicGroup);
        return heroLandTopicGroup.getId();
    }

    @Override
    public PageResponse<HeroLandQuestionListForTopicDto> getTopicQuestions(HeroLandTopicQuestionsPageRequest request) {
        PageResponse<HeroLandQuestionListForTopicDto> pageResult = new PageResponse<>();
        Page<HerolandTopicQuestion> questions = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                () -> herolandTopicQuestionMapper.selectByTopics(Lists.newArrayList(request.getTopicId())));
        if (CollectionUtils.isEmpty(questions.getResult())) {
            return pageResult;
        }

        List<Long> questionIds = questions.getResult().stream().map(HerolandTopicQuestion::getQuestionId).collect(Collectors.toList());
        List<HerolandQuestionBank> banks = herolandQuestionBankMapper.getByIdsWithDelete(questionIds);

        List<String> courseKeys = banks.stream().map(HerolandQuestionBank::getCourse).distinct().collect(Collectors.toList());
        List<String> gradeKeys = banks.stream().map(HerolandQuestionBank::getGradeCode).distinct().collect(Collectors.toList());
        courseKeys.addAll(gradeKeys);
        List<HerolandBasicDataDP> keys = heroLandAdminService.getDictInfoByKeys(courseKeys);
        Map<String, List<HerolandBasicDataDP>> keysMap = keys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));

        List<HeroLandQuestionListForTopicDto> topicDtos = Lists.newArrayList();
        List<String> qtIds = banks.stream().map(HerolandQuestionBank::getQtId).collect(Collectors.toList());
        List<Long> bankIds = banks.stream().map(HerolandQuestionBank::getId).collect(Collectors.toList());
        List<HerolandQuestionUniqDP> uniqDPS = herolandQuestionBankMapper.selectSimpleSnaphot(qtIds);
        Map<String, List<HerolandQuestionUniqDP>> qtSnapshotMap = uniqDPS.stream().collect(Collectors.groupingBy(HerolandQuestionUniqDP::getQtId));
        List<HerolandQuestionBankDetail> byQtId = herolandQuestionBankDetailMapper.getByQtId(bankIds);
        Map<Long, List<HerolandQuestionBankDetail>> qtMap = byQtId.stream().collect(Collectors.groupingBy(HerolandQuestionBankDetail::getQbId));
        banks.stream().forEach(e -> {
            HeroLandQuestionListForTopicDto dto = new HeroLandQuestionListForTopicDto();
            dto.setGrade(e.getGradeCode());
            dto.setCourse(e.getCourse());
            dto.setCourseName(keysMap.containsKey(e.getCourse()) ? keysMap.get(e.getCourse()).get(0).getDictValue() : "");
            dto.setGradeName(keysMap.containsKey(e.getGradeCode()) ? keysMap.get(e.getGradeCode()).get(0).getDictValue() : "");
            dto.setArea(e.getArea());
            dto.setDiff(e.getDiff());
            dto.setPaperType(e.getPaperType());
            dto.setTitle(e.getTitle());
            dto.setType(e.getType());
            dto.setSubType(e.getSubType());
            dto.setSource(e.getSource());
            dto.setYear(org.springframework.util.StringUtils.isEmpty(e.getYear()) ? "": DateUtils.dateToYear(e.getYear()));
            dto.setId(e.getId());
            dto.setQtId(e.getQtId());
            if (qtSnapshotMap.containsKey(e.getQtId())) {
                HerolandQuestionUniqDP max = qtSnapshotMap.get(e.getQtId()).stream().max(Comparator.comparing(HerolandQuestionUniqDP::getSnapshotNo)).get();
                if (!Objects.equals(max.getSnapshotNo(), e.getSnapshotNo())) {
                    dto.setNewQuestionId(max.getId());
                    dto.setDesc("UPDATED");
                } else {
                    dto.setDesc("NORMAL");
                }
            }else {
                dto.setDesc("DELETED");
            }
            if (qtMap.containsKey(e.getId())) {
                dto.setAnswer(qtMap.get(e.getId()).get(0).getAnswer());
                dto.setOptionAnswer(qtMap.get(e.getId()).get(0).getOptionAnswer());
                List<QuestionOptionDto> questionOptionDto = JSON.parseArray(qtMap.get(e.getId()).get(0).getOption(), QuestionOptionDto.class);
                dto.setOptions(questionOptionDto);
            }
            topicDtos.add(dto);

        });
        pageResult.setItems(topicDtos);
        pageResult.setPageSize(questions.getPageSize());
        pageResult.setPage(questions.getPageNum());
        pageResult.setTotal((int) questions.getTotal());
        return pageResult;

        }

    @Override
    public Boolean saveAssign(HeroLandTopicAssignRequest request) {

        herolandTopicQuestionMapper.deleteByTopicIds(Lists.newArrayList(request.getTopicId()));

        List<HerolandTopicQuestion> list = new ArrayList<>();
        request.getQuestionIds().stream().forEach(e -> {
            HerolandTopicQuestion topicQuestion = new HerolandTopicQuestion();
            topicQuestion.setQuestionId(e);
            topicQuestion.setTopicId(request.getTopicId());
            topicQuestion.setChapterId(0L);
            list.add(topicQuestion);
        });
        request.getChapterQuestions().stream().forEach(e -> {
            e.getQuestionIds().stream().forEach(ques -> {
                HerolandTopicQuestion topicQuestion = new HerolandTopicQuestion();
                topicQuestion.setQuestionId(ques);
                topicQuestion.setTopicId(request.getTopicId());
                topicQuestion.setChapterId(e.getChapterId());
                list.add(topicQuestion);
            });
        });
        List<HerolandTopicQuestion> uniq = list.stream().distinct().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(uniq)){
            return herolandTopicQuestionMapper.saveBatch(uniq) > 0;
        }
        return false;
    }

    @Override
    public HeroLandTopicDto getTopic(HeroLandTopicQuestionsPageRequest request) {
        if (NumberUtils.nullOrZeroLong(request.getTopicId())){
            return null;
        }
        HeroLandTopicGroup heroLandTopicGroup = heroLandTopicGroupMapper.selectByPrimaryKey(request.getTopicId());
        HeroLandTopicDto heroLandTopicDto = BeanCopyUtils.copyByJSON(heroLandTopicGroup, HeroLandTopicDto.class);
        List<String> keys = Lists.newArrayList();
        keys.add(heroLandTopicDto.getCourseCode());
        keys.add(heroLandTopicDto.getGradeCode());
        keys.add(heroLandTopicDto.getClassCode());
        keys.add(heroLandTopicDto.getOrgCode());
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(keys);
        Map<String, List<HerolandBasicDataDP>> keyMap = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        if (keyMap.containsKey(heroLandTopicDto.getCourseCode())){
            heroLandTopicDto.setCourseName(keyMap.get(heroLandTopicDto.getCourseCode()).get(0).getDictValue());
        }
        if (keyMap.containsKey(heroLandTopicDto.getGradeCode())){
            heroLandTopicDto.setGradeName(keyMap.get(heroLandTopicDto.getGradeCode()).get(0).getDictValue());
        }
        if (keyMap.containsKey(heroLandTopicDto.getClassCode())){
            heroLandTopicDto.setClassName(keyMap.get(heroLandTopicDto.getClassCode()).get(0).getDictValue());
        }
        if (keyMap.containsKey(heroLandTopicDto.getOrgCode())){
            heroLandTopicDto.setOrgCode(keyMap.get(heroLandTopicDto.getOrgCode()).get(0).getDictValue());
        }
        return heroLandTopicDto;
    }

    @Override
    public List<HeroLandQuestionTopicListForStatisticDto> getTopicQuestionForCourseStatistics(HeroLandTopicQuestionForCourseRequest request) {

       List<HeroLandQuestionTopicListForStatisticDto> list = new ArrayList<>();
        HeroLandTopicGroupQO qo = BeanCopyUtils.copyByJSON(request, HeroLandTopicGroupQO.class);
        List<HeroLandTopicGroup> heroLandTopicGroups = heroLandTopicGroupMapper.selectByQuery(qo);
        if (CollectionUtils.isEmpty(heroLandTopicGroups)){
            return list;
        }
        List<Long> topicIds = heroLandTopicGroups.stream().map(HeroLandTopicGroup::getId).collect(Collectors.toList());
        List<HerolandTopicQuestion> herolandTopicQuestions = herolandTopicQuestionMapper.selectByTopics(topicIds);
        Map<Long, List<HerolandTopicQuestion>> topicMap = herolandTopicQuestions.stream().collect(Collectors.groupingBy(HerolandTopicQuestion::getTopicId));
        heroLandTopicGroups.stream().forEach(e -> {
            HeroLandQuestionTopicListForStatisticDto dto = BeanCopyUtils.copyByJSON(e, HeroLandQuestionTopicListForStatisticDto.class);
            if (topicMap.containsKey(e.getId())){
                List<Long> question = topicMap.get(e.getId()).stream().map(HerolandTopicQuestion::getQuestionId).distinct().collect(Collectors.toList());
                dto.setQuestionNum(question.size());
            }
            list.add(dto);
        });

        List<Long> chapterIds = herolandTopicQuestions.stream().map(HerolandTopicQuestion::getChapterId).distinct().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(chapterIds)){
            List<HerolandChapter> chapters = heroLandChapterMapper.getByIds(chapterIds);
            Map<Long, List<HerolandChapter>> idMap = chapters.stream().collect(Collectors.groupingBy(HerolandChapter::getId));
            list.stream().forEach(e -> {
                if (topicMap.containsKey(e.getId())){
                    List<Long> chapter = topicMap.get(e.getId()).stream().map(HerolandTopicQuestion::getChapterId).distinct().collect(Collectors.toList());
                    chapter.stream().forEach(i -> {
                        if (idMap.containsKey(i) && ChapterEnum.ZHANG.getType().equals(idMap.get(i).get(0).getContentType())){
                            e.getChapterList().add(i);
                        }else {
                            e.getSectionList().add(i);
                        }
                    });

                }
            });
        }
        getAdminData(list);
        return list;
    }

    private void getAdminData(List<HeroLandQuestionTopicListForStatisticDto> list){
        List<String> classKeys = list.stream().map(HeroLandQuestionTopicListForStatisticDto::getClassCode).distinct().collect(Collectors.toList());
        List<String> orgKeys = list.stream().map(HeroLandQuestionTopicListForStatisticDto::getOrgCode).distinct().collect(Collectors.toList());
        List<String> courseKeys = list.stream().map(HeroLandQuestionTopicListForStatisticDto::getCourseCode).distinct().collect(Collectors.toList());
        List<String> gradeKeys = list.stream().map(HeroLandQuestionTopicListForStatisticDto::getGradeCode).distinct().collect(Collectors.toList());
        classKeys.addAll(gradeKeys);
        classKeys.addAll(orgKeys);
        classKeys.addAll(courseKeys);
        List<HerolandBasicDataDP> keys = heroLandAdminService.getDictInfoByKeys(classKeys);
        Map<String, List<HerolandBasicDataDP>> keyMap = keys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        list.stream().forEach(e -> {
            e.setCourseName(keyMap.containsKey(e.getCourseCode()) ? keyMap.get(e.getCourseCode()).get(0).getDictValue() : "");
            e.setGradeName(keyMap.containsKey(e.getGradeCode()) ? keyMap.get(e.getGradeCode()).get(0).getDictValue() : "");
            e.setClassName(keyMap.containsKey(e.getClassCode()) ? keyMap.get(e.getClassCode()).get(0).getDictValue() : "");
            e.setOrgCode(keyMap.containsKey(e.getOrgCode()) ? keyMap.get(e.getOrgCode()).get(0).getDictValue() : "");
        });
    }

    @Override
    public PageResponse<HeroLandQuestionTopicListForStatisticDto> getTopicQuestionForChapterStatistics(HeroLandTopicQuestionForCourseRequest request) {
        PageResponse<HeroLandQuestionTopicListForStatisticDto> pageResult = new PageResponse<>();
        List<HeroLandQuestionTopicListForStatisticDto> list = new ArrayList<>();
        pageResult.setItems(list);
        HeroLandTopicGroupQO qo = BeanCopyUtils.copyByJSON(request, HeroLandTopicGroupQO.class);
        Page<HeroLandTopicGroup> topicGroupsPageResult = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                () -> heroLandTopicGroupMapper.selectByQuery(qo));
        if (CollectionUtils.isEmpty(topicGroupsPageResult.getResult())) {
            return pageResult;
        }
        List<HeroLandTopicGroup> heroLandTopicGroups = topicGroupsPageResult.getResult();
        List<Long> topicIds = heroLandTopicGroups.stream().map(HeroLandTopicGroup::getId).collect(Collectors.toList());
        List<HerolandTopicQuestion> herolandTopicQuestions = herolandTopicQuestionMapper.selectByTopics(topicIds);
        Map<Long, List<HerolandTopicQuestion>> topicMap = herolandTopicQuestions.stream().collect(Collectors.groupingBy(HerolandTopicQuestion::getTopicId));
        heroLandTopicGroups.stream().forEach(e -> {
            HeroLandQuestionTopicListForStatisticDto dto = BeanCopyUtils.copyByJSON(e, HeroLandQuestionTopicListForStatisticDto.class);
            if (topicMap.containsKey(e.getId())){
                List<Long> questionIds = topicMap.get(e.getId()).stream().map(HerolandTopicQuestion::getQuestionId).distinct().collect(Collectors.toList());
                Map<Long, List<HerolandQuestionBank>> quesBank = herolandQuestionBankMapper.getByIdsWithDelete(questionIds).stream().collect(Collectors.groupingBy(HerolandQuestionBank::getId));
                dto.setQuestionNum(questionIds.size());
                List<HerolandKnowledgeRefer> refers = herolandKnowledgeReferMapper.selectByReferIds(questionIds, KnowledgeReferEnum.QUESTION.getType());
                Map<Long, List<HerolandKnowledgeRefer>> questionsMap = refers.stream().collect(Collectors.groupingBy(HerolandKnowledgeRefer::getReferId));
                questionIds.stream().forEach(qId -> {
                    HerolandQuestionKnowledgeSimpleDto knowledgeSimpleDto = new HerolandQuestionKnowledgeSimpleDto();
                    knowledgeSimpleDto.setQuestionId(qId);
                    if (questionsMap.containsKey(qId)){
                        knowledgeSimpleDto.setDiff(quesBank.containsKey(qId) ? quesBank.get(qId).get(0).getDiff() : 1);
                        knowledgeSimpleDto.setType(quesBank.containsKey(qId) ? quesBank.get(qId).get(0).getType() : 1);
                        List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByIds(questionsMap.get(qId).stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList()));
                        knowledgeSimpleDto.setKnowledge(herolandKnowledges.stream().map(HerolandKnowledge::getKnowledge).collect(Collectors.toList()));
                    }
                });
            }
            list.add(dto);
        });
        getAdminData(list);
        pageResult.setItems(list);
        pageResult.setPageSize(topicGroupsPageResult.getPageSize());
        pageResult.setPage(topicGroupsPageResult.getPageNum());
        pageResult.setTotal((int) topicGroupsPageResult.getTotal());
        return pageResult;
    }

}
