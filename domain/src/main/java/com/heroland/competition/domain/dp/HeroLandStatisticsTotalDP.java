package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.IDGenerateUtils;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.heroland.competition.common.utils.IDGenerateUtils.ModelEnum.DEFAULT;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandStatisticsTotal")
public class HeroLandStatisticsTotalDP extends BaseDO implements Serializable {
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
     * 排名情况
     */
    private Long rank;

    /**
     * 详情
     */
    private List<HeroLandStatisticsDetailDP> details;



    public HeroLandStatisticsTotalDP addCheck(){
        this.beforeInsert();
        AssertUtils.notBlank(userId);
        AssertUtils.notBlank(orgCode);
        try {
            this.totalId = TinyId.nextId("statistics").toString();
        } catch (Exception e) {
            e.printStackTrace();
            this.totalId = IDGenerateUtils.getIdByRandom(DEFAULT)+"";
        }

        return this;
    }

    public HeroLandStatisticsTotalDP updateCheck(){
        AssertUtils.notBlank(userId);
        AssertUtils.notBlank(orgCode);
        AssertUtils.notBlank(totalId);
        this.beforeUpdate();
        return this;
    }
    public HeroLandStatisticsTotalDP updateTotalAndDetailCheck(){
        AssertUtils.notBlank(userId);
        AssertUtils.notBlank(orgCode);
        AssertUtils.notBlank(totalId);
        AssertUtils.assertThat(CollectionUtils.isEmpty(details));
        for (HeroLandStatisticsDetailDP detail : details) {
            AssertUtils.notBlank(detail.getDetailId());
            AssertUtils.notBlank(detail.getTotalId());
            AssertUtils.notBlank(detail.getUserId());
            detail.beforeUpdate();
        }
        this.beforeUpdate();

        return this;
    }


    public HeroLandStatisticsTotalDP addTotalAndDetailCheck(){
        AssertUtils.notBlank(userId);
        AssertUtils.notBlank(orgCode);
        this.beforeInsert();
        try {
            this.totalId = TinyId.nextId("statistics").toString();
        } catch (Exception e) {
            e.printStackTrace();
            this.totalId = IDGenerateUtils.getIdByRandom(DEFAULT)+"";
        }
        AssertUtils.assertThat(CollectionUtils.isEmpty(details));
        for (HeroLandStatisticsDetailDP detail : details) {
            try {
                detail.beforeInsert();
                detail.setDetailId(TinyId.nextId("statistics").toString());
            } catch (Exception e) {
                e.printStackTrace();
                detail.setDetailId(IDGenerateUtils.getIdByRandom(DEFAULT)+"");
            }
            detail.setTotalId(totalId);
        }

        return this;
    }


    public List<HeroLandStatisticsDetailDP> getDetails() {
        return details;
    }

    public void setDetails(List<HeroLandStatisticsDetailDP> details) {
        this.details = details;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getHistory() {
        return history;
    }

    public void setHistory(Boolean history) {
        this.history = history;
    }
}