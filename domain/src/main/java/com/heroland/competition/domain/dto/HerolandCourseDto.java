package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/18
 */
@Data
public class HerolandCourseDto implements Serializable {

    private Long id;

    /**
     * 年级code
     */
    private String grade;


    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 上|下
     * 1 上，2 下
     */
    private Integer unit;

    /**
     * 科目code
     */
    private String course;

    /**
     * 科目名称
     */
    private String courseName;

    /**
     * 版本code
     */
    private String edition;
    /**
     * 版本名称
     */
    private String editionName;

    /**
     * 在教材中的必修|选修code
     *
     */
    private String editionType;
    /**
     * 在教材中的必修|选修
     * 如必修1，选修2-1等
     */
    private String editionTypeName;

    /**
     * 简介
     */
    private String description;

    /**
     * 所分配学校
     */
    private List<HerolandSchoolDto> schoolDtoList;



}
