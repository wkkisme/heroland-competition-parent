package com.heroland.competition.common.enums;

/**
 * 比赛记录状态
 * @author mac
 */

public enum CompetitionStatusEnum {


    /**
     * 比赛状态 0比赛结束，1 比赛中 2 比赛未开始
     * 同意
     */
    FINISH(0),
    /**
     * 1 比赛中
     */
    COMPETING(1),

    /**
     * 2 比赛未开始
     */
    UN_START(2)
    ;

    private final Integer status;

    CompetitionStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

}
