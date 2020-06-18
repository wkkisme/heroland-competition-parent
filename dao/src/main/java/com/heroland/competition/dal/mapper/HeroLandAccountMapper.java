package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandAccountDP;
import com.heroland.competition.dal.pojo.HeroLandAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeroLandAccountMapper {
    long countByExample(HeroLandAccountExample example);

    int deleteByExample(HeroLandAccountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandAccountDP record);

    int insertSelective(HeroLandAccountDP record);

    List<HeroLandAccountDP> selectByExample(HeroLandAccountExample example);

    HeroLandAccountDP selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HeroLandAccountDP record, @Param("example") HeroLandAccountExample example);

    int updateByExample(@Param("record") HeroLandAccountDP record, @Param("example") HeroLandAccountExample example);

    int updateByPrimaryKeySelective(HeroLandAccountDP record);

    int updateByPrimaryKey(HeroLandAccountDP record);
}