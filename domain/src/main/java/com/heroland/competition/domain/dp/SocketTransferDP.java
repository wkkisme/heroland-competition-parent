package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;

import java.io.Serializable;
import java.util.Set;

/**
 * socket传输领域模型
 * @author wangkai
 */
public class SocketTransferDP  extends BaseDO implements Serializable {
    /**
     * 操作类型
     */
    private Integer type;

    private String senderId;

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
     * 请求id
     */
    private String requestId;

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
     * 消息类型
     */
    private Integer topicType;

    /**
     * 最近一起玩的人
     */
    private Set<String> recent;


    /**
     * 状态
     */
    private Integer userStatus;
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getAddresseeId() {
        return addresseeId;
    }

    public void setAddresseeId(String addresseeId) {
        this.addresseeId = addresseeId;
    }

    public String getAddresseeName() {
        return addresseeName;
    }

    public void setAddresseeName(String addresseeName) {
        this.addresseeName = addresseeName;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getTopicType() {
        return topicType;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    public Set<String> getRecent() {
        return recent;
    }

    public void setRecent(Set<String> recent) {
        this.recent = recent;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}
