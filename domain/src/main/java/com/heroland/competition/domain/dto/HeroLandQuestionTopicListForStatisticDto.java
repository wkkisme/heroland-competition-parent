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
     * 赛事名称
     */
    private String topicName;


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

    private String className;

    /**
     * 班级code
     */
    private String orgCode;


    private String orgName;

    /**
     * 题目数量
     */
    private Integer questionNum = 0;

    /**
     * 题型
     */
    private Integer diff;

    /**
     * 难度
     */
    private String levelCode;

    /**
     * 课的id集合
     */
    private List<Long> chapterList = Lists.newArrayList();

    /**
     * 节的集合
     */
    private List<Long> sectionList = Lists.newArrayList();

    /**
     * 题ID集合
     */
    private List<Long> questionIds = Lists.newArrayList();

    /**
     * 关联的题号和知识点列表
     */
    private List<HerolandQuestionKnowledgeSimpleDto> knowledges = Lists.newArrayList();

    /**
     * 关联的章节号和知识点列表
     */
//    private List<HerolandQuestionChapterSimpleDto> chapters = Lists.newArrayList();
}
