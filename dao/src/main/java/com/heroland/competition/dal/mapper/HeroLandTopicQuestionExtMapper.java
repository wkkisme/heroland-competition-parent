package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandTopicQuestion;
import com.heroland.competition.dal.pojo.HeroLandTopicQuestions;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;

import java.util.List;

public interface HeroLandTopicQuestionExtMapper extends HeroLandTopicQuestionMapper{

    int insertBash(List<HeroLandTopicQuestion> queryListConversion);

    List<HeroLandTopicQuestions> selectTopicQuestions(HeroLandQuestionQO topicIdCheck);

    Long selectTopicQuestionsCount(HeroLandQuestionQO qo);
}