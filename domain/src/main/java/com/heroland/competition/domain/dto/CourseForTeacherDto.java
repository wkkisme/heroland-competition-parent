package com.heroland.competition.domain.dto;

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

    private List<SchoolCourseForTeacherDto> course;
}
