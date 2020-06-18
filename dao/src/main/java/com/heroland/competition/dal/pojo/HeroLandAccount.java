package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandAccount")
public class HeroLandAccount extends BaseDO implements Serializable {
    /**
     * 账户余额
     */
    @ApiModelProperty(value="balance账户余额")
    private Long balance;

    /**
     * 用户id
     */
    @ApiModelProperty(value="userId用户id")
    private String userId;

    /**
     * 账户id
     */
    @ApiModelProperty(value="accountId账户id")
    private String accountId;

    /**
     * 级别名称
     */
    @ApiModelProperty(value="levelName级别名称")
    private String levelName;

    /**
     * 级别分数
     */
    @ApiModelProperty(value="levelScore级别分数")
    private Integer levelScore;

    /**
     * heroland_account
     */
    private static final long serialVersionUID = 1L;

    /**
     * 账户余额
     * @return balance 账户余额
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * 账户余额
     * @param balance 账户余额
     */
    public void setBalance(Long balance) {
        this.balance = balance;
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
     * 账户id
     * @return account_id 账户id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 账户id
     * @param accountId 账户id
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * 级别名称
     * @return level_name 级别名称
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * 级别名称
     * @param levelName 级别名称
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    /**
     * 级别分数
     * @return level_score 级别分数
     */
    public Integer getLevelScore() {
        return levelScore;
    }

    /**
     * 级别分数
     * @param levelScore 级别分数
     */
    public void setLevelScore(Integer levelScore) {
        this.levelScore = levelScore;
    }
}