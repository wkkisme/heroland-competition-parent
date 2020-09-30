package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/9/30
 */
@Data
public class SchoolCourseForTeacherDto implements Serializable {

    private String schoolCode;


    private String schoolName;

    private String gradeCode;

    private String gradeName;

    private String classCode;

    private String className;

    private String courseCode;

    private String courseName;
}
