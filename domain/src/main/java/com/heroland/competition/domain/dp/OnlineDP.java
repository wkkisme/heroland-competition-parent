package com.heroland.competition.domain.dp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineDP implements Serializable {
    /**
     * 等级
     */
    private String level;

    /**
     * 是否正在比赛
     * true：正在比赛
     * false：等待比赛
     */
    private boolean isPlaying;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户所在题组id
     */
    private String topicId;

    /**
     * 操作类型
     */
    private int type;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 发送人id
     */
    private String senderId;

    /**
     * 发送人名称
     */
    private String senderName;

    /**
     * 收信人id
     */
    private String addresseeId;

    /**
     * 收信人名称
     */
    private String addresseeName;

    /**
     * 发送消息的时间戳
     */
    private Long sendTime;

    /**
     * 题组类型
     */
    private String topicType;

    /**
     * 最近一起玩的人
     */
    private Set<String> recent;


    /**
     * ip
     */
    private String ip;

    /**
     * port
     */
    private int port;

    /**
     * url
     */
    private String addr;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OnlineDP)) {
            return false;
        }
        OnlineDP onlineDP = (OnlineDP) o;
        return Objects.equals(topicId, onlineDP.topicId) &&
                Objects.equals(senderId, onlineDP.senderId) &&
                Objects.equals(topicType, onlineDP.topicType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicId, senderId, topicType);
    }
}
