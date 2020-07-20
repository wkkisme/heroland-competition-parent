package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.google.common.collect.Lists;
import com.heroland.competition.domain.dto.QuestionOptionDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HerolandQuestionBankDP extends BaseDO implements Serializable {

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
     * 选项答案
     */
    private String optionAnswer;

    /**
     * 解答题答案
     */
    private String answer;

    /**
     * 答案解析
     */
    private String parse;

    /**
     * 关联知识点
     */
    private List<Long> knowledges = Lists.newArrayList();


    //todo 补全
    public HerolandQuestionBankDP checkAndBuildBeforeCreate(){


        return this;
    }


}