package com.heroland.competition.common.enums;

/**
 * @author smjyouzan
 * @date 2020/7/8
 */
public enum HerolandErrMsgEnum {

    EMPTY_PARAM("10000", "参数为空"),
    PARAM_ERROR("10001", "参数格式不对"),
    PARAM_DUP("10002", "数据库内已存在相同版本科目"),


    EMPTY_PAY("70001", "支付单不存在"),
    ERROR_PAY_STATE("70002", "支付状态错误"),
    ERROR_CURRENCY("70003", "暂不支持该汇率，请联系配置"),
    ERROR_DIMOND("70004", "暂不支持该类型宝石，请联系配置"),
    NO_DIMOND("70005", "无可用宝石充值"),


    ERROR_PARAM("70100", "同一类型下不支持相同编号"),

    ERROR_TIME("70101", "时间参数不正确"),

    ERROR_PARSE("70102", "解析错误"),

    HAS_JOINED("70103", "该用户已报名"),

    NOT_JOINED("70104", "该用户还未报名"),

    FUNDS_INSUFFICIENT("70105", "账户余额不足"),

    ERROR_UPDATE_PARAM_TYPE("70106", "比赛类型不能修改"),

    ERROR_UPDATE_PARAM_BEGIN("70107", "比赛已经开始，不支持修改"),

    ERROR_UPDATE_PARAM_END("70108", "比赛已经结束，不支持修改"),

    DUPLICATE("70109", "请稍后提交"),

    DUPLICATE_ORDER("70110", "下单过于频繁，请稍后重试"),


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
