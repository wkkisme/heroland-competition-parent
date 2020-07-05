package com.heroland.competition.domain.dp;

import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.MybatisCriteriaMethodEnum;
import com.anycommon.response.utils.ResponseBodyWrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @ApiModelProperty(value = "currentTotalScore当前数据总分数")
    private Integer currentTotalScore;

    @ApiModelProperty(value = "winRate当前数据总胜率")
    private String winRate;


    @ApiModelProperty(value = "records比赛记录")
    private List<HeroLandCompetitionRecordDP> records;

    public HeroLandAccountDP queryCheck() {
        if (StringUtils.isBlank(this.userId)) {
            ResponseBodyWrapper.failParamException();
        }
        return this;
    }

    public Integer getCurrentTotalScore() {
        if (!CollectionUtils.isEmpty(records) && currentTotalScore == null) {
            for (HeroLandCompetitionRecordDP record : records) {
                this.currentTotalScore += record.getInviteScore();
                this.currentTotalScore += record.getOpponentScore();
            }
        }
        return currentTotalScore;
    }

    public String getWinRate() {
        if (!CollectionUtils.isEmpty(records)) {
            int totalCount = records.size();
            int winCount = 0;
            for (HeroLandCompetitionRecordDP record : records) {
                /*
                 * 0 邀请方胜利。1 被邀请方胜利。2 平局
                 */
                if (record.getInviteId().equals(this.userId) && record.getResult() == 0) {
                    winCount += 1;
                }
                if (record.getOpponentId().equals(this.userId) && record.getResult() == 1) {
                    winCount += 1;
                }

            }
            return (winCount / totalCount) + "";
        }

        return winRate;
    }

    public void setWinRate(String winRate) {
        this.winRate = winRate;
    }

    public void setCurrentTotalScore(Integer currentTotalScore) {
//        if (!CollectionUtils.isEmpty(records)) {
//            for (HeroLandCompetitionRecordDP record : records) {
//                currentTotalScore += record.getInviteScore();
//                currentTotalScore += record.getOpponentScore();
//            }
//        }
        this.currentTotalScore = currentTotalScore;
    }

    public List<HeroLandCompetitionRecordDP> getRecords() {
        return records;
    }

    public void setRecords(List<HeroLandCompetitionRecordDP> records) {
        this.records = records;
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
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
                Objects.equals(levelScore, that.levelScore) &&
                Objects.equals(competitionType, that.competitionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, userId, accountId, levelName, levelScore, competitionType);
    }
}