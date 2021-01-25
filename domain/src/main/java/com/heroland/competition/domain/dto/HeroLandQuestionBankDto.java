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
public class HeroLandQuestionBankDto implements Serializable {

    /**
     *题目id 编辑时必传
     */
    private Long id;


    /**
     * 题目全局id
     */
    private String qtId;

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
     */
    private Integer type;

    /**
     * 题目的子类型，比如选择题的子类型有多选|单选
     */
    private Integer subType;

    /**
     * 难度
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
     * 选项内容
     */
    private List<QuestionOptionDto> options;

    /**
     * 选项|填空题答案
     */
    private String optionAnswer;

    /**
     * 解答题答案
     * 暂无
     */
    private String answer;

    /**
     * 针对跨学科的解说
     */
    private String parse;

    /**
     * 答案解析
     */
    private String analysis;

    /**
     * 示例
     */
    private String information;

    /**
     * 思维
     */
    private Integer think;

    /**
     * 题库类型
     */
    private String storage;

    /**
     * 题库类型
     * 为赛事而区分
     */
    private Integer bankType;

    /**
     * 关联的知识点列表
     */
    private List<HerolandKnowledgeSimpleDto> knowledges = Lists.newArrayList();

    /**
     * 相似题id
     * qtId
     */
    private String optionZ;

    /**
     * 不知道是啥
     * 可暂时不管
     */
    private String passage;

    /**
     * 文章id
     */
    private Long passageId;
}
