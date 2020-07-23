package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandStatisticsTotal;
import com.heroland.competition.dal.pojo.HeroLandStatisticsTotalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeroLandStatisticsTotalMapper {
    long countByExample(HeroLandStatisticsTotalExample example);

    int deleteByExample(HeroLandStatisticsTotalExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandStatisticsTotal record);

    int insertSelective(HeroLandStatisticsTotal record);

    List<HeroLandStatisticsTotal> selectByExample(HeroLandStatisticsTotalExample example);

    HeroLandStatisticsTotal selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HeroLandStatisticsTotal record, @Param("example") HeroLandStatisticsTotalExample example);

    int updateByExample(@Param("record") HeroLandStatisticsTotal record, @Param("example") HeroLandStatisticsTotalExample example);

    int updateByPrimaryKeySelective(HeroLandStatisticsTotal record);

    int updateByPrimaryKey(HeroLandStatisticsTotal record);
}