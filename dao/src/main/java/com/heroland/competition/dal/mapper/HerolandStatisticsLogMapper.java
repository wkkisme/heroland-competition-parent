package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandStatisticsLog;
import com.heroland.competition.dal.pojo.HerolandStatisticsLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HerolandStatisticsLogMapper {
    long countByExample(HerolandStatisticsLogExample example);

    int deleteByExample(HerolandStatisticsLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HerolandStatisticsLog record);

    int insertSelective(HerolandStatisticsLog record);

    List<HerolandStatisticsLog> selectByExample(HerolandStatisticsLogExample example);

    HerolandStatisticsLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HerolandStatisticsLog record, @Param("example") HerolandStatisticsLogExample example);

    int updateByExample(@Param("record") HerolandStatisticsLog record, @Param("example") HerolandStatisticsLogExample example);

    int updateByPrimaryKeySelective(HerolandStatisticsLog record);

    int updateByPrimaryKey(HerolandStatisticsLog record);

    int batchInsert(@Param("items") List<HerolandStatisticsLog> logList);
}