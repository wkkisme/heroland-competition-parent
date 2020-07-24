package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandTopicQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface HerolandTopicQuestionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandTopicQuestion record);

    int insertSelective(HerolandTopicQuestion record);

    HerolandTopicQuestion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandTopicQuestion record);

    int updateByPrimaryKey(HerolandTopicQuestion record);

    void deleteByTopicIds(@Param("topicIds") List<Long> topicIds);

    List<HerolandTopicQuestion> selectByTopics(@Param("topicIds") List<Long> topicIds);

    int saveBatch(@Param("records") List<HerolandTopicQuestion> list);
}