package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandSchoolCourse;

public interface HerolandSchoolCourseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandSchoolCourse record);

    int insertSelective(HerolandSchoolCourse record);

    HerolandSchoolCourse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandSchoolCourse record);

    int updateByPrimaryKey(HerolandSchoolCourse record);
}