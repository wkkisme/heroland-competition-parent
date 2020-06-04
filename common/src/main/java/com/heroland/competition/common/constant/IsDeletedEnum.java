package com.heroland.competition.common.constant;

public enum IsDeletedEnum {
    NOT_DELETE(0, "未删除"),
    DELETED(1, "已删除");

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    IsDeletedEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
