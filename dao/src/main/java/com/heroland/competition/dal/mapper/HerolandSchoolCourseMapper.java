package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandSchoolCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HerolandSchoolCourseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandSchoolCourse record);

    int insertSelective(HerolandSchoolCourse record);

    HerolandSchoolCourse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandSchoolCourse record);

    int updateByPrimaryKey(HerolandSchoolCourse record);

    int batchSave(@Param("records") List<HerolandSchoolCourse> records);

    void deleteBySchoolAndCourse(@Param("schoolCode") String schoolCode, @Param("courseId") Long courseId);

//    void deleteByCourse( @Param("courseId") Long courseId);

    List<HerolandSchoolCourse> getBySchoolListAndCourse(@Param("schoolCodes") List<String> schoolCodes, @Param("courseId") Long courseId);

    List<HerolandSchoolCourse> getByCourses(@Param("courseIds") List<Long> courseIds);
}