package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/18
 */
@Data
public class HerolandTopicAddSchoolCourseRequest implements Serializable {

    /**
     * 学校code
     */
    @NotNull
    private String orgCode;


    /**
     * 年级code
     */
    @NotNull
    private String gradeCode;

    /**
     * 班级code
     */
    private String classCode;

    /**
     * 科目code
     */
    private String courseCode;

}
