package com.heroland.competition.common.enums;

import lombok.Getter;

/**
 * Function:常量
 *
 * @author crossoverJie
 * Date: 28/03/2018 17:41
 * @since JDK 1.8
 */
public class Constants {


    /**
     * 服务端手动 push 次数
     */
    public static final String COUNTER_SERVER_PUSH_COUNT = "counter.server.push.count";


    /**
     * 客户端手动 push 次数
     */
    public static final String COUNTER_CLIENT_PUSH_COUNT = "counter.client.push.count";


    /**
     * 自定义报文类型
     */
    @Getter
    public enum CommandReqType {

        /**
         * 客户端上线
         */
        ONLINE(1, "客户端上线"),

        /**
         * 客户端下线
         */
        OFF_ONLINE(2, "客户端下线"),

        /**
         * 客户端发送的文本消息
         */
        TEXT_MSG(3, "客户端发送的文本消息"),

        /**
         * 客户端发起的PING
         */
        PING(4, "客户端发起的PING"),

        /**
         * 邀请
         */
        INVITE(5, "邀请"),

        /**
         * 回应邀请
         */
        RESPOND_ACCEPT(6, "回应邀请"),

        /**
         * 开始答题
         */
        START_ANSWER_QUESTION(7, "开始答题"),

        /**
         * 答题
         */
        DO_ANSWER_QUESTIONS(8, "答题"),

        /**
         * 结束答题
         */
        STOP_ANSWER_QUESTIONS(9, "结束答题"),

        /**
         * 获取在线用户列表
         */
        GET_ONLINE_USERS(10, "获取在线用户列表"),

        /**
         * 获取对手是否答题完成
         */
        GET_OPPONENT_ANSWER_STATE(11, "获取对手是否答题完成"),
        ;

        private final int code;

        private final String desc;

        CommandReqType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static CommandReqType getCommandType(int code) {
            for (CommandReqType value : values()) {
                if (value.getCode() == code) return value;
            }
            return null;
        }
    }

    @Getter
    public enum CommandResType {

    }


}
