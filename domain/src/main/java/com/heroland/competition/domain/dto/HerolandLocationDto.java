package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/6
 */
@Data
public class HerolandLocationDto implements Serializable {

    /**
     * 地区key
     */
    private String areaKey;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 学校key
     */
    private String schoolKey;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 年级key
     */
    private String gradeKey;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 班级key
     */
    private String classKey;

    /**
     * 班级名称
     */
    private String className;


}
