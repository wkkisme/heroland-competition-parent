package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandTopicGroup;
import com.heroland.competition.dal.pojo.HeroLandTopicGroupExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeroLandTopicGroupMapper {
    long countByExample(HeroLandTopicGroupExample example);

    int deleteByExample(HeroLandTopicGroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandTopicGroup record);

    int insertSelective(HeroLandTopicGroup record);

    List<HeroLandTopicGroup> selectByExample(HeroLandTopicGroupExample example);

    HeroLandTopicGroup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HeroLandTopicGroup record, @Param("example") HeroLandTopicGroupExample example);

    int updateByExample(@Param("record") HeroLandTopicGroup record, @Param("example") HeroLandTopicGroupExample example);

    int updateByPrimaryKeySelective(HeroLandTopicGroup record);

    int updateByPrimaryKey(HeroLandTopicGroup record);
}