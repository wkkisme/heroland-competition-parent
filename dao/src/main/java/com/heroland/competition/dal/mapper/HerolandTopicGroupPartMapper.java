package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandTopicGroupPart;
import com.heroland.competition.dal.pojo.HerolandTopicGroupPartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HerolandTopicGroupPartMapper {
    long countByExample(HerolandTopicGroupPartExample example);

    int deleteByExample(HerolandTopicGroupPartExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HerolandTopicGroupPart record);

    int insertSelective(HerolandTopicGroupPart record);

    List<HerolandTopicGroupPart> selectByExample(HerolandTopicGroupPartExample example);

    HerolandTopicGroupPart selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HerolandTopicGroupPart record, @Param("example") HerolandTopicGroupPartExample example);

    int updateByExample(@Param("record") HerolandTopicGroupPart record, @Param("example") HerolandTopicGroupPartExample example);

    int updateByPrimaryKeySelective(HerolandTopicGroupPart record);

    int updateByPrimaryKey(HerolandTopicGroupPart record);

    int batchInsert(@Param("records") List<HerolandTopicGroupPart> list);
}