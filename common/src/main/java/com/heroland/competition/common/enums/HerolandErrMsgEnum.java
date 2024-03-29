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
    PAYTOOL("70006", "暂不支持该支付方式"),



    ERROR_PARAM("70100", "学校编号必须唯一"),
    ERROR_NAME("70100", "同一基础数据类型下已存在相同名称数据"),

    ERROR_TIME("70101", "时间参数不正确"),
    ERROR_TIME_1("70101", "开始时间必须大于当前时间"),

    ERROR_PARSE("70102", "解析错误"),

    HAS_JOINED("70103", "该用户已报名, 请不要重复报名"),

    NOT_JOINED("70104", "该用户还未报名"),

    FUNDS_INSUFFICIENT("70105", "账户余额不足"),

    ERROR_UPDATE_PARAM_TYPE("70106", "比赛类型不能修改"),

    ERROR_UPDATE_PARAM_BEGIN("70107", "比赛已经开始，不支持修改"),

    ERROR_UPDATE_PARAM_END("70108", "比赛已经结束，不支持修改"),

    ERROR_UPDATE_PARAM_REGISTER("70111", "报名已经开始，不支持修改"),

    ERROR_DELETE_PARAM_REGISTER("70112", "报名或比赛已经开始，不支持删除"),

    ERROR_DELETE_PARAM_REGISTER2("70113", "距报名开始不足5分钟，不支持删除"),
    ERROR_QUERY_PARAM("70114", "比赛不存在"),
    ERROR_TIME_PARAM("70115", "创建同一年级的世界赛相差必须大于10分钟"),
    JOINED_LIMIT("70116", "报名人数已满"),
    ROLE_LIMIT("70117", "该行为此用户权限不足"),

    STATISTIC_DOING("70118", "比赛数据正在统计，请稍后查看"),
    STATISTIC_NOT("70119", "比赛还未结束, 不支持查看结果"),

    DUPLICATE("70109", "请稍后提交"),

    DUPLICATE_ORDER("70110", "下单过于频繁，请稍后重试"),


    ERROR_TITLE("70200", "题目信息不能为空"),
    ERROR_TYPE("70201", "题型不能为空"),
    ERROR_OPTION("70202", "题目选项不能为空"),
    ERROR_ANSWER("70203", "题目答案不能为空"),




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
