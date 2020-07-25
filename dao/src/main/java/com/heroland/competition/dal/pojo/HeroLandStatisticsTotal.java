package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandStatisticsTotal")
public class HeroLandStatisticsTotal extends BaseDO implements Serializable {
    /**
     * 用户名称

     */
    @ApiModelProperty(value="userName用户名称 ")
    private String userName;

    /**
     * userid
     */
    @ApiModelProperty(value="userIduserid")
    private String userId;

    /**
     * 
     */
    @ApiModelProperty(value="gradeCode")
    private String gradeCode;

    /**
     * 
     */
    @ApiModelProperty(value="gradeName")
    private String gradeName;

    /**
     * 
     */
    @ApiModelProperty(value="classCode")
    private String classCode;

    /**
     * 
     */
    @ApiModelProperty(value="className")
    private String className;

    /**
     * 
     */
    @ApiModelProperty(value="totalScore")
    private Integer totalScore;

    /**
     * 得分率
     */
    @ApiModelProperty(value="completeRate得分率")
    private Double completeRate;

    /**
     * 正确率
     */
    @ApiModelProperty(value="answerRightRate正确率")
    private Double answerRightRate;

    /**
     * 胜率
     */
    @ApiModelProperty(value="winRate胜率")
    private Double winRate;

    /**
     * 总时长单位s
     */
    @ApiModelProperty(value="totalTime总时长单位s")
    private Integer totalTime;

    /**
     * 平均分
     */
    @ApiModelProperty(value="averageScore平均分")
    private Double averageScore;

    /**
     * id
     */
    @ApiModelProperty(value="totalIdid")
    private String totalId;

    /**
     * 机构code
     */
    @ApiModelProperty(value="orgCode机构code")
    private String orgCode;

    /**
     * b比赛类型
     */
    @ApiModelProperty(value="typeb比赛类型")
    private Integer type;

    /**
     * 是否历史
     */
    @ApiModelProperty(value="history是否历史")
    private Boolean history;

    /**
     * 总场次
     */
    @ApiModelProperty(value="totalGames总场次")
    private Integer totalGames;

    /**
     * heroland_statistics_total
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户名称

     * @return user_name 用户名称

     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名称

     * @param userName 用户名称

     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

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
     * 
     * @return grade_code 
     */
    public String getGradeCode() {
        return gradeCode;
    }

    /**
     * 
     * @param gradeCode 
     */
    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode == null ? null : gradeCode.trim();
    }

    /**
     * 
     * @return grade_name 
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * 
     * @param gradeName 
     */
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName == null ? null : gradeName.trim();
    }

    /**
     * 
     * @return class_code 
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * 
     * @param classCode 
     */
    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }

    /**
     * 
     * @return class_name 
     */
    public String getClassName() {
        return className;
    }

    /**
     * 
     * @param className 
     */
    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    /**
     * 
     * @return total_score 
     */
    public Integer getTotalScore() {
        return totalScore;
    }

    /**
     * 
     * @param totalScore 
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
     * id
     * @return total_id id
     */
    public String getTotalId() {
        return totalId;
    }

    /**
     * id
     * @param totalId id
     */
    public void setTotalId(String totalId) {
        this.totalId = totalId == null ? null : totalId.trim();
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
     * b比赛类型
     * @return type b比赛类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * b比赛类型
     * @param type b比赛类型
     */
    public void setType(Integer type) {
        this.type = type;
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
}