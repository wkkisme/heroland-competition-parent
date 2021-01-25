package com.heroland.competition.domain.request;

import com.google.common.collect.Lists;
import com.heroland.competition.domain.dto.QuestionOptionDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class HerolandQuestionBankRequest implements Serializable {

    /**
     *题目id，非主键id
     *
     */
    private String qtId;

    /**
     * 题目id
     * 当前的主键id
     */
    private Long id;


    /**
     * 不知道是啥
     * 可暂时不管
     */
    private Long passageId;

    /**
     * 不知道是啥
     * 可暂时不管
     */
    private String passage;



    /**
     * 年级
     */
    private String grade;

    /**
     * 科目
     */
    private String course;

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
    private List<QuestionOptionDto> options = Lists.newArrayList();

    /**
     * 选项答案|填空题答案
     */
    private String optionAnswer;

    /**
     * 胡乱答案
     */
    private String stormAnswer;

    /**
     * 答案解析
     */
    private String analysis;


    /**
     * 解答题答案
     */
    private String answer;

    /**
     * 解析（特殊场景）
     */
    private String parse;

    /**
     * 题库类型
     */
    private String storage;

    /**
     * 思维
     */
    private Integer think;

    /**
     * 示例
     */
    private String information;

    /**
     * 题库类型
     * 为赛事而区分
     */
    private Integer bankType;

    /**
     * 相似题id
     * qtId 多个用逗号隔开
     */
    private String optionZ;

    /**
     * 关联知识点
     */
    private List<Long> knowledges = Lists.newArrayList();

    /**
     * 相似题，记录的是 qtId
     */
    private List<String> similarQt = Lists.newArrayList();

}