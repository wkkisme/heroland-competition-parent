package com.heroland.competition.domain.qo;

import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.utils.AssertUtils;

import javax.validation.constraints.NotNull;

/**
 * 钻石账户相关
 * @author mac
 */
public class HeroLandAccountManageQO {

    private String userId;

    private String orgCode;

    private String classCode;

    /**
     * 比赛类型
     */
    private CompetitionEnum competitionType;

    /**
     * 账户扣除描述
     */
    private String remark;

    /**
     * 账户id
     */
    private String accountId;

    /**
     * 增加或者扣除数量
     */
    private Integer num;

    /**
     * 增加或者扣除数量
     */
    private Integer score;


    /**
     * 业务名称
     * ANALYSE("ANALYSE","解说分析"),
     * SPY("SPY","侦查敌情"),
     * TIPS("TIPS","解题技巧"),
     * SKIP("SKIP","越级挑战"),
     * SAME("SAME","赛类似题"),
     *
     *
     * 传参：ANALYSE ...
     * 目前不完整，新增需要业务方来后端注册
     */
    private String bizName;

    /**
     * 业务组别
     * 比如有 对赛策略，应试训练等
     *
     *  COMPETITON("GAME","对赛策略"),
     *
     * 新增组别需要在后端注册
     */
    private String bizGroup;


    public HeroLandAccountManageQO queryDecrCheck(){

        AssertUtils.notBlank(userId);
        AssertUtils.assertThat(competitionType != null);
        AssertUtils.assertThat(num != null);
        return this;
    }
   public HeroLandAccountManageQO queryIncrCheck(){

        AssertUtils.notBlank(userId);
        AssertUtils.assertThat(num != null);
        return this;
    }


    public  void  scoreCheck(){
        AssertUtils.notBlank(userId);
        AssertUtils.notNull(score);
    }
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public CompetitionEnum getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(CompetitionEnum competitionType) {
        this.competitionType = competitionType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getBizGroup() {
        return bizGroup;
    }

    public void setBizGroup(String bizGroup) {
        this.bizGroup = bizGroup;
    }
}
