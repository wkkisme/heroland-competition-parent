package com.heroland.competition.netty;


/**
 * heroland-competition
 *
 * @author wangkai
 * @date 2020/6/16
 */

public enum MsgActionEnum {
    /**
     * Websocket消息类型，WsDataContent.action
     */
    CONNECT(1, "客户端初始化建立连接"),
    KEEPALIVE(2, "客户端保持心跳"),
    MESSAGE_SIGN(3, "客户端连接请求-服务端响应-消息签收"),
    BREAK_OFF(4, "服务端主动断开连接"),
    BUSINESS(5, "服务端主动推送业务消息"),
    SEND_TO_SOMEONE(9, "发送消息给某人(用于通信测试)");

    public final Integer type;
    public final String content;

    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }
}
