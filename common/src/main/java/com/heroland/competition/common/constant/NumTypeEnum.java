package com.heroland.competition.common.constant;

public enum NumTypeEnum {
    TYPE_1("1", "整形"),
    TYPE_2("2", "浮点型（保留2位小数）");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    NumTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
