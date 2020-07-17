package com.heroland.competition.common.enums;

/**
 * @author smjyouzan
 * @date 2020/7/8
 */
public enum HerolandErrMsgEnum {
    EMPTY_PAY("70001", "支付单不存在"),
    ERROR_PAY_STATE("70002", "支付状态错误"),
    ERROR_CURRENCY("70003", "暂不支持该汇率，请联系配置"),
    ERROR_DIMOND("70004", "暂不支持该类型宝石，请联系配置"),


    ERROR_PARAM("70100", "同一类型下不支持相同编号"),
   ;

    private String errorCode;
    private String errorMessage;

    private HerolandErrMsgEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
