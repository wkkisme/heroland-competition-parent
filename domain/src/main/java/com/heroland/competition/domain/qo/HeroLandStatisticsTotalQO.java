package com.heroland.competition.domain.qo;

import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import com.anycommon.response.common.BaseQO;
import com.heroland.competition.common.enums.GroupByEnum;
import com.heroland.competition.common.enums.OrderByEnum;
import com.heroland.competition.common.utils.AssertUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandStatisticsTotal")
public class HeroLandStatisticsTotalQO extends BaseQO implements Serializable {
    /**
     * 学校机构code 必传
     */
    private String orgCode;
    /**
     * 用户名称

     */
    @ApiModelProperty(value="userName用户名称")
    private String userName;

    /**
     * userid
     */
    @ApiModelProperty(value="userIduserid 查询详情时必填")
    private String userId;

    /**
     * 
     */
    @ApiModelProperty(value="gradeCode年级code")
    private String gradeCode;

    /**
     * 
     */
    @ApiModelProperty(value="gradeName年级名称")
    private String gradeName;

    /**
     * 
     */
    @ApiModelProperty(value="classCode班级code,除了同步作业赛不需要传，其余的必传")
    private String classCode;

    /**
     * 
     */
    @ApiModelProperty(value="className班级名称")
    private String className;

    /**
     * 
     */
    @ApiModelProperty(value="totalScore总分数")
    private String totalScore;

    /**
     * 得分率
     */
    @ApiModelProperty(value="completeRate完成率")
    private Double completeRate;

    /**
     * 正确率
     */
    @ApiModelProperty(value="answerRightRate答对率")
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
     * "type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 "
     */
    private Integer type;

    /**
     * 班级code 多个查询时
     */
    private List<String> classCodes;
    /**
     * 0不是，1是 默认为false
     */
    @MybatisCriteriaAnnotation
    private Boolean history = false;

    /**
     * 科目code
     */
    private String subjectCode;



    private String rankField;

    /**
     * 参数为：
     *      TOTAL_SCORE_DESC( "总分数倒序"),
     *     TOTAL_SCORE_ASC( "总分数正序"),
     *     COMPLETE_RATE_DESC( "完成率倒序"),
     *     COMPLETE_RATE_ASC("完成率正序"),
     *     ANSWERRIGHT_RATE_DESC("答对率倒序"),
     *     ANSWERRIGHT_RATE_ASC("答对率正序"),
     *     WIN_RATE_DESC("胜率倒序"),
     *     WIN_RATE_ASC("胜率正序"),
     *     AVERAGE_SCORE_DESC("平均分倒序"),
     *     AVERAGES_CORE_ASC("平均分正序");
     */
    private OrderByEnum orderByField;


    private String orderField;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;


    private List<String> userIds;

    private String currentUserId;

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void checkType(){

        AssertUtils.assertThat(type != null);
    }


    public List<String> getClassCodes() {
        return classCodes;
    }

    public void setClassCodes(List<String> classCodes) {
        this.classCodes = classCodes;
    }


    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getRankField() {
        return rankField;
    }

    public void setRankField(String rankField) {
        this.rankField = rankField;
    }

    public Boolean getHistory() {
        return history;
    }

    public void setHistory(Boolean history) {
        this.history = history;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public OrderByEnum getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(OrderByEnum orderByField) {
        this.orderByField = orderByField;
    }

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
    public String getTotalScore() {
        return totalScore;
    }

    /**
     *
     * @param totalScore
     */
    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore == null ? null : totalScore.trim();
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
}