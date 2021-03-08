package com.heroland.competition.domain.qo;


import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import com.anycommon.response.common.BaseQO;
import com.anycommon.response.utils.MybatisCriteriaMethodEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * heroland-competition-parent
 *
 * @author wangkai
 * @date 2020/6/30
 */

@Data
public class HeroLandAccountQO extends BaseQO {
    /**
     * userId用户id 必须
     */
    @MybatisCriteriaAnnotation
    @ApiModelProperty(value = "userId用户id",required = false)
    private String userId;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "startTime开始时间")
    @MybatisCriteriaAnnotation(method = MybatisCriteriaMethodEnum.AND_GREATER_THAN_OR_EQUAL_TO)
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "endTime结束时间")
    private Date endTime;

    private String accountId;

    private Integer topicType;

    private String orgCode;

    private String classCode;

    private String gradeCode;
    public Integer getTopicType() {
        return topicType;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
