package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandStatisticsWord;
import com.heroland.competition.dal.pojo.HerolandStatisticsWordExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HerolandStatisticsWordMapper {
    long countByExample(HerolandStatisticsWordExample example);

    int deleteByExample(HerolandStatisticsWordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HerolandStatisticsWord record);

    int insertSelective(HerolandStatisticsWord record);

    List<HerolandStatisticsWord> selectByExample(HerolandStatisticsWordExample example);

    HerolandStatisticsWord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HerolandStatisticsWord record, @Param("example") HerolandStatisticsWordExample example);

    int updateByExample(@Param("record") HerolandStatisticsWord record, @Param("example") HerolandStatisticsWordExample example);

    int updateByPrimaryKeySelective(HerolandStatisticsWord record);

    int updateByPrimaryKey(HerolandStatisticsWord record);

    List<String> distinctCourseInTime(@Param("userId") String userId,@Param("beginTime") Date beginTime,@Param("endTime") Date endTime);

    int batchInsert(@Param("items") List<HerolandStatisticsWord> herolandStatisticsWords);


}