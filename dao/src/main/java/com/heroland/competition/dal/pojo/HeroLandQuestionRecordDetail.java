package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandQuestionRecordDetail")
public class HeroLandQuestionRecordDetail extends BaseDO implements Serializable {
    /**
     * 年级code
     */
    @ApiModelProperty(value="gradeCode年级code")
    private String gradeCode;

    /**
     * 班级code
     */
    @ApiModelProperty(value="classCode班级code")
    private String classCode;

    /**
     * 全局id
     */
    @ApiModelProperty(value="questionId全局id")
    private Long questionId;

    /**
     * 题目
     */
    @ApiModelProperty(value="title题目")
    private String title;

    /**
     * 正确答案
     */
    @ApiModelProperty(value="answer正确答案")
    private String answer;

    /**
     * 你的答案
     */
    @ApiModelProperty(value="yourAnswer你的答案")
    private String yourAnswer;

    /**
     * 用户id
     */
    @ApiModelProperty(value="userId用户id")
    private String userId;

    /**
     * 比赛记录唯一id
     */
    @ApiModelProperty(value="recordId比赛记录唯一id")
    private String recordId;

    /**
     * detail 唯一id
     */
    @ApiModelProperty(value="recordDetailIddetail 唯一id")
    private String recordDetailId;

    /**
     * 该题答题开始时间
     */
    @ApiModelProperty(value="beginDate该题答题开始时间")
    private Date beginDate;

    /**
     * 该题答题结束时间
     */
    @ApiModelProperty(value="endDate该题答题结束时间")
    private Date endDate;

    /**
     * 该题得分
     */
    @ApiModelProperty(value="score该题得分")
    private Integer score;

    /**
     * 是否回答正确
     */
    @ApiModelProperty(value="correctAnswer是否回答正确")
    private Boolean correctAnswer;

    /**
     * 题组id
     */
    @ApiModelProperty(value="topicId题组id")
    private String topicId;

    /**
     * 科目code
     */
    @ApiModelProperty(value="subjectCode科目code")
    private String subjectCode;

    /**
     * 对手名字
     */
    @ApiModelProperty(value="opponentName对手名字")
    private String opponentName;

    /**
     * 
     */
    @ApiModelProperty(value="orgCode")
    private String orgCode;

    /**
     * heroland_question_record_detail
     */
    private static final long serialVersionUID = 1L;

    /**
     * 年级code
     * @return grade_code 年级code
     */
    public String getGradeCode() {
        return gradeCode;
    }

    /**
     * 年级code
     * @param gradeCode 年级code
     */
    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode == null ? null : gradeCode.trim();
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
     * 全局id
     * @return question_id 全局id
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * 全局id
     * @param questionId 全局id
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /**
     * 题目
     * @return title 题目
     */
    public String getTitle() {
        return title;
    }

    /**
     * 题目
     * @param title 题目
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 正确答案
     * @return answer 正确答案
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 正确答案
     * @param answer 正确答案
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * 你的答案
     * @return your_answer 你的答案
     */
    public String getYourAnswer() {
        return yourAnswer;
    }

    /**
     * 你的答案
     * @param yourAnswer 你的答案
     */
    public void setYourAnswer(String yourAnswer) {
        this.yourAnswer = yourAnswer == null ? null : yourAnswer.trim();
    }

    /**
     * 用户id
     * @return user_id 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 比赛记录唯一id
     * @return record_id 比赛记录唯一id
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 比赛记录唯一id
     * @param recordId 比赛记录唯一id
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    /**
     * detail 唯一id
     * @return record_detail_id detail 唯一id
     */
    public String getRecordDetailId() {
        return recordDetailId;
    }

    /**
     * detail 唯一id
     * @param recordDetailId detail 唯一id
     */
    public void setRecordDetailId(String recordDetailId) {
        this.recordDetailId = recordDetailId == null ? null : recordDetailId.trim();
    }

    /**
     * 该题答题开始时间
     * @return begin_date 该题答题开始时间
     */
    public Date getBeginDate() {
        return beginDate;
    }

    /**
     * 该题答题开始时间
     * @param beginDate 该题答题开始时间
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * 该题答题结束时间
     * @return end_date 该题答题结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 该题答题结束时间
     * @param endDate 该题答题结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 该题得分
     * @return score 该题得分
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 该题得分
     * @param score 该题得分
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 是否回答正确
     * @return correct_answer 是否回答正确
     */
    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * 是否回答正确
     * @param correctAnswer 是否回答正确
     */
    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
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
     * 对手名字
     * @return opponent_name 对手名字
     */
    public String getOpponentName() {
        return opponentName;
    }

    /**
     * 对手名字
     * @param opponentName 对手名字
     */
    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName == null ? null : opponentName.trim();
    }

    /**
     * 
     * @return org_code 
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 
     * @param orgCode 
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }
}