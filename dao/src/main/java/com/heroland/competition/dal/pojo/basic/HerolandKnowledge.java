package com.heroland.competition.dal.pojo.basic;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;

@Data
public class HerolandKnowledge extends BaseDO implements Serializable {

    /**
     * 年级
     */
    private String grade;

    /**
     * 科目
     */
    private String course;

    /**
     * 知识点
     */
    private String knowledge;

//    /**
//     * 是否是页子级别
//     */
//    private Boolean isRoot;
//
//    /**
//     * 根级别知识点
//     */
//    private Long rootKnowledgeId;
//
//    /**
//     * 父级别知识点
//     */
//    private Long parentKnowledgeId;

    /**
     * 知识点难度
     */
    private Integer diff;


    private String mappingId;

}