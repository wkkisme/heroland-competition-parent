package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandTopicGroup;
import com.heroland.competition.domain.qo.HeroLandTopicGroupQO;

import java.util.List;

public interface HeroLandTopicGroupExtMapper extends HeroLandTopicGroupMapper {

    List<HeroLandTopicGroup> selectTopicAndQuestions(HeroLandTopicGroupQO qo);
}