package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class HeroLandCompetitionRecordQO extends BaseQO {

    /**
     * 记录唯一id
     */
    @ApiModelProperty(value = "recordId记录唯一id")
    private String recordId;

    /**
     * 题组id
     */
    @ApiModelProperty(value = "topicId题组id")
    private String topicId;

    /**
     * 对应某一次比赛名称
     */
    @ApiModelProperty(value = "topicName对应某一次比赛名称")
    private String topicName;

    /**
     * 话题类型（比赛类型）
     */
    @ApiModelProperty(value = "topicType话题类型（比赛类型）")
    private Integer topicType;


    /**
     * 对手id
     */
    @ApiModelProperty(value = "opponentId对手id")
    private String opponentId;

    /**
     * 对手级别
     */
    @ApiModelProperty(value = "opponentLevel对手级别")
    private String opponentLevel;

    /**
     * 邀请人id
     */
    @ApiModelProperty(value = "inviteId邀请人id")
    private String inviteId;

    /**
     * 科目code
     */
    @ApiModelProperty(value = "subjectCode科目code")
    private String subjectCode;

    /**
     * 题目id
     */
    @ApiModelProperty(value = "questionId题目id")
    private String questionId;

    /**
     * 邀请者比赛开始时间
     */
    @ApiModelProperty(value = "inviteStartTime邀请者比赛开始时间")
    private Date inviteStartTime;

    /**
     * 邀请者比赛结束时间
     */
    @ApiModelProperty(value = "inviteEndTime邀请者比赛结束时间")
    private Date inviteEndTime;

    /**
     * 对手比赛开始时间
     */
    @ApiModelProperty(value = "opponentStartTime对手比赛开始时间")
    private Date opponentStartTime;

    /**
     * 对手比赛结束时间
     */
    @ApiModelProperty(value = "opponentEndTime对手比赛结束时间")
    private Date opponentEndTime;


    public HeroLandCompetitionRecordQO queryIdCheck() {
        if (StringUtils.isAnyBlank(recordId)) {
            ResponseBodyWrapper.failParamException();
        }

        return this;
    }

    public String getRecordId() {
        return recordId;
    }

    public HeroLandCompetitionRecordQO setRecordId(String recordId) {
        this.recordId = recordId;
        return this;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getTopicType() {
        return topicType;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    public String getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }

    public String getOpponentLevel() {
        return opponentLevel;
    }

    public void setOpponentLevel(String opponentLevel) {
        this.opponentLevel = opponentLevel;
    }

    public String getInviteId() {
        return inviteId;
    }

    public void setInviteId(String inviteId) {
        this.inviteId = inviteId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Date getInviteStartTime() {
        return inviteStartTime;
    }

    public void setInviteStartTime(Date inviteStartTime) {
        this.inviteStartTime = inviteStartTime;
    }

    public Date getInviteEndTime() {
        return inviteEndTime;
    }

    public void setInviteEndTime(Date inviteEndTime) {
        this.inviteEndTime = inviteEndTime;
    }

    public Date getOpponentStartTime() {
        return opponentStartTime;
    }

    public void setOpponentStartTime(Date opponentStartTime) {
        this.opponentStartTime = opponentStartTime;
    }

    public Date getOpponentEndTime() {
        return opponentEndTime;
    }

    public void setOpponentEndTime(Date opponentEndTime) {
        this.opponentEndTime = opponentEndTime;
    }
}
