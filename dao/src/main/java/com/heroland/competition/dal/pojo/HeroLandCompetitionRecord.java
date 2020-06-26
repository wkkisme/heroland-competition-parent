package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandCompetitionRecord")
public class HeroLandCompetitionRecord extends BaseDO implements Serializable {
    /**
     * 记录唯一id
     */
    @ApiModelProperty(value="recordId记录唯一id")
    private String recordId;

    /**
     * 题组id
     */
    @ApiModelProperty(value="topicId题组id")
    private String topicId;

    /**
     * 对应某一次比赛名称
     */
    @ApiModelProperty(value="topicName对应某一次比赛名称")
    private String topicName;

    /**
     * 话题类型（比赛类型）
     */
    @ApiModelProperty(value="topicType话题类型（比赛类型）")
    private Integer topicType;

    /**
     * 0 邀请方胜利。1 被邀请方胜利。2 平局
     */
    @ApiModelProperty(value="result0 邀请方胜利。1 被邀请方胜利。2 平局")
    private Integer result;

    /**
     * 对手id
     */
    @ApiModelProperty(value="opponentId对手id")
    private String opponentId;

    /**
     * 对手级别
     */
    @ApiModelProperty(value="opponentLevel对手级别")
    private String opponentLevel;

    /**
     * 对手得分
     */
    @ApiModelProperty(value="opponentScore对手得分")
    private Integer opponentScore;

    /**
     * 邀请人id
     */
    @ApiModelProperty(value="inviteId邀请人id")
    private String inviteId;

    /**
     * 邀请人得分
     */
    @ApiModelProperty(value="inviteScore邀请人得分")
    private Integer inviteScore;

    /**
     * 科目code
     */
    @ApiModelProperty(value="subjectCode科目code")
    private String subjectCode;

    /**
     * 题目id
     */
    @ApiModelProperty(value="questionId题目id")
    private String questionId;

    /**
     * 比赛开始时间
     */
    @ApiModelProperty(value="startTime比赛开始时间")
    private Date startTime;

    /**
     * 比赛结束时间
     */
    @ApiModelProperty(value="endTime比赛结束时间")
    private Date endTime;

    /**
     * heroland_competition_record
     */
    private static final long serialVersionUID = 1L;

    /**
     * 记录唯一id
     * @return record_id 记录唯一id
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 记录唯一id
     * @param recordId 记录唯一id
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    /**
     * 题组id
     * @return topic_id 题组id
     */
    public String getTopicId() {
        return topicId;
    }

    /**
     * 题组id
     * @param topicId 题组id
     */
    public void setTopicId(String topicId) {
        this.topicId = topicId == null ? null : topicId.trim();
    }

    /**
     * 对应某一次比赛名称
     * @return topic_name 对应某一次比赛名称
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * 对应某一次比赛名称
     * @param topicName 对应某一次比赛名称
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName == null ? null : topicName.trim();
    }

    /**
     * 话题类型（比赛类型）
     * @return topic_type 话题类型（比赛类型）
     */
    public Integer getTopicType() {
        return topicType;
    }

    /**
     * 话题类型（比赛类型）
     * @param topicType 话题类型（比赛类型）
     */
    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    /**
     * 0 邀请方胜利。1 被邀请方胜利。2 平局
     * @return result 0 邀请方胜利。1 被邀请方胜利。2 平局
     */
    public Integer getResult() {
        return result;
    }

    /**
     * 0 邀请方胜利。1 被邀请方胜利。2 平局
     * @param result 0 邀请方胜利。1 被邀请方胜利。2 平局
     */
    public void setResult(Integer result) {
        this.result = result;
    }

    /**
     * 对手id
     * @return opponent_id 对手id
     */
    public String getOpponentId() {
        return opponentId;
    }

    /**
     * 对手id
     * @param opponentId 对手id
     */
    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId == null ? null : opponentId.trim();
    }

    /**
     * 对手级别
     * @return opponent_level 对手级别
     */
    public String getOpponentLevel() {
        return opponentLevel;
    }

    /**
     * 对手级别
     * @param opponentLevel 对手级别
     */
    public void setOpponentLevel(String opponentLevel) {
        this.opponentLevel = opponentLevel == null ? null : opponentLevel.trim();
    }

    /**
     * 对手得分
     * @return opponent_score 对手得分
     */
    public Integer getOpponentScore() {
        return opponentScore;
    }

    /**
     * 对手得分
     * @param opponentScore 对手得分
     */
    public void setOpponentScore(Integer opponentScore) {
        this.opponentScore = opponentScore;
    }

    /**
     * 邀请人id
     * @return invite_id 邀请人id
     */
    public String getInviteId() {
        return inviteId;
    }

    /**
     * 邀请人id
     * @param inviteId 邀请人id
     */
    public void setInviteId(String inviteId) {
        this.inviteId = inviteId == null ? null : inviteId.trim();
    }

    /**
     * 邀请人得分
     * @return invite_score 邀请人得分
     */
    public Integer getInviteScore() {
        return inviteScore;
    }

    /**
     * 邀请人得分
     * @param inviteScore 邀请人得分
     */
    public void setInviteScore(Integer inviteScore) {
        this.inviteScore = inviteScore;
    }

    /**
     * 科目code
     * @return subject_code 科目code
     */
    public String getSubjectCode() {
        return subjectCode;
    }

    /**
     * 科目code
     * @param subjectCode 科目code
     */
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode == null ? null : subjectCode.trim();
    }

    /**
     * 题目id
     * @return question_id 题目id
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * 题目id
     * @param questionId 题目id
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
    }

    /**
     * 比赛开始时间
     * @return start_time 比赛开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 比赛开始时间
     * @param startTime 比赛开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 比赛结束时间
     * @return end_time 比赛结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 比赛结束时间
     * @param endTime 比赛结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}