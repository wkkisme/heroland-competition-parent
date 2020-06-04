package com.heroland.competition.common.constant;

public enum CalculateTypeEnum {
    TYPE_1("1", "填写"),
    TYPE_2("2", "普通计算"),
    TYPE_3("3", "特殊计算"),
    TYPE_4("4", "中间计算"),
    TYPE_99("99", "无需计算");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    CalculateTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
