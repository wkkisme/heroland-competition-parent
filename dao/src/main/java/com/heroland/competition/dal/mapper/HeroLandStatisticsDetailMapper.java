package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HeroLandStatisticsDetail;
import com.heroland.competition.dal.pojo.HeroLandStatisticsDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeroLandStatisticsDetailMapper {
    long countByExample(HeroLandStatisticsDetailExample example);

    int deleteByExample(HeroLandStatisticsDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HeroLandStatisticsDetail record);

    int insertSelective(HeroLandStatisticsDetail record);

    List<HeroLandStatisticsDetail> selectByExample(HeroLandStatisticsDetailExample example);

    HeroLandStatisticsDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HeroLandStatisticsDetail record, @Param("example") HeroLandStatisticsDetailExample example);

    int updateByExample(@Param("record") HeroLandStatisticsDetail record, @Param("example") HeroLandStatisticsDetailExample example);

    int updateByPrimaryKeySelective(HeroLandStatisticsDetail record);

    int updateByPrimaryKey(HeroLandStatisticsDetail record);
}