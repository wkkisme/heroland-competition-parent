package com.heroland.competition.service.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.DateUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.*;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.domain.dp.HeroLandQuestionDP;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandQuestionUniqDP;
import com.heroland.competition.domain.dto.HeroLandQuestionListForTopicDto;
import com.heroland.competition.domain.dto.HeroLandTopicDto;
import com.heroland.competition.domain.dto.QuestionOptionDto;
import com.heroland.competition.domain.request.HeroLandTopicAssignRequest;
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
    private HeroLandAdminService heroLandAdminService;

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
    public Boolean addTopic(HeroLandTopicGroupDP dp) {
        if (!NumberUtils.nullOrZeroLong(dp.getId())){
            return editTopic(dp);
        }
        dp = dp.addCheckAndInit();
        HeroLandTopicGroup heroLandTopicGroup = BeanCopyUtils.copyByJSON(dp, HeroLandTopicGroup.class);

        return heroLandTopicGroupMapper.insertSelective(heroLandTopicGroup) > 0;
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
            list.add(topicQuestion);
        });
        return herolandTopicQuestionMapper.saveBatch(list) > 0;
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

}
