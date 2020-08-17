package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.HerolandTopicJoinUser")
public class HerolandTopicJoinUser extends BaseDO implements Serializable {
    /**
     * heroland_topic_group表的id
     */
    @ApiModelProperty(value="topicIdheroland_topic_group表的id")
    private Long topicId;

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