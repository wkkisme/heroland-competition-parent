package com.heroland.competition.domain.qo;

import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.utils.AssertUtils;

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

    public HeroLandAccountManageQO queryCheck(){

        AssertUtils.notBlank(userId,accountId);
        AssertUtils.assertThat(competitionType != null);
        AssertUtils.assertThat(num != null);
        return this;
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
}
