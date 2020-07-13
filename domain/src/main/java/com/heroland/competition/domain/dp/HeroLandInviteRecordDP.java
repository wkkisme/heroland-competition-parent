package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ApiModel(value = "com.heroland.competition.dal.pojo.HeroLandInviteRecord")
public class HeroLandInviteRecordDP extends BaseDO implements Serializable {
    /**
     * 记录id
     */
    @ApiModelProperty(value = "recordId记录id")
    private String recordId;

    /**
     * 题组名称
     */
    @ApiModelProperty(value = "topicName题组名称")
    private String topicName;

    /**
     * 邀请人id
     */
    @ApiModelProperty(value = "inviteUserId邀请人id")
    private String inviteUserId;

    /**
     * 被邀请人id
     */
    @ApiModelProperty(value = "beInviteUserId被邀请人id")
    private String beInviteUserId;

    /**
     * 比赛类型
     */
    @ApiModelProperty(value = "topicType比赛类型")
    private String topicType;

    /**
     * 题组id
     */
    @ApiModelProperty(value = "topicId题组id")
    private String topicId;

    /**
     * 0答应1 拒绝
     */
    @ApiModelProperty(value = "status0答应1 拒绝")
    private Integer status;

    /**
     * 状态描述
     */
    @ApiModelProperty(value = "statusRemark状态描述")
    private String statusRemark;

    /**
     * 题目id
     */
    @ApiModelProperty(value = "questionId题目id")
    private String questionId;

    private static final String INVITE_KEY = "invite_competition";

    public HeroLandInviteRecordDP addCheck() {
        if (StringUtils.isAnyBlank(this.inviteUserId, this.beInviteUserId, this.topicType, this.topicName)) {
            ResponseBodyWrapper.failParamException();
        }

        try {
            Long invite = TinyId.nextId("invite");
            this.recordId = invite.toString();
        } catch (Exception e) {
            e.printStackTrace();
            this.recordId = UUID.randomUUID().toString();
        }

        this.beforeInsert();
        return this;
    }

    public HeroLandInviteRecordDP inviteCheck(RedisTemplate<String, Object> redisTemplate){
        if (isInvited(redisTemplate)){
            // todo 国际化
            ResponseBodyWrapper.fail("同学你已经邀请人了,请不要重复邀请哟 ！！","50000");
        }
        if (isBeInvited(redisTemplate)){
            ResponseBodyWrapper.fail("同学你邀请的人已经被人邀请了哟 ！！","50000");
        }
        return this;
    }
    /**
     * 是否被邀请
     *
     * @param redisTemplate redis
     * @return if
     */
    public Boolean isInvited(RedisTemplate<String, Object> redisTemplate) {
        return getInviteStatus(redisTemplate, this.getInviteUserId());

    }
    /**
     * 是否被邀请
     *
     * @param redisTemplate redis
     * @return if
     */
    public Boolean isBeInvited(RedisTemplate<String, Object> redisTemplate) {
        return getInviteStatus(redisTemplate, this.getBeInviteUserId());

    }

    private Boolean getInviteStatus(RedisTemplate<String, Object> redisTemplate, String userId) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        boolean isInvited = true;
        Boolean aBoolean = valueOperations.setIfAbsent(INVITE_KEY + userId, this, 10L, TimeUnit.HOURS);
        if (aBoolean != null) {
            isInvited = aBoolean;
        }
        return !isInvited;
    }


    /**
     * 邀请者比赛结束
     *
     * @param redisTemplate
     * @return
     */
    public Boolean finishInviteUserCompetition(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.delete(INVITE_KEY+this.getInviteUserId());
    }

    /**
     * 被邀请者比赛结束 可以重新被邀请
     *
     * @param redisTemplate
     * @return
     */
    public Boolean finishBeInviteUserCompetition(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.delete(INVITE_KEY+this.getBeInviteUserId());
    }


    public HeroLandInviteRecordDP updateCheck() {
        if (StringUtils.isAnyBlank(this.inviteUserId, this.beInviteUserId, this.topicType, this.topicName, this.recordId)) {
            ResponseBodyWrapper.failParamException();
        }
        this.beforeUpdate();
        return this;
    }


    public HeroLandInviteRecordDP deleteCheck() {
        if (StringUtils.isAnyBlank(this.recordId)) {
            ResponseBodyWrapper.failParamException();
        }
        this.beforeDelete();
        return this;
    }


    /**
     * heroland_invite_record
     */
    private static final long serialVersionUID = 1L;

    /**
     * 记录id
     *
     * @return record_id 记录id
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 记录id
     *
     * @param recordId 记录id
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    /**
     * 题组名称
     *
     * @return topic_name 题组名称
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * 题组名称
     *
     * @param topicName 题组名称
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName == null ? null : topicName.trim();
    }

    /**
     * 邀请人id
     *
     * @return invite_user_id 邀请人id
     */
    public String getInviteUserId() {
        return inviteUserId;
    }

    /**
     * 邀请人id
     *
     * @param inviteUserId 邀请人id
     */
    public void setInviteUserId(String inviteUserId) {
        this.inviteUserId = inviteUserId == null ? null : inviteUserId.trim();
    }

    /**
     * 被邀请人id
     *
     * @return be_invite_user_id 被邀请人id
     */
    public String getBeInviteUserId() {
        return beInviteUserId;
    }

    /**
     * 被邀请人id
     *
     * @param beInviteUserId 被邀请人id
     */
    public void setBeInviteUserId(String beInviteUserId) {
        this.beInviteUserId = beInviteUserId == null ? null : beInviteUserId.trim();
    }

    /**
     * 比赛类型
     *
     * @return topic_type 比赛类型
     */
    public String getTopicType() {
        return topicType;
    }

    /**
     * 比赛类型
     *
     * @param topicType 比赛类型
     */
    public void setTopicType(String topicType) {
        this.topicType = topicType == null ? null : topicType.trim();
    }

    /**
     * 题组id
     *
     * @return topic_id 题组id
     */
    public String getTopicId() {
        return topicId;
    }

    /**
     * 题组id
     *
     * @param topicId 题组id
     */
    public void setTopicId(String topicId) {
        this.topicId = topicId == null ? null : topicId.trim();
    }

    /**
     * 0答应1 拒绝
     *
     * @return status 0答应1 拒绝
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0答应1 拒绝
     *
     * @param status 0答应1 拒绝
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 状态描述
     *
     * @return status_remark 状态描述
     */
    public String getStatusRemark() {
        return statusRemark;
    }

    /**
     * 状态描述
     *
     * @param statusRemark 状态描述
     */
    public void setStatusRemark(String statusRemark) {
        this.statusRemark = statusRemark == null ? null : statusRemark.trim();
    }

    /**
     * 题目id
     *
     * @return question_id 题目id
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * 题目id
     *
     * @param questionId 题目id
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
    }
}