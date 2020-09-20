package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/18
 */
@Data
public class HerolandCourseSimpleDto implements Serializable {


    /**
     * 科目code
     */
    private String course;

    /**
     * 科目名称
     */
    private String courseName;

    /**
     * 该科目下比赛是否已完成
     */
    private Boolean hasFinished;



}
