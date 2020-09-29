package com.heroland.competition.domain.dp;

import cn.hutool.core.util.ObjectUtil;
import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.utils.IDGenerateUtils;
import com.heroland.competition.domain.dto.QuestionOptionDto;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "com.heroland.competition.dal.pojo.HeroLandQuestionRecordDetail")
public class HeroLandQuestionRecordDetailDP extends BaseDO implements Serializable {
    /**
     * 年级code
     */
    @ApiModelProperty(value = "gradeCode年级code")
    private String gradeCode;

    /**
     * 班级code
     */
    @ApiModelProperty(value = "classCode班级code")
    private String classCode;

    /**
     * 全局id
     */
    @ApiModelProperty(value = "questionId全局id")
    private Long questionId;

    /**
     * 题目
     */
    @ApiModelProperty(value = "title题目")
    private String title;

    /**
     * 正确答案
     */
    @ApiModelProperty(value = "answer正确答案")
    private String answer;

    /**
     * 你的答案
     */
    @ApiModelProperty(value = "yourAnswer你的答案")
    private String yourAnswer;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "userId用户id")
    private String userId;

    /**
     * 比赛记录唯一id
     */
    @ApiModelProperty(value = "recordId比赛记录唯一id")
    private String recordId;

    /**
     * detail 唯一id
     */
    @ApiModelProperty(value = "recordDetailIddetail 唯一id")
    private String recordDetailId;

    /**
     * 该题答题开始时间
     */
    @ApiModelProperty(value = "beginDate该题答题开始时间")
    private Date beginDate;

    /**
     * 该题答题结束时间
     */
    @ApiModelProperty(value = "endDate该题答题结束时间")
    private Date endDate;

    /**
     * 该题得分
     */
    @ApiModelProperty(value = "score该题得分")
    private Integer score;

    /**
     * 是否回答正确
     */
    @ApiModelProperty(value="correctAnswer是否回答正确")
    private Boolean correctAnswer;

    /**
     * 题组id
     */
    @ApiModelProperty(value = "topicId题组id")
    private String topicId;

    /**
     * 科目code
     */
    @ApiModelProperty(value = "subjectCode科目code")
    private String subjectCode;

    /**
     * 对手名字
     */
    @ApiModelProperty(value = "opponentName对手名字")
    private String opponentName;


    private String course;

    private String grade;

    private Integer diff;
    private String orgCode;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
        this.subjectCode = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
        this.gradeCode = grade;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
        this.course = subjectCode;
    }


    public HeroLandQuestionRecordDetailDP updateCheck() {
        if (StringUtils.isAnyBlank(this.recordDetailId, this.recordId)) {
            ResponseBodyWrapper.failParamException();
        }
        if (this.recordId == null && this.recordDetailId == null) {
            ResponseBodyWrapper.failParamException();
        }
        this.beforeUpdate();
        return this;
    }

    public HeroLandQuestionRecordDetailDP addCheck() {
        if (getId() != null){
            this.questionId = getId();
        }
        if (ObjectUtil.isNull(beginDate) || this.questionId == null || StringUtils.isAnyBlank(this.userId, this.yourAnswer, this.recordId)) {
            ResponseBodyWrapper.failParamException();
        }

        try {
            Long invite = TinyId.nextId("questionRecord");
            this.recordDetailId = invite.toString();
        } catch (Exception e) {
            e.printStackTrace();
            this.recordDetailId = IDGenerateUtils.getIdByRandom(IDGenerateUtils.ModelEnum.DEFAULT) + "";
        }

        this.beforeInsert();
        return this;
    }

    /**
     * heroland_question_record_detail
     */
    private static final long serialVersionUID = 1L;


    /**
     * 年级code
     *
     * @return grade_code 年级code
     */
    public String getGradeCode() {
        return gradeCode;
    }

    /**
     * 年级code
     *
     * @param gradeCode 年级code
     */
    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode == null ? null : gradeCode.trim();
    }

    /**
     * 班级code
     *
     * @return class_code 班级code
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * 班级code
     *
     * @param classCode 班级code
     */
    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }


    /**
     * 题目
     *
     * @return title 题目
     */
    public String getTitle() {
        return title;
    }

    /**
     * 题目
     *
     * @param title 题目
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 正确答案
     *
     * @return answer 正确答案
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 正确答案
     *
     * @param answer 正确答案
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }


    /**
     * 你的答案
     *
     * @return your_answer 你的答案
     */
    public String getYourAnswer() {
        return yourAnswer;
    }

    /**
     * 你的答案
     *
     * @param yourAnswer 你的答案
     */
    public void setYourAnswer(String yourAnswer) {
        this.yourAnswer = yourAnswer == null ? null : yourAnswer.trim();
    }

    /**
     * 用户id
     *
     * @return user_id 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 唯一id
     *
     * @return record_id 唯一id
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 唯一id
     *
     * @param recordId 唯一id
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getRecordDetailId() {
        return recordDetailId;
    }

    public void setRecordDetailId(String recordDetailId) {
        this.recordDetailId = recordDetailId == null ? null : recordDetailId.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }


    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public Integer getDiff() {
        return diff;
    }

    public void setDiff(Integer diff) {
        this.diff = diff;
    }
}