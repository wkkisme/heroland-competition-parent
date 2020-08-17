package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 比赛结果查询
 *
 * @author wushuaiping
 * @date 2020/8/2 22:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerCompetitionResultDP extends BaseDO implements Serializable {

    /**
     * 胜负结果
     */
    private Integer result;

    /**
     * 总分
     */
    private Integer totalScore;

    /**
     * 答题结果详情
     */
    private List<AnswerDetail> answerDetails;

    @Data
    public static class AnswerDetail {

        /**
         *
         */
        private Integer useTime;

        /**
         * 得分
         */
        private Integer score;

        /**
         * 题型
         */
        private Integer diff;

        /**
         * 是否回答正确
         */
        private Boolean correctAnswer;
    }

}
