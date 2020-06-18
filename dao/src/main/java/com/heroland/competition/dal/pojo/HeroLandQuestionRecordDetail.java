package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

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
    private String questionId;

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
     * 唯一id
     */
    @ApiModelProperty(value="recordId唯一id")
    private String recordId;

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
    public String getQuestionId() {
        return questionId;
    }

    /**
     * 全局id
     * @param questionId 全局id
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
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
     * a选项
     * @return option_a a选项
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     * a选项
     * @param optionA a选项
     */
    public void setOptionA(String optionA) {
        this.optionA = optionA == null ? null : optionA.trim();
    }

    /**
     * b选项
     * @return option_b b选项
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     * b选项
     * @param optionB b选项
     */
    public void setOptionB(String optionB) {
        this.optionB = optionB == null ? null : optionB.trim();
    }

    /**
     * c选项
     * @return option_c c选项
     */
    public String getOptionC() {
        return optionC;
    }

    /**
     * c选项
     * @param optionC c选项
     */
    public void setOptionC(String optionC) {
        this.optionC = optionC == null ? null : optionC.trim();
    }

    /**
     * d选项
     * @return option_d d选项
     */
    public String getOptionD() {
        return optionD;
    }

    /**
     * d选项
     * @param optionD d选项
     */
    public void setOptionD(String optionD) {
        this.optionD = optionD == null ? null : optionD.trim();
    }

    /**
     * e选项
     * @return option_e e选项
     */
    public String getOptionE() {
        return optionE;
    }

    /**
     * e选项
     * @param optionE e选项
     */
    public void setOptionE(String optionE) {
        this.optionE = optionE == null ? null : optionE.trim();
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
     * 解答
     * @return solution 解答
     */
    public String getSolution() {
        return solution;
    }

    /**
     * 解答
     * @param solution 解答
     */
    public void setSolution(String solution) {
        this.solution = solution == null ? null : solution.trim();
    }

    /**
     * 解析
     * @return parse 解析
     */
    public String getParse() {
        return parse;
    }

    /**
     * 解析
     * @param parse 解析
     */
    public void setParse(String parse) {
        this.parse = parse == null ? null : parse.trim();
    }

    /**
     * 难度code
     * @return level_code 难度code
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * 难度code
     * @param levelCode 难度code
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode == null ? null : levelCode.trim();
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
     * 唯一id
     * @return record_id 唯一id
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 唯一id
     * @param recordId 唯一id
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }
}