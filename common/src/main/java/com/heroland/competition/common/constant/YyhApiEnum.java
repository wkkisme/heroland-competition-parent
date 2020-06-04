package com.heroland.competition.common.constant;

public enum YyhApiEnum {

    DRIVER_MYSQL("mysql","mysql"),
    DRIVER_ORACLE("oracle","oracle"),
    DRIVER_ODPS("odps","odps");


    private String code;
    private String desc;

    private YyhApiEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static YyhApiEnum parse(String code) {
        for (YyhApiEnum yyhApiEnum : YyhApiEnum.values()) {
            if (yyhApiEnum.code.equalsIgnoreCase(code)){
                return yyhApiEnum;
            }
        }
        return null;
    }
}
