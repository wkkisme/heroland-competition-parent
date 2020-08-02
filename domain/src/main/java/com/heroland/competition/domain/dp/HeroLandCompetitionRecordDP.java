package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author mac
 */
@ApiModel(value = "com.heroland.competition.dal.pojo.HeroLandCompetitionRecord")
public class HeroLandCompetitionRecordDP extends BaseDO implements Serializable {
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
     * 0 邀请方胜利。1 被邀请方胜利。2 平局
     */
    @ApiModelProperty(value = "result0 邀请方胜利。1 被邀请方胜利。2 平局")
    private Integer result;

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
     * 对手得分
     */
    @ApiModelProperty(value = "opponentScore对手得分")
    private Integer opponentScore;

    /**
     * 邀请人id
     */
    @ApiModelProperty(value = "inviteId邀请人id")
    private String inviteId;

    /**
     * 邀请人得分
     */
    @ApiModelProperty(value = "inviteScore邀请人得分")
    private Integer inviteScore;

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

    @ApiModelProperty(value = "winRate胜率")
    private String winRate;

    @ApiModelProperty(value = "finishQuestions完成题数")
    private Integer finishQuestions;

    private List<HeroLandQuestionRecordDetailDP> details;

    private String primaryRedisKey;

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

    public String getPrimaryRedisKey() {
        if (primaryRedisKey == null) {
            primaryRedisKey = topicId + questionId + inviteId + opponentId;
        }
        return primaryRedisKey;
    }

    public void setPrimaryRedisKey(String primaryRedisKey) {
        this.primaryRedisKey = primaryRedisKey;
    }

    public HeroLandCompetitionRecordDP addCheck() {
        if (StringUtils.isAnyBlank(this.topicId, this.topicName)) {
            ResponseBodyWrapper.failParamException();
        }
        if (this.inviteId == null || this.opponentId == null) {
            ResponseBodyWrapper.failParamException();
        }
        this.beforeInsert();

        // todo 获取当前人的id
        String currentUserId = "";
        if (this.inviteId.equals(currentUserId)) {
            this.inviteStartTime = new Date();
        }
        if (this.opponentId.equals(currentUserId)) {
            this.opponentStartTime = new Date();
        }
        if (this.opponentStartTime == null && this.inviteStartTime == null) {
            ResponseBodyWrapper.failParamException();
        }


        try {
            this.recordId = TinyId.nextId("competitionRecord").toString();
        } catch (Exception e) {
            e.printStackTrace();
            this.recordId = UUID.randomUUID().toString();
        }

        return this;
    }


    public HeroLandCompetitionRecordDP addSynchronizeCheck() {

        if (StringUtils.isAnyBlank(this.questionId)) {
            ResponseBodyWrapper.failParamException();
        }

        return addCheck();
    }


    public HeroLandCompetitionRecordDP updateCheck() {
        if (StringUtils.isAnyBlank(this.topicId, this.topicName, this.recordId)) {
            ResponseBodyWrapper.failParamException();
        }
        if (this.inviteId == null && this.opponentId == null) {
            ResponseBodyWrapper.failParamException();
        }
        this.beforeUpdate();
        return this;
    }

    public HeroLandCompetitionRecordDP deleteCheck() {
        if (StringUtils.isAnyBlank(this.recordId)) {
            ResponseBodyWrapper.failParamException();
        }

        this.beforeDelete();
        return this;
    }

    public HeroLandCompetitionRecordDP doAnswer() {
        // todo 获取当前人的id
        String currentUserId = "";

        if (StringUtils.isAnyBlank(this.recordId)) {
            ResponseBodyWrapper.failParamException();
        }
        if (this.inviteId.equals(currentUserId) && this.inviteStartTime == null) {
            ResponseBodyWrapper.failParamException();
        } else if (this.inviteId.equals(currentUserId)) {
            this.inviteEndTime = new Date();
        }

        if (this.opponentId.equals(currentUserId) && this.opponentStartTime == null) {
            ResponseBodyWrapper.failParamException();
        } else if (this.opponentId.equals(currentUserId)) {
            this.opponentEndTime = new Date();
        }


        return this;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getWinRate() {
        return winRate;
    }

    public void setWinRate(String winRate) {
        this.winRate = winRate;
    }

    public Integer getFinishQuestions() {
        return finishQuestions;
    }

    public void setFinishQuestions(Integer finishQuestions) {
        this.finishQuestions = finishQuestions;
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

    public List<HeroLandQuestionRecordDetailDP> getDetails() {
        return details;
    }

    public void setDetails(List<HeroLandQuestionRecordDetailDP> details) {
        this.details = details;
    }

    /**
     * heroland_competition_record
     */
    private static final long serialVersionUID = 1L;

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
     * 对应某一次比赛名称
     *
     * @return topic_name 对应某一次比赛名称
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * 对应某一次比赛名称
     *
     * @param topicName 对应某一次比赛名称
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName == null ? null : topicName.trim();
    }

    /**
     * 话题类型（比赛类型）
     *
     * @return topic_type 话题类型（比赛类型）
     */
    public Integer getTopicType() {
        return topicType;
    }

    /**
     * 话题类型（比赛类型）
     *
     * @param topicType 话题类型（比赛类型）
     */
    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    /**
     * 记录唯一id
     *
     * @return record_id 记录唯一id
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 记录唯一id
     *
     * @param recordId 记录唯一id
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    /**
     * 0 邀请方胜利。1 被邀请方胜利。2 平局
     *
     * @return result 0 邀请方胜利。1 被邀请方胜利。2 平局
     */
    public Integer getResult() {
        return result;
    }

    /**
     * 0 邀请方胜利。1 被邀请方胜利。2 平局
     *
     * @param result 0 邀请方胜利。1 被邀请方胜利。2 平局
     */
    public void setResult(Integer result) {
        this.result = result;
    }

    /**
     * 对手id
     *
     * @return opponent_id 对手id
     */
    public String getOpponentId() {
        return opponentId;
    }

    /**
     * 对手id
     *
     * @param opponentId 对手id
     */
    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId == null ? null : opponentId.trim();
    }

    /**
     * 对手级别
     *
     * @return opponent_level 对手级别
     */
    public String getOpponentLevel() {
        return opponentLevel;
    }

    /**
     * 对手级别
     *
     * @param opponentLevel 对手级别
     */
    public void setOpponentLevel(String opponentLevel) {
        this.opponentLevel = opponentLevel == null ? null : opponentLevel.trim();
    }

    /**
     * 对手得分
     *
     * @return opponent_score 对手得分
     */
    public Integer getOpponentScore() {
        return opponentScore;
    }

    /**
     * 对手得分
     *
     * @param opponentScore 对手得分
     */
    public void setOpponentScore(Integer opponentScore) {
        this.opponentScore = opponentScore;
    }

    /**
     * 邀请人id
     *
     * @return invite_id 邀请人id
     */
    public String getInviteId() {
        return inviteId;
    }

    /**
     * 邀请人id
     *
     * @param inviteId 邀请人id
     */
    public void setInviteId(String inviteId) {
        this.inviteId = inviteId == null ? null : inviteId.trim();
    }

    /**
     * 邀请人得分
     *
     * @return invite_score 邀请人得分
     */
    public Integer getInviteScore() {
        return inviteScore;
    }

    /**
     * 邀请人得分
     *
     * @param inviteScore 邀请人得分
     */
    public void setInviteScore(Integer inviteScore) {
        this.inviteScore = inviteScore;
    }

    /**
     * 科目code
     *
     * @return subject_code 科目code
     */
    public String getSubjectCode() {
        return subjectCode;
    }

    /**
     * 科目code
     *
     * @param subjectCode 科目code
     */
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode == null ? null : subjectCode.trim();
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