package com.heroland.competition.common.enums;

public enum CompetitionEnum {

    /**
     * "type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 "
     */
    SYNC(0), WINTER(1), SUMMER(2), EXAM(3), SCHOOL(4), WORLD(5);

    private final Integer type;

    CompetitionEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
