package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandQuestionDP;
import com.heroland.competition.dal.pojo.HeroLandQuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeroLandQuestionMapper {
    long countByExample(HeroLandQuestionExample example);

    int deleteByExample(HeroLandQuestionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandQuestionDP record);

    int insertSelective(HeroLandQuestionDP record);

    List<HeroLandQuestionDP> selectByExample(HeroLandQuestionExample example);

    HeroLandQuestionDP selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HeroLandQuestionDP record, @Param("example") HeroLandQuestionExample example);

    int updateByExample(@Param("record") HeroLandQuestionDP record, @Param("example") HeroLandQuestionExample example);

    int updateByPrimaryKeySelective(HeroLandQuestionDP record);

    int updateByPrimaryKey(HeroLandQuestionDP record);
}