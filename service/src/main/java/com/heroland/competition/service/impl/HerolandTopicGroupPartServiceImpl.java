package com.heroland.competition.service.impl;

import com.google.common.collect.Lists;
import com.heroland.competition.dal.mapper.HerolandTopicGroupPartMapper;
import com.heroland.competition.dal.pojo.HerolandTopicGroupPart;
import com.heroland.competition.domain.dp.HerolandTopicGroupPartDP;
import com.heroland.competition.service.HerolandTopicGroupPartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Component
@Slf4j
public class HerolandTopicGroupPartServiceImpl implements HerolandTopicGroupPartService {

    @Resource
    private HerolandTopicGroupPartMapper herolandTopicGroupPartMapper;

    @Override
    public Boolean addBatchDepartment(List<HerolandTopicGroupPartDP> herolandTopicGroupPartDPs) {
        if (CollectionUtils.isEmpty(herolandTopicGroupPartDPs)){
            return false;
        }
        List<HerolandTopicGroupPart> list = Lists.newArrayList();
        herolandTopicGroupPartDPs.forEach(e -> {
            HerolandTopicGroupPart part = new HerolandTopicGroupPart();
            part.setTopicId(e.getTopicId());
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
        });
        return herolandTopicGroupPartMapper.batchInsert(list) > 0;
    }
}
