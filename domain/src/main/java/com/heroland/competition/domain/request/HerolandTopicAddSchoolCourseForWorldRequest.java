package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/18
 */
@Data
public class HerolandTopicAddSchoolCourseForWorldRequest implements Serializable {

    /**
     * 年级code
     */
    @NotNull
    private String gradeCode;


    /**
     * 科目code
     */
    @NotNull
    private String courseCode;

}
