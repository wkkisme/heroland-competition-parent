package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandQuestion;
import com.heroland.competition.dal.pojo.HeroLandQuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeroLandQuestionMapper {
    long countByExample(HeroLandQuestionExample example);

    int deleteByExample(HeroLandQuestionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandQuestion record);

    int insertSelective(HeroLandQuestion record);

    List<HeroLandQuestion> selectByExample(HeroLandQuestionExample example);

    HeroLandQuestion selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HeroLandQuestion record, @Param("example") HeroLandQuestionExample example);

    int updateByExample(@Param("record") HeroLandQuestion record, @Param("example") HeroLandQuestionExample example);

    int updateByPrimaryKeySelective(HeroLandQuestion record);

    int updateByPrimaryKey(HeroLandQuestion record);
}