package com.heroland.competition.service.impl;

import com.google.common.collect.Lists;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.dal.mapper.HerolandTopicGroupPartMapper;
import com.heroland.competition.dal.pojo.HerolandTopicGroupPart;
import com.heroland.competition.dal.pojo.HerolandTopicGroupPartExample;
import com.heroland.competition.domain.dp.HerolandTopicGroupPartDP;
import com.heroland.competition.domain.dto.HeroLandTopicDto;
import com.heroland.competition.domain.dto.HeroLandTopicForSDto;
import com.heroland.competition.domain.qo.HerolandTopicForSQO;
import com.heroland.competition.domain.qo.HerolandTopicGroupGradeQO;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;
import com.heroland.competition.service.HeroLandQuestionService;
import com.heroland.competition.service.HeroLandTopicGroupService;
import com.heroland.competition.service.HerolandTopicGroupPartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Component
@Slf4j
public class HerolandTopicGroupPartServiceImpl implements HerolandTopicGroupPartService {

    @Resource
    private HerolandTopicGroupPartMapper herolandTopicGroupPartMapper;

    @Resource
    private HeroLandQuestionService heroLandQuestionService;

    @Override
    public Boolean addBatchDepartment(List<HerolandTopicGroupPartDP> herolandTopicGroupPartDPs) {
        if (CollectionUtils.isEmpty(herolandTopicGroupPartDPs)){
            return false;
        }
        List<HerolandTopicGroupPart> list = Lists.newArrayList();
        herolandTopicGroupPartDPs.forEach(e -> {
            HerolandTopicGroupPart part = new HerolandTopicGroupPart();
            part.setTopicId(e.getTopicId());
            part.setTopicType(e.getTopicType());
            part.setOrgCode(e.getOrgCode());
            part.setGradeCode(e.getGradeCode());
            part.setCourseCode(e.getCourseCode());
            part.setClassCode(e.getClassCode());
            if (StringUtils.isEmpty(part.getClassCode())){
                part.setClassCode("");
            }
            if (StringUtils.isEmpty(part.getCourseCode())){
                part.setCourseCode("");
            }
            list.add(part);
        });
        return herolandTopicGroupPartMapper.batchInsert(list) > 0;
    }

    @Override
    public Boolean deleteDepartment(List<Long> list) {
        if (CollectionUtils.isEmpty(list)){
            return true;
        }
        return herolandTopicGroupPartMapper.batchDeleteById(list) > 0;
    }

    @Override
    public Boolean deleteDepartmentBytopicIds(List<Long> list) {
        if (CollectionUtils.isEmpty(list)){
            return true;
        }
        return herolandTopicGroupPartMapper.batchDeleteByTopicIds(list) > 0;
    }


    @Override
    public List<HeroLandTopicForSDto> listDepartmentByGrades(HerolandTopicGroupGradeQO qo) {
        List<HeroLandTopicForSDto> result = Lists.newArrayList();
        HerolandTopicGroupPartExample example = new HerolandTopicGroupPartExample();
        example.createCriteria().andOrgCodeEqualTo(qo.getOrgCode()).andGradeCodeIn(qo.getGradeCodes());
        List<HerolandTopicGroupPart> topicGroupParts = herolandTopicGroupPartMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(topicGroupParts)){
            return Lists.newArrayList();
        }
        List<Long> topicIds = topicGroupParts.stream().map(HerolandTopicGroupPart::getTopicId).collect(Collectors.toList());
        HeroLandTopicQuestionsPageRequest request = new HeroLandTopicQuestionsPageRequest();
        request.setTopicIds(topicIds);
        List<HeroLandTopicDto> topics = heroLandQuestionService.getTopics(request);
        topics.stream().forEach(e -> {
            HeroLandTopicForSDto heroLandTopicForSDto = new HeroLandTopicForSDto();
            heroLandTopicForSDto.setTopicId(e.getTopicId());
            heroLandTopicForSDto.setStartTime(e.getStartTime());
            heroLandTopicForSDto.setEndTime(e.getEndTime());
            heroLandTopicForSDto.setTopicName(e.getTopicName());
            result.add(heroLandTopicForSDto);
        });

        return result;
    }

    @Override
    public List<HerolandTopicGroupPartDP> listPartByTopicIds(HerolandTopicForSQO qo) {

        List<HerolandTopicGroupPartDP> result = Lists.newArrayList();
        HerolandTopicGroupPartExample example = new HerolandTopicGroupPartExample();
        HerolandTopicGroupPartExample.Criteria criteria = example.createCriteria();
        if (!CollectionUtils.isEmpty(qo.getTopicIds())){
            criteria.andTopicIdIn(qo.getTopicIds());
        }
        if (!StringUtils.isEmpty(qo.getOrgCode())){
            criteria.andOrgCodeEqualTo(qo.getOrgCode());
        }
        if (!StringUtils.isEmpty(qo.getGradeCode())){
            criteria.andGradeCodeEqualTo(qo.getGradeCode());
        }
        if (!CollectionUtils.isEmpty(qo.getCourseCodes())){
            criteria.andCourseCodeIn(qo.getCourseCodes());
        }
        List<HerolandTopicGroupPart> topicGroupParts = herolandTopicGroupPartMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(topicGroupParts)){
            return Lists.newArrayList();
        }
        result = topicGroupParts.stream().map(e ->{
            HerolandTopicGroupPartDP partDP = BeanCopyUtils.copyByJSON(e, HerolandTopicGroupPartDP.class);
            return partDP;
        }).collect(Collectors.toList());
        return result;
    }
}
