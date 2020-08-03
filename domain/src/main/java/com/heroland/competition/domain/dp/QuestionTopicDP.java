package com.heroland.competition.domain.dp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wushuaiping
 * @date 2020/8/3 21:42
 */
@Data
public class QuestionTopicDP implements Serializable {

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 题目快照号
     */
    private Integer snapshotNo;

    /**
     * 题目类型
     */
    private Integer type;

    /**
     * 题目难度
     */
    private Integer diff;

    /**
     * 试题类型
     */
    private Integer paperType;

    /**
     * 题目科目
     */
    private String course;

    /**
     * 题目年级code
     */
    private String gradeCode;

    /**
     * 题组难度
     */
    private String topicLevelCode;

    /**
     * 题组名称
     */
    private String topicName;

    /**
     * 题组比赛类型
     */
    private Integer topicType;

    /**
     * 题组题型
     */
    private Integer topicDiff;

    /**
     * 知识点
     */
    private String knowledges;

    /**
     * 题组id
     */
    private Long topicId;
}
