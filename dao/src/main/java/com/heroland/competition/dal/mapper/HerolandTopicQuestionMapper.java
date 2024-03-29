package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandTopicQuestion;
import com.heroland.competition.domain.dp.QuestionTopicDP;
import com.heroland.competition.domain.qo.HerolandTopicQuestionQo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HerolandTopicQuestionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandTopicQuestion record);

    int insertSelective(HerolandTopicQuestion record);

    HerolandTopicQuestion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandTopicQuestion record);

    int updateByPrimaryKey(HerolandTopicQuestion record);

    void deleteByTopicIds(@Param("topicIds") List<Long> topicIds);

    List<HerolandTopicQuestion> selectByTopics(@Param("topicIds") List<Long> topicIds,
                                               @Param("questionId") Long questionId);

    List<HerolandTopicQuestion> selectByQo(HerolandTopicQuestionQo herolandTopicQuestionQo);

    int saveBatch(@Param("records") List<HerolandTopicQuestion> list);

    List<QuestionTopicDP> selectQuestionsByTopic(@Param("item") HerolandTopicQuestionQo herolandTopicQuestionQo);
}