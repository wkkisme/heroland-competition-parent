package com.heroland.competition.dal.pojo.basic;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;

@Data
public class HerolandChapter extends BaseDO implements Serializable {

    /**
     * 年级
     */
    private String grade;
//    private String gradeName;

    /**
     * 科目
     */
    private String course;
//    private String courseName;

    /**
     * 版本
     */
    private String edition;
//    private String editionName;

    /**
     * 章
     */
    private String chapter;

    /**
     * 单元
     */
    private String unit;

    /**
     * 节
     */
    private String section;

    /**
     * 章顺序
     */
    private Integer chapterOrder;

    /**
     * 单元顺序
     */
    private Integer unitOrder;

    /**
     * 节顺序
     */
    private Integer sectionOrder;

    /**
     * 难度 （章节的难度和知识点的难度单独定义）
     */
    private Integer diff;


}