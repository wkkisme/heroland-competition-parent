package com.heroland.competition.domain.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @date 2020/7/12
 */
@Data
public class HeroLandQuestionTopicListForStatisticDto implements Serializable {

    /**
     * id
     */
    private Long id;


    /**
     * 年级
     */
    private String gradeCode;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 科目
     */
    private String courseCode;

    /**
     * 科目名称
     */
    private String courseName;

    /**
     * 班级code
     */
    private String classCode;

    /**
     * 班级code
     */
    private String orgCode;

    /**
     * 题目数量
     */
    private Integer questionNum = 0;

    /**
     * 课的id集合
     */
    private List<Long> chapterList = Lists.newArrayList();

    /**
     * 节的集合
     */
    private List<Long> sectionList = Lists.newArrayList();



    /**
     * 关联的题号和知识点列表
     */
    private List<HerolandQuestionKnowledgeSimpleDto> knowledges = Lists.newArrayList();
}
