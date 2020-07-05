package com.heroland.competition.domain.qo;


import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import com.anycommon.response.common.BaseQO;
import com.anycommon.response.utils.MybatisCriteriaMethodEnum;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * heroland-competition-parent
 *
 * @author wangkai
 * @date 2020/6/30
 */

public class HeroLandAccountQO extends BaseQO {
    @MybatisCriteriaAnnotation
    private String userId;

    @ApiModelProperty(value = "startTime开始时间")
    @MybatisCriteriaAnnotation(method = MybatisCriteriaMethodEnum.AND_GREATER_THAN_OR_EQUAL_TO)
    private Date startTime;

    @ApiModelProperty(value = "endTime结束时间")
    private Date endTime;

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
