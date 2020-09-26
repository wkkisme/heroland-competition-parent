package com.heroland.competition.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/18
 */
@Data
public class HerolandTopicAddSchoolCourseForWorldDto implements Serializable {

    /**
     * 年级code
     */
    private String gradeCode;

    /**
     * 年级name
     */
    private String gradeName;


    /**
     * 科目code
     */
    private String courseCode;

    /**
     * 科目name
     */
    private String courseName;

}
