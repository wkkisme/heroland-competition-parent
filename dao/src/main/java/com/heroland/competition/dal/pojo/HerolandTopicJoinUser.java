package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value="com.heroland.competition.dal.pojo.HerolandTopicJoinUser")
public class HerolandTopicJoinUser extends BaseDO implements Serializable {
    /**
     * heroland_topic_group表的id
     */
    @ApiModelProperty(value="topicIdheroland_topic_group表的id")
    private Long topicId;

    /**
     * 赛事类型
     */
    @ApiModelProperty(value="topicType赛事类型")
    private Integer topicType;

    /**
     * 用户id
     */
    @ApiModelProperty(value="joinUser用户id")
    private String joinUser;

    /**
     * 报名userid
     */
    @ApiModelProperty(value="registerUser报名userid")
    private String registerUser;

    /**
     * 报名时间
     */
    @ApiModelProperty(value="registerTime报名时间")
    private Date registerTime;

    /**
     * 状态，参与，取消参与
     */
    @ApiModelProperty(value="state状态，参与，取消参与")
    private String state;

    /**
     * 取消人userid
     */
    @ApiModelProperty(value="cancelUser取消人userid")
    private String cancelUser;

    /**
     * 取消原因
     */
    @ApiModelProperty(value="cancelReason取消原因")
    private String cancelReason;

    /**
     * heroland_topic_join_user
     */
    private static final long serialVersionUID = 1L;

    /**
     * heroland_topic_group表的id
     * @return topic_id heroland_topic_group表的id
     */
    public Long getTopicId() {
        return topicId;
    }

    /**
     * heroland_topic_group表的id
     * @param topicId heroland_topic_group表的id
     */
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    /**
     * 赛事类型
     * @return topic_type 赛事类型
     */
    public Integer getTopicType() {
        return topicType;
    }

    /**
     * 赛事类型
     * @param topicType 赛事类型
     */
    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    /**
     * 用户id
     * @return join_user 用户id
     */
    public String getJoinUser() {
        return joinUser;
    }

    /**
     * 用户id
     * @param joinUser 用户id
     */
    public void setJoinUser(String joinUser) {
        this.joinUser = joinUser == null ? null : joinUser.trim();
    }

    /**
     * 报名userid
     * @return register_user 报名userid
     */
    public String getRegisterUser() {
        return registerUser;
    }

    /**
     * 报名userid
     * @param registerUser 报名userid
     */
    public void setRegisterUser(String registerUser) {
        this.registerUser = registerUser == null ? null : registerUser.trim();
    }

    /**
     * 报名时间
     * @return register_time 报名时间
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * 报名时间
     * @param registerTime 报名时间
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * 状态，参与，取消参与
     * @return state 状态，参与，取消参与
     */
    public String getState() {
        return state;
    }

    /**
     * 状态，参与，取消参与
     * @param state 状态，参与，取消参与
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * 取消人userid
     * @return cancel_user 取消人userid
     */
    public String getCancelUser() {
        return cancelUser;
    }

    /**
     * 取消人userid
     * @param cancelUser 取消人userid
     */
    public void setCancelUser(String cancelUser) {
        this.cancelUser = cancelUser == null ? null : cancelUser.trim();
    }

    /**
     * 取消原因
     * @return cancel_reason 取消原因
     */
    public String getCancelReason() {
        return cancelReason;
    }

    /**
     * 取消原因
     * @param cancelReason 取消原因
     */
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason == null ? null : cancelReason.trim();
    }
}