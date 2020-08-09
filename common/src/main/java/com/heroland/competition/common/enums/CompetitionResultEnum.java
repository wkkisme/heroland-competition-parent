package com.heroland.competition.common.enums;

public enum CompetitionResultEnum {

    /**
     * 0 邀请方胜利。1 被邀请方胜利。2 平局且都答错
     */
    INVITE_WIN(0),BE_INVITE_WIN(1),DRAW(2);


    private final Integer result;

    CompetitionResultEnum(Integer result) {
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }
}
