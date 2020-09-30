package com.heroland.competition.domain.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/9/30
 */
@Data
public class CourseForTeacherDto implements Serializable {

    private String userId;

    /**
     * 科目
     */
    private List<SchoolCourseForTeacherDto> course = Lists.newArrayList();

    /**
     * 班级
     */
    private List<SchoolCourseForTeacherDto> clazz = Lists.newArrayList();

    /**
     * 年级
     */
    private List<SchoolCourseForTeacherDto> grade = Lists.newArrayList();
}
