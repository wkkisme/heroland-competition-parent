package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandTopicGroup;
import com.heroland.competition.dal.pojo.HeroLandTopicGroupExample;
import java.util.List;

import com.heroland.competition.domain.qo.HeroLandTopicGroupQO;
import org.apache.ibatis.annotations.Param;

public interface HeroLandTopicGroupMapper {
    long countByExample(HeroLandTopicGroupExample example);

    int deleteByExample(HeroLandTopicGroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandTopicGroup record);

    int insertSelective(HeroLandTopicGroup record);

    List<HeroLandTopicGroup> selectByExample(HeroLandTopicGroupExample example);

    HeroLandTopicGroup selectByPrimaryKey(Long id);

    List<HeroLandTopicGroup> selectByPrimaryKeys(@Param("ids") List<Long> ids);

    int updateByExampleSelective(@Param("record") HeroLandTopicGroup record, @Param("example") HeroLandTopicGroupExample example);

    int updateByExample(@Param("record") HeroLandTopicGroup record, @Param("example") HeroLandTopicGroupExample example);

    int updateByPrimaryKeySelective(HeroLandTopicGroup record);

    int updateByPrimaryKey(HeroLandTopicGroup record);

    List<HeroLandTopicGroup> selectByQuery(@Param("item") HeroLandTopicGroupQO qo);
}