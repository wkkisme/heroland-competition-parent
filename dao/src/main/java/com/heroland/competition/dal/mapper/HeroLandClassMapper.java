package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandClass;
import com.heroland.competition.dal.pojo.HeroLandClassExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeroLandClassMapper {
    long countByExample(HeroLandClassExample example);

    int deleteByExample(HeroLandClassExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandClass record);

    int insertSelective(HeroLandClass record);

    List<HeroLandClass> selectByExample(HeroLandClassExample example);

    HeroLandClass selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HeroLandClass record, @Param("example") HeroLandClassExample example);

    int updateByExample(@Param("record") HeroLandClass record, @Param("example") HeroLandClassExample example);

    int updateByPrimaryKeySelective(HeroLandClass record);

    int updateByPrimaryKey(HeroLandClass record);
}