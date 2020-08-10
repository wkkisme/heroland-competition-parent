package com.heroland.competition.domain.dp;

import java.io.Serializable;

/**
 * 答题结果
 * @author mac
 */
public class HeroLandCompetitionResultDP implements Serializable {

    /**
     * 答对题数
     */
    private Integer rightCount;

    /**
     * 得分
     */
    private Integer score;

    public Integer getRightCount() {
        return rightCount;
    }

    public void setRightCount(Integer rightCount) {
        this.rightCount = rightCount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
