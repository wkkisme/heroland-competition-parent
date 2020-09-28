package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value="com.heroland.competition.dal.pojo.HerolandStatisticsWord")
public class HerolandStatisticsWordDP extends BaseDO implements Serializable {
    /**
     * userid
     */
    @ApiModelProperty(value="userIduserid")
    private String userId;

    /**
     * 姓名
     */
    @ApiModelProperty(value="userName姓名")
    private String userName;

    /**
     * topicId
     */
    @ApiModelProperty(value="topicIdtopicId")
    private Long topicId;

    /**
     * 比赛类型
     */
    @ApiModelProperty(value="type比赛类型")
    private Integer type;

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
    private Integer totalScore;

    /**
     * 排名
     */
    @ApiModelProperty(value="totalRank排名")
    private Integer totalRank;

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
     * 年级code
     */
    @ApiModelProperty(value="gradeCode年级code")
    private String gradeCode;

    /**
     * 年级名称
     */
    @ApiModelProperty(value="gradeName年级名称")
    private String gradeName;

    /**
     * topic的开始时间
     */
    @ApiModelProperty(value="startTimetopic的开始时间")
    private Date startTime;

    /**
     * topic的结束时间
     */
    @ApiModelProperty(value="endTimetopic的结束时间")
    private Date endTime;

    /**
     * heroland_statistics_word
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
     * topicId
     * @return topic_id topicId
     */
    public Long getTopicId() {
        return topicId;
    }

    /**
     * topicId
     * @param topicId topicId
     */
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
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
     * 排名
     * @return total_rank 排名
     */
    public Integer getTotalRank() {
        return totalRank;
    }

    /**
     * 排名
     * @param totalRank 排名
     */
    public void setTotalRank(Integer totalRank) {
        this.totalRank = totalRank;
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
     * topic的开始时间
     * @return start_time topic的开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * topic的开始时间
     * @param startTime topic的开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * topic的结束时间
     * @return end_time topic的结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * topic的结束时间
     * @param endTime topic的结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}