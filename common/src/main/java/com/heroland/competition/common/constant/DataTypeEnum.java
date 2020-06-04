package com.heroland.competition.common.constant;

public enum DataTypeEnum {
    MONTH_1("1", "月数据"),
    YEAR_2("2", "年数据");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    DataTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
