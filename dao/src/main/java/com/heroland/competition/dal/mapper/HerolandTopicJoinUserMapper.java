package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandTopicJoinUser;
import com.heroland.competition.dal.pojo.HerolandTopicJoinUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HerolandTopicJoinUserMapper {
    long countByExample(HerolandTopicJoinUserExample example);

    int deleteByExample(HerolandTopicJoinUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HerolandTopicJoinUser record);

    int insertSelective(HerolandTopicJoinUser record);

    List<HerolandTopicJoinUser> selectByExample(HerolandTopicJoinUserExample example);

    HerolandTopicJoinUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HerolandTopicJoinUser record, @Param("example") HerolandTopicJoinUserExample example);

    int updateByExample(@Param("record") HerolandTopicJoinUser record, @Param("example") HerolandTopicJoinUserExample example);

    int updateByPrimaryKeySelective(HerolandTopicJoinUser record);

    int updateByPrimaryKey(HerolandTopicJoinUser record);

    int batchInsert(@Param("items") List<HerolandTopicJoinUser> list);
}