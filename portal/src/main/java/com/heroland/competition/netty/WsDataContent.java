package com.heroland.competition.netty;


import java.io.Serializable;

/**
 * heroland-competition
 *
 * @author wangkai
 * @date 2020/6/16
 */

public class WsDataContent implements Serializable {

    /**
     * 消息类型
     */
    private Integer action;
    /**
     * msgId
     */
    private String msgId;
    /**
     * 发起连接需要的参数
     */
    private UserWebsocketSalt salt;
    /**
     * data
     */
    private Object data;

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public UserWebsocketSalt getSalt() {
        return salt;
    }

    public void setSalt(UserWebsocketSalt salt) {
        this.salt = salt;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
