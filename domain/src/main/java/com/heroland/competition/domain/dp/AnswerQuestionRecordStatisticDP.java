package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询比赛记录
 *
 * @author wushuaiping
 * @date 2020/7/25 23:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerQuestionRecordStatisticDP extends BaseDO implements Serializable {

    /**
     * 题号
     */
    private Long questionId;

    /**
     * 题组id
     */
    private Long topicId;

    /**
     * 题组名称
     */
    private String topicName;

    /**
     * 题目名称
     */
    private String questionTitle;

    /**
     * 知识点
     */
    private String knowledge;

    /**
     * 对手等级
     */
    private String opponentLevel;

    /**
     * 题的难度code
     */
    private String levelCode;

    /**
     * 是否回答正确
     */
    private Boolean isCorrectAnswer;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 题型
     */
    private Integer diff;

    /**
     * 比赛结果
     */
    private Integer result;

}
