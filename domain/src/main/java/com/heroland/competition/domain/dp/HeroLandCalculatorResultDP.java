package com.heroland.competition.domain.dp;


import com.anycommon.response.common.BaseDO;

import java.io.Serializable;

/**
 * 计算结果
 * @author wangkai
 */
public class HeroLandCalculatorResultDP extends BaseDO implements Serializable {

    /**
     * 我的等级
     */
    private String inviteLevel;


    /**
     * 我的得分
     */
    private Integer inviteScore;

    /**
     * 对手等级
     */
    private String opponentLevel;


    /**
     * 对手得分
     */
    private Integer opponentScore;

    public String getInviteLevel() {
        return inviteLevel;
    }

    public void setInviteLevel(String inviteLevel) {
        this.inviteLevel = inviteLevel;
    }

    public Integer getInviteScore() {
        return inviteScore;
    }

    public void setInviteScore(Integer inviteScore) {
        this.inviteScore = inviteScore;
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
