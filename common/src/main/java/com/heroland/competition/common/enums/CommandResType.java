package com.heroland.competition.common.enums;

public enum CommandResType {

    /**
     * 处理成功
     */
    SUCCESS(0, "处理成功"),

    /**
     * 客户端上线失败
     */
    ONLINE_FAILED(1, "客户端上线失败"),

    /**
     * 刷新用户列表
     */
    REFRESH_ONLINE_USER(2, "刷新用户列表"),

    /**
     * 客户端下线失败
     */
    OFF_ONLINE_FAILED(3, "客户端下线失败"),

    /**
     * 服务端发送的文本消息
     */
    TEXT_MSG(4, "服务端发送的文本消息"),

    /**
     * 服务端发起的PING请求
     */
    PING(5, "服务端发起的PING请求"),

    /**
     * 参数校验失败
     */
    VALID_PARAMS_FAILED(6, "参数校验失败"),

    /**
     * 邀请失败
     */
    INVITE_FAILED(7, "邀请失败"),

    /**
     * 系统发生未知异常
     */
    SERVER_ERROR(8, "系统发生未知异常"),

    /**
     * 回应邀请失败
     */
    RESPOND_INVITE_FAILED(9, "回应邀请失败"),

    /**
     * 开始答题失败
     */
    START_ANSWER_FAILED(10, "开始答题失败"),

    /**
     * 用户被邀请
     */
    BE_INVITE(11, "被邀请"),

    /**
     * 邀请结果
     */
    INVITE_CANCEL(12, "拒绝邀请"),

    /**
     * 答应邀请
     */
    INVITE_AGREE(13, "答应邀请"),

    /**
     * 答题 失败
     */
    ANSWER_FAILED(14, "答题失败"),

    /**
     * 结束比赛失败
     */
    STOP_ANSWER_FAILED(15, "结束比赛失败"),

    /**
     * 发起邀请成功
     */
    START_INVITE_SUCCESS(16, "发起邀请成功"),

    /**
     * 上线成功
     */
    ONLINE_SUCCESS(17, "上线成功"),

    /**
     * 开始答题成功
     */
    START_ANSWER_SUCCESS(18, "开始答题成功"),

    /**
     * 有用户上线，需要刷新列表
     */
    ONLINE_SUCCESS_REFRESH(19, "有用户上线，需要刷新列表"),

    /**
     * 结束比赛成功
     */
    STOP_ANSWER_SUCCESS(20, "结束比赛成功"),

    /**
     * 对手已结束比赛
     */
    OPPONENT_USER_STOP_ANSWER(21, "对手已结束比赛"),
    ;

    private final int code;

    private final String desc;

    CommandResType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CommandResType getCommandType(int code) {
        for (CommandResType value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
