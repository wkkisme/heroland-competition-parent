package com.heroland.competition.domain.dp;

import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.enums.InviteStatusEnum;
import com.heroland.competition.common.utils.IDGenerateUtils;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;


import static com.heroland.competition.common.utils.IDGenerateUtils.ModelEnum.ADMIN;

@ApiModel(value = "com.heroland.competition.dal.pojo.HeroLandInviteRecord")
public class HeroLandInviteRecordDP extends BaseDO {
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
    private Integer topicType;

    /**
     * 题组id
     */
    @ApiModelProperty(value = "topicId题组id")
    private String topicId;

    /**
     * 0答应1 拒绝 2 邀请中
     */
    @ApiModelProperty(value = "status0答应1 拒绝 2 邀请中")
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

    /**
     * 当前用户信息，一定要传
     */
    private OnlineDP currentUser;

    private static final String INVITE_KEY = "invite_competition:";

    public HeroLandInviteRecordDP addCheck() {
        if (this.topicType == null || StringUtils.isAnyBlank(this.inviteUserId, this.beInviteUserId, this.topicName)) {
            ResponseBodyWrapper.failParamException();
        }

        try {
            Long invite = TinyId.nextId("invite");
            this.recordId = invite.toString();
        } catch (Exception e) {
            e.printStackTrace();
            this.recordId = IDGenerateUtils.getIdByRandom(ADMIN) + "";;
        }
        // 设置状态为 邀请中
        status = InviteStatusEnum.WAITING.getStatus();

        this.beforeInsert();
        return this;
    }

    public HeroLandInviteRecordDP inviteCheck(RedisService redisTemplate){
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
     * @param redisService redis
     * @return if
     */
    public Boolean isInvited(RedisService redisService) {
        return getInviteStatus(redisService, this.getInviteUserId());

    }
    /**
     * 是否被邀请
     *
     * @param redisService redis
     * @return if
     */
    public Boolean isBeInvited(RedisService redisService) {
        return getInviteStatus(redisService, this.getBeInviteUserId());

    }

    private Boolean getInviteStatus(RedisService redisService, String userId) {
        boolean isInvited = redisService.setNx(INVITE_KEY + userId, this, "PT6M");
        return !isInvited;
    }


    /**
     * 邀请者比赛结束
     *
     * @param redisService
     * @return
     */
    public Boolean finishInviteUserCompetition(RedisService redisService) {
        return redisService.del(INVITE_KEY+this.getInviteUserId());
    }

    /**
     * 被邀请者比赛结束 可以重新被邀请
     *
     * @param redisService
     * @return
     */
    public Boolean finishBeInviteUserCompetition(RedisService redisService) {
        return redisService.del(INVITE_KEY+this.getBeInviteUserId());
    }


    public HeroLandInviteRecordDP updateCheck() {
        if ( this.topicType == null || StringUtils.isAnyBlank(this.inviteUserId, this.beInviteUserId, this.topicName, this.recordId)) {
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

    public OnlineDP getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(OnlineDP currentUser) {
        this.currentUser = currentUser;
    }

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
    public Integer getTopicType() {
        return topicType;
    }

    /**
     * 比赛类型
     *
     * @param topicType 比赛类型
     */
    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
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