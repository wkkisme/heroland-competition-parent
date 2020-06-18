package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandTopicQuestion;
import com.heroland.competition.dal.pojo.HeroLandTopicQuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeroLandTopicQuestionMapper {
    long countByExample(HeroLandTopicQuestionExample example);

    int deleteByExample(HeroLandTopicQuestionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandTopicQuestion record);

    int insertSelective(HeroLandTopicQuestion record);

    List<HeroLandTopicQuestion> selectByExample(HeroLandTopicQuestionExample example);

    HeroLandTopicQuestion selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HeroLandTopicQuestion record, @Param("example") HeroLandTopicQuestionExample example);

    int updateByExample(@Param("record") HeroLandTopicQuestion record, @Param("example") HeroLandTopicQuestionExample example);

    int updateByPrimaryKeySelective(HeroLandTopicQuestion record);

    int updateByPrimaryKey(HeroLandTopicQuestion record);
}