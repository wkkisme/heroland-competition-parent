package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class HerolandQuestionBankImportDP extends BaseDO implements Serializable {

    /**
     * 对应的导入时文件上显示的id
     * 在校验题目时可以通过这个字段告诉操作者是哪个题目校验不通过
     */
    private String importId;

    /**
     *
     */
    private Long passageid;

    /**
     * 暂时不落库
     */
    private String passage;

    /**
     * 题目名称
     */
    private String question;

    /**
     * 示例
     */
    private String information;

    /**
     * 选择题选项
     */
    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;
    private String option_e;

    /**
     * 相似题目
     */
    private String option_z;

    /**
     * 胡乱答案
     */
    private String answer0;

    /**
     * 答案
     */
    private String answer1;

    /**
     * 解析
     */
    private String answer2;

    /**
     * 解析2
     */
    private String parse;

    /**
     * 年级
     */
    private String gradeid;

    /**
     * 科目
     */
    private String subjectid;

    /**
     * 题库类型
     */
    private String storage;

    /**
     * 思维
     */
    private Integer think;

    /**
     * 题型
     */
    private Integer qtype;

    /**
     * 难度
     */
    private Integer diff;

    /**
     *
     * 暂时不落库
     */
    private String knowledges;

    /**
     * 知识点id
     */
    private String knowledgeId;

    /**
     * 引用，暂时不落库
     */
    private String reference;

    /**
     * 题库类型
     * 为赛事而区分
     */
    private Integer bankType;


}