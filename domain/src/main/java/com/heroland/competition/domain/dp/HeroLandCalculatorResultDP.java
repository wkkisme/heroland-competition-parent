package com.heroland.competition.domain.dp;


/**
 * 计算结果
 */
public class HeroLandCalculatorResultDP {

    /**
     * 我的等级
     */
    private String myLevel;


    /**
     * 我的得分
     */
    private Integer myScore;

    /**
     * 对手等级
     */
    private String opponentLevel;


    /**
     * 对手得分
     */
    private Integer opponentScore;

    public String getMyLevel() {
        return myLevel;
    }

    public void setMyLevel(String myLevel) {
        this.myLevel = myLevel;
    }

    public Integer getMyScore() {
        return myScore;
    }

    public void setMyScore(Integer myScore) {
        this.myScore = myScore;
    }

    public String getOpponentLevel() {
        return opponentLevel;
    }

    public void setOpponentLevel(String opponentLevel) {
        this.opponentLevel = opponentLevel;
    }

    public Integer getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(Integer opponentScore) {
        this.opponentScore = opponentScore;
    }
}
