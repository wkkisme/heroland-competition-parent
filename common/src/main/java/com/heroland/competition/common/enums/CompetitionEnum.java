package com.heroland.competition.common.enums;

public enum CompetitionEnum {

    /**
     * "type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 "
     */
    SYNC(0,"同步作业赛"), WINTER(1,"寒假作业赛"),
    SUMMER(2,"暑假作业赛"), EXAM(3,"应试赛"),
    SCHOOL(4,"校际赛"), WORLD(5,"世界赛");

    private final Integer type;
    private final String name;

    CompetitionEnum(Integer type,String name) {
        this.type = type;
        this.name =name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
