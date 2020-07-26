package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandStatisticsDetail")
public class HeroLandStatisticsDetailDP extends BaseDO implements Serializable {
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
     * 排名情况
     */
    private Long rank;

    private Long rightCount;

    public Long getRightCount() {
        return rightCount;
    }

    public void setRightCount(Long rightCount) {
        this.rightCount = rightCount;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Boolean getHistory() {
        return history;
    }

    public void setHistory(Boolean history) {
        this.history = history;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getOrgCode() {
        return orgCode;
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

    public Integer getTotalScore() {
        return totalScore;
    }

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
}