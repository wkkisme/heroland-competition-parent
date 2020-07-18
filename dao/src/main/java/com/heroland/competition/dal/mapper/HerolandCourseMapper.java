package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HerolandCourseMapper {
    int deleteByPrimaryKey(Long id);


    int insertSelective(HerolandCourse record);

    HerolandCourse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandCourse record);

//    int updateByPrimaryKey(HerolandCourse record);

    List<HerolandCourse> get(@Param("grade") String grade,@Param("gradeSlice") Integer gradeSlice,@Param("course") String course,@Param("edition") String edition,@Param("subType") String subType);

}