package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandTopicQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HerolandTopicQuestionExtMapper  extends  HerolandTopicQuestionMapper{

    List<HerolandTopicQuestion> countAll();
}