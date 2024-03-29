package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandStatisticsDetail")
public class HeroLandStatisticsDetail extends BaseDO implements Serializable {
    /**
     * userid
     */
    @ApiModelProperty(value="userIduserid")
    private String userId;

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
     * 总分数
     */
    @ApiModelProperty(value="totalScore总分数")
    private Integer totalScore= 0;

    /**
     * 得分率
     */
    @ApiModelProperty(value="completeRate得分率")
    private Double completeRate = 0D;

    /**
     * 正确率
     */
    @ApiModelProperty(value="answerRightRate正确率")
    private Double answerRightRate = 0D;

    /**
     * 胜率
     */
    @ApiModelProperty(value="winRate胜率")
    private Double winRate = 0D;

    /**
     * 总时长单位s
     */
    @ApiModelProperty(value="totalTime总时长单位s")
    private Integer totalTime = 0;

    /**
     * 平均分
     */
    @ApiModelProperty(value="averageScore平均分")
    private Double averageScore = 0D;

    /**
     * 总表id
     */
    @ApiModelProperty(value="totalId总表id")
    private String totalId;

    /**
     * 业务id
     */
    @ApiModelProperty(value="detailId业务id")
    private String detailId;

    /**
     * 机构code
     */
    @ApiModelProperty(value="orgCode机构code")
    private String orgCode;

    /**
     * 是否历史
     */
    @ApiModelProperty(value="history是否历史")
    private Boolean history;

    /**
     * 总场次
     */
    @ApiModelProperty(value="totalGames总场次")
    private Integer totalGames = 0;

    /**
     * 班级code
     */
    @ApiModelProperty(value="classCode班级code")
    private String classCode;

    /**
     * 年级code
     */
    @ApiModelProperty(value="gradeCode年级code")
    private String gradeCode;

    /**
     * 姓名
     */
    @ApiModelProperty(value="userName姓名")
    private String userName;

    /**
     * 比赛类型
     */
    @ApiModelProperty(value="type比赛类型")
    private Integer type;

    /**
     * 年级名称
     */
    @ApiModelProperty(value="gradeName年级名称")
    private String gradeName;

    /**
     * 班级名称
     */
    @ApiModelProperty(value="className班级名称")
    private String className;

    /**
     * heroland_statistics_detail
     */
    private static final long serialVersionUID = 1L;

    /**
     * userid
     * @return user_id userid
     */
    public String getUserId() {
        return userId;
    }

    /**
     * userid
     * @param userId userid
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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
     * 科目名称
     * @return subject_name 科目名称
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 科目名称
     * @param subjectName 科目名称
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    /**
     * 总分数
     * @return total_score 总分数
     */
    public Integer getTotalScore() {
        return totalScore;
    }

    /**
     * 总分数
     * @param totalScore 总分数
     */
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * 得分率
     * @return complete_rate 得分率
     */
    public Double getCompleteRate() {
        return completeRate;
    }

    /**
     * 得分率
     * @param completeRate 得分率
     */
    public void setCompleteRate(Double completeRate) {
        this.completeRate = completeRate;
    }

    /**
     * 正确率
     * @return answer_right_rate 正确率
     */
    public Double getAnswerRightRate() {
        return answerRightRate;
    }

    /**
     * 正确率
     * @param answerRightRate 正确率
     */
    public void setAnswerRightRate(Double answerRightRate) {
        this.answerRightRate = answerRightRate;
    }

    /**
     * 胜率
     * @return win_rate 胜率
     */
    public Double getWinRate() {
        return winRate;
    }

    /**
     * 胜率
     * @param winRate 胜率
     */
    public void setWinRate(Double winRate) {
        this.winRate = winRate;
    }

    /**
     * 总时长单位s
     * @return total_time 总时长单位s
     */
    public Integer getTotalTime() {
        return totalTime;
    }

    /**
     * 总时长单位s
     * @param totalTime 总时长单位s
     */
    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * 平均分
     * @return average_score 平均分
     */
    public Double getAverageScore() {
        return averageScore;
    }

    /**
     * 平均分
     * @param averageScore 平均分
     */
    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    /**
     * 总表id
     * @return total_id 总表id
     */
    public String getTotalId() {
        return totalId;
    }

    /**
     * 总表id
     * @param totalId 总表id
     */
    public void setTotalId(String totalId) {
        this.totalId = totalId == null ? null : totalId.trim();
    }

    /**
     * 业务id
     * @return detail_id 业务id
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 业务id
     * @param detailId 业务id
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId == null ? null : detailId.trim();
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
     * 是否历史
     * @return history 是否历史
     */
    public Boolean getHistory() {
        return history;
    }

    /**
     * 是否历史
     * @param history 是否历史
     */
    public void setHistory(Boolean history) {
        this.history = history;
    }

    /**
     * 总场次
     * @return total_games 总场次
     */
    public Integer getTotalGames() {
        return totalGames;
    }

    /**
     * 总场次
     * @param totalGames 总场次
     */
    public void setTotalGames(Integer totalGames) {
        this.totalGames = totalGames;
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
     * 姓名
     * @return user_name 姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 姓名
     * @param userName 姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 比赛类型
     * @return type 比赛类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 比赛类型
     * @param type 比赛类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 年级名称
     * @return grade_name 年级名称
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * 年级名称
     * @param gradeName 年级名称
     */
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName == null ? null : gradeName.trim();
    }

    /**
     * 班级名称
     * @return class_name 班级名称
     */
    public String getClassName() {
        return className;
    }

    /**
     * 班级名称
     * @param className 班级名称
     */
    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }
}