package com.heroland.competition.domain.dp;

import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.MybatisCriteriaMethodEnum;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.IDGenerateUtils;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import static com.heroland.competition.common.utils.IDGenerateUtils.ModelEnum.ADMIN;

@ApiModel(value = "com.heroland.competition.dal.pojo.HeroLandAccount")
public class HeroLandAccountDP extends BaseDO implements Serializable {
    /**
     * 账户余额
     */
    @ApiModelProperty(value = "balance账户余额")
    private Long balance;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "userId用户id")
    @MybatisCriteriaAnnotation
    private String userId;

    /**
     * 用户名称
     */
    @ApiModelProperty(value="userName用户名称")
    private String userName;

    /**
     * 账户id
     */
    @ApiModelProperty(value = "accountId账户id")
    @MybatisCriteriaAnnotation
    private String accountId;

    /**
     * 级别名称
     */
    @ApiModelProperty(value = "levelName级别名称")
    private String levelName;

    /**
     * 级别分数
     */
    @ApiModelProperty(value = "levelScore级别分数")
    private Integer levelScore;

    @ApiModelProperty(value = "competitionType比赛类型")
    private String competitionType;

    @ApiModelProperty(value = "startTime开始时间")
    @MybatisCriteriaAnnotation(method = MybatisCriteriaMethodEnum.AND_GREATER_THAN_OR_EQUAL_TO)
    private Date startTime;

    @ApiModelProperty(value = "endTime结束时间")
    private Date endTime;

    /**
     * 等级code
     */
    private String levelCode;

    /**
     * 比赛topicId
     */
    private String topicId;

    private String classCode;

    private Integer userStatus;

    private String topic;
    private String topicType;

    public String getTopicType() {
        return topicType;
    }

    public void setTopicType(String topicType) {
        this.topicType = topicType;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public HeroLandAccountDP queryCheck() {
        if (StringUtils.isBlank(this.userId)) {
            ResponseBodyWrapper.failParamException();
        }
        return this;
    }

    public HeroLandAccountDP addCheck(long balance) {

        AssertUtils.notBlank(userId);

        accountId = IDGenerateUtils.getIdByRandom(ADMIN) + "";

        if ("".equals(accountId)){
            accountId = TinyId.nextId("invite")+"";
        }
        super.beforeInsert();

        this.balance= balance;

        if (levelScore == null){
            levelScore = 0;
        }

        return this;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
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

    /**
     * heroland_account
     */
    private static final long serialVersionUID = 1L;

    /**
     * 账户余额
     *
     * @return balance 账户余额
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * 账户余额
     *
     * @param balance 账户余额
     */
    public void setBalance(Long balance) {
        this.balance = balance;
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
     * 账户id
     *
     * @return account_id 账户id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 账户id
     *
     * @param accountId 账户id
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * 级别名称
     *
     * @return level_name 级别名称
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * 级别名称
     *
     * @param levelName 级别名称
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    /**
     * 级别分数
     *
     * @return level_score 级别分数
     */
    public Integer getLevelScore() {
        return levelScore;
    }

    /**
     * 级别分数
     *
     * @param levelScore 级别分数
     */
    public void setLevelScore(Integer levelScore) {
        this.levelScore = levelScore;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HeroLandAccountDP that = (HeroLandAccountDP) o;
        return Objects.equals(balance, that.balance) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(levelName, that.levelName) &&
                Objects.equals(levelScore, that.levelScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, userId, accountId, levelName, levelScore);
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }
}