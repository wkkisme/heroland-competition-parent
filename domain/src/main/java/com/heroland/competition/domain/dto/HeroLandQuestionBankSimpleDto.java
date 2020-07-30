package com.heroland.competition.domain.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/12
 */
@Data
public class HeroLandQuestionBankSimpleDto implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 年级
     */
    private String grade;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 科目
     */
    private String course;

    /**
     * 科目名称
     */
    private String courseName;

    /**
     * 题目
     */
    private String title;

    /**
     * 题目类型
     * OPTION(1,"选择题"),
     *     OPTION_ONE(11,"单选题"),
     *     OPTION_MULTI(12,"多选题"),
     *     FILL(2,"填空题"),
     *     ANSWER(3,"解答题"),
     *     COMPO(4,"综合题"),
     *
     */
    private Integer type;

    /**
     * 题目的子类型，比如选择题的子类型有多选|单选
     */
    private Integer subType;

    /**
     * 难度
     * ESAY(1,"容易"),
     *     HALF_ESAY(2,"较易"),
     *     MEDIUM(3,"中等"),
     *     HALF_DIFFICULT(4,"较难"),
     *     DIFFICULT(5,"难"),
     */
    private Integer diff;

    /**
     * 年份 如2006
     */
    private String year;

    /**
     * 地区 如上海市
     */
    private String area;

    /**
     * 来源 如2016年上海市闵行区中考数学一模试卷
     */
    private String source;

    /**
     * 试题类型
     */
    private Integer paperType;


    /**
     * 思维类型
     * 1是分析、2是推理、3是歸納
     */
    private Integer think;

    /**
     * 关联的知识点列表
     */
    private List<HerolandKnowledgeSimpleDto> knowledges = Lists.newArrayList();
}
