package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandCompetitionRecord")
public class HeroLandCompetitionRecord extends BaseDO implements Serializable {
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
     * 记录唯一id
     */
    @ApiModelProperty(value="recordId记录唯一id")
    private String recordId;

    /**
     * 0 邀请方胜利。1 被邀请方胜利。2 平局且都答对，3平局且都答错
     */
    @ApiModelProperty(value="result0 邀请方胜利。1 被邀请方胜利。2 平局且都答对，3平局且都答错")
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
    private Long questionId;

    /**
     * 邀请者比赛开始时间
     */
    @ApiModelProperty(value="inviteStartTime邀请者比赛开始时间")
    private Date inviteStartTime;

    /**
     * 邀请者比赛结束时间
     */
    @ApiModelProperty(value="inviteEndTime邀请者比赛结束时间")
    private Date inviteEndTime;

    /**
     * 对手比赛开始时间
     */
    @ApiModelProperty(value="opponentStartTime对手比赛开始时间")
    private Date opponentStartTime;

    /**
     * 对手比赛结束时间
     */
    @ApiModelProperty(value="opponentEndTime对手比赛结束时间")
    private Date opponentEndTime;

    /**
     * 机构code
     */
    @ApiModelProperty(value="orgCode机构code")
    private String orgCode;

    /**
     * 班级code
     */
    @ApiModelProperty(value="classCode班级code")
    private String classCode;

    /**
     * 年级
     */
    @ApiModelProperty(value="gradeCode年级")
    private String gradeCode;

    /**
     * 邀请人等级
     */
    @ApiModelProperty(value="inviteLevel邀请人等级")
    private String inviteLevel;

    /**
     * 比赛状态 0比赛结束，1 比赛中 2 未开始
     */
    @ApiModelProperty(value="status比赛状态 0比赛结束，1 比赛中 2 未开始")
    private Integer status;

    /**
     * 邀请记录id
     */
    @ApiModelProperty(value="inviteRecordId邀请记录id")
    private String inviteRecordId;

    /**
     * 是否正确（同步作业赛）
     */
    @ApiModelProperty(value="correctAnswer是否正确（同步作业赛）")
    private Boolean correctAnswer;

    /**
     * heroland_competition_record
     */
    private static final long serialVersionUID = 1L;

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
     * 0 邀请方胜利。1 被邀请方胜利。2 平局且都答对，3平局且都答错
     * @return result 0 邀请方胜利。1 被邀请方胜利。2 平局且都答对，3平局且都答错
     */
    public Integer getResult() {
        return result;
    }

    /**
     * 0 邀请方胜利。1 被邀请方胜利。2 平局且都答对，3平局且都答错
     * @param result 0 邀请方胜利。1 被邀请方胜利。2 平局且都答对，3平局且都答错
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
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * 题目id
     * @param questionId 题目id
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /**
     * 邀请者比赛开始时间
     * @return invite_start_time 邀请者比赛开始时间
     */
    public Date getInviteStartTime() {
        return inviteStartTime;
    }

    /**
     * 邀请者比赛开始时间
     * @param inviteStartTime 邀请者比赛开始时间
     */
    public void setInviteStartTime(Date inviteStartTime) {
        this.inviteStartTime = inviteStartTime;
    }

    /**
     * 邀请者比赛结束时间
     * @return invite_end_time 邀请者比赛结束时间
     */
    public Date getInviteEndTime() {
        return inviteEndTime;
    }

    /**
     * 邀请者比赛结束时间
     * @param inviteEndTime 邀请者比赛结束时间
     */
    public void setInviteEndTime(Date inviteEndTime) {
        this.inviteEndTime = inviteEndTime;
    }

    /**
     * 对手比赛开始时间
     * @return opponent_start_time 对手比赛开始时间
     */
    public Date getOpponentStartTime() {
        return opponentStartTime;
    }

    /**
     * 对手比赛开始时间
     * @param opponentStartTime 对手比赛开始时间
     */
    public void setOpponentStartTime(Date opponentStartTime) {
        this.opponentStartTime = opponentStartTime;
    }

    /**
     * 对手比赛结束时间
     * @return opponent_end_time 对手比赛结束时间
     */
    public Date getOpponentEndTime() {
        return opponentEndTime;
    }

    /**
     * 对手比赛结束时间
     * @param opponentEndTime 对手比赛结束时间
     */
    public void setOpponentEndTime(Date opponentEndTime) {
        this.opponentEndTime = opponentEndTime;
    }

    /**
     * 机构code
     * @return org_code 机构code
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 机构code
     * @param orgCode 机构code
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 班级code
     * @return class_code 班级code
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * 班级code
     * @param classCode 班级code
     */
    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }

    /**
     * 年级
     * @return grade_code 年级
     */
    public String getGradeCode() {
        return gradeCode;
    }

    /**
     * 年级
     * @param gradeCode 年级
     */
    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode == null ? null : gradeCode.trim();
    }

    /**
     * 邀请人等级
     * @return invite_level 邀请人等级
     */
    public String getInviteLevel() {
        return inviteLevel;
    }

    /**
     * 邀请人等级
     * @param inviteLevel 邀请人等级
     */
    public void setInviteLevel(String inviteLevel) {
        this.inviteLevel = inviteLevel == null ? null : inviteLevel.trim();
    }

    /**
     * 比赛状态 0比赛结束，1 比赛中 2 未开始
     * @return status 比赛状态 0比赛结束，1 比赛中 2 未开始
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 比赛状态 0比赛结束，1 比赛中 2 未开始
     * @param status 比赛状态 0比赛结束，1 比赛中 2 未开始
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 邀请记录id
     * @return invite_record_id 邀请记录id
     */
    public String getInviteRecordId() {
        return inviteRecordId;
    }

    /**
     * 邀请记录id
     * @param inviteRecordId 邀请记录id
     */
    public void setInviteRecordId(String inviteRecordId) {
        this.inviteRecordId = inviteRecordId == null ? null : inviteRecordId.trim();
    }

    /**
     * 是否正确（同步作业赛）
     * @return correct_answer 是否正确（同步作业赛）
     */
    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * 是否正确（同步作业赛）
     * @param correctAnswer 是否正确（同步作业赛）
     */
    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}