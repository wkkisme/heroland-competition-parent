package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.UUID;

@ApiModel(value = "com.heroland.competition.dal.pojo.HeroLandQuestion")
public class HeroLandQuestionDP extends BaseDO implements Serializable {
    /**
     * 本次题目全局id
     */
    @ApiModelProperty(value="questionId本次题目全局id")
    private String questionId;

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
     * 题目
     */
    @ApiModelProperty(value="title题目")
    private String title;

    /**
     * a选项
     */
    @ApiModelProperty(value="optionAa选项")
    private String optionA;

    /**
     * b选项
     */
    @ApiModelProperty(value="optionBb选项")
    private String optionB;

    /**
     * c选项
     */
    @ApiModelProperty(value="optionCc选项")
    private String optionC;

    /**
     * d选项
     */
    @ApiModelProperty(value="optionDd选项")
    private String optionD;

    /**
     * e选项
     */
    @ApiModelProperty(value="optionEe选项")
    private String optionE;

    /**
     * 正确答案
     */
    @ApiModelProperty(value="answer正确答案")
    private String answer;

    /**
     * 解答
     */
    @ApiModelProperty(value="solution解答")
    private String solution;

    /**
     * 解析
     */
    @ApiModelProperty(value="parse解析")
    private String parse;

    /**
     * 难度code
     */
    @ApiModelProperty(value="levelCode难度code")
    private String levelCode;

    /**
     * 本身题目的id
     */
    @ApiModelProperty(value="formerQuestionId本身题目的id")
    private String formerQuestionId;

    /**
     * 某次比赛的id
     */
    @ApiModelProperty(value="topicId某次比赛的id")
    private String topicId;

    /**
     * 某次比赛的name
     */
    @ApiModelProperty(value="topicName某次比赛的name")
    private String topicName;

    /**
     * 科目code
     */
    @ApiModelProperty(value="subjectCode科目code")
    private String subjectCode;

    /**
     * 科目名称
     */
    @ApiModelProperty(value="subjectName科目名称")
    private String subjectName;

    /**
     * heroland_question
     */
    private static final long serialVersionUID = 1L;

    public HeroLandQuestionDP addCheck() {

        if (StringUtils.isBlank(this.getTitle())) {
            ResponseBodyWrapper.failParamException();
        }
        try {
            Long question = TinyId.nextId("question");
            this.setQuestionId(question.toString());
        } catch (Exception e) {
            this.setQuestionId(UUID.randomUUID().toString());
        }

        return this;

    }

    public HeroLandQuestionDP deleteCheck() {

        if (this.getId() == null) {
            ResponseBodyWrapper.failParamException();
        }

        this.beforeDelete();

        return this;

    }

    public HeroLandQuestionDP addTopicQuestionsCheck() {

        if (StringUtils.isAnyBlank(this.getTopicId(),this.getQuestionId())) {
            ResponseBodyWrapper.failParamException();
        }

        this.beforeInsert();

        return this;

    }

    public String getFormerQuestionId() {
        return formerQuestionId;
    }

    public void setFormerQuestionId(String formerQuestionId) {
        this.formerQuestionId = formerQuestionId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

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
     * 全局id
     *
     * @return question_id 全局id
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * 全局id
     *
     * @param questionId 全局id
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
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
     * a选项
     *
     * @return option_a a选项
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     * a选项
     *
     * @param optionA a选项
     */
    public void setOptionA(String optionA) {
        this.optionA = optionA == null ? null : optionA.trim();
    }

    /**
     * b选项
     *
     * @return option_b b选项
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     * b选项
     *
     * @param optionB b选项
     */
    public void setOptionB(String optionB) {
        this.optionB = optionB == null ? null : optionB.trim();
    }

    /**
     * c选项
     *
     * @return option_c c选项
     */
    public String getOptionC() {
        return optionC;
    }

    /**
     * c选项
     *
     * @param optionC c选项
     */
    public void setOptionC(String optionC) {
        this.optionC = optionC == null ? null : optionC.trim();
    }

    /**
     * d选项
     *
     * @return option_d d选项
     */
    public String getOptionD() {
        return optionD;
    }

    /**
     * d选项
     *
     * @param optionD d选项
     */
    public void setOptionD(String optionD) {
        this.optionD = optionD == null ? null : optionD.trim();
    }

    /**
     * e选项
     *
     * @return option_e e选项
     */
    public String getOptionE() {
        return optionE;
    }

    /**
     * e选项
     *
     * @param optionE e选项
     */
    public void setOptionE(String optionE) {
        this.optionE = optionE == null ? null : optionE.trim();
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
     * 解答
     *
     * @return solution 解答
     */
    public String getSolution() {
        return solution;
    }

    /**
     * 解答
     *
     * @param solution 解答
     */
    public void setSolution(String solution) {
        this.solution = solution == null ? null : solution.trim();
    }

    /**
     * 解析
     *
     * @return parse 解析
     */
    public String getParse() {
        return parse;
    }

    /**
     * 解析
     *
     * @param parse 解析
     */
    public void setParse(String parse) {
        this.parse = parse == null ? null : parse.trim();
    }

    /**
     * 难度code
     *
     * @return level_code 难度code
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * 难度code
     *
     * @param levelCode 难度code
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode == null ? null : levelCode.trim();
    }
}