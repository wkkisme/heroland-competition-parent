package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandUserClass;
import com.heroland.competition.dal.pojo.HeroLandUserClassExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeroLandUserClassMapper {
    long countByExample(HeroLandUserClassExample example);

    int deleteByExample(HeroLandUserClassExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandUserClass record);

    int insertSelective(HeroLandUserClass record);

    List<HeroLandUserClass> selectByExample(HeroLandUserClassExample example);

    HeroLandUserClass selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HeroLandUserClass record, @Param("example") HeroLandUserClassExample example);

    int updateByExample(@Param("record") HeroLandUserClass record, @Param("example") HeroLandUserClassExample example);

    int updateByPrimaryKeySelective(HeroLandUserClass record);

    int updateByPrimaryKey(HeroLandUserClass record);
}