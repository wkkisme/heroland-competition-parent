package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandQuestion;

import java.util.List;

/**
 * @author mac
 */
public interface HeroLandQuestionExtMapper extends HeroLandQuestionMapper {
    List<HeroLandQuestion> selectByTopicIds(List<String> topicIds);
}