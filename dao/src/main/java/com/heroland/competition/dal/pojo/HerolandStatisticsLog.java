package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.HerolandStatisticsLog")
public class HerolandStatisticsLog extends BaseDO implements Serializable {
    /**
     * topicId
     */
    @ApiModelProperty(value="topicIdtopicId")
    private Long topicId;

    /**
     * 统计类型
     */
    @ApiModelProperty(value="type统计类型")
    private Integer type;

    /**
     * 是否完成了统计
     */
    @ApiModelProperty(value="isFinished是否完成了统计")
    private Boolean isFinished;

    /**
     * heroland_statistics_log
     */
    private static final long serialVersionUID = 1L;

    /**
     * topicId
     * @return topic_id topicId
     */
    public Long getTopicId() {
        return topicId;
    }

    /**
     * topicId
     * @param topicId topicId
     */
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    /**
     * 统计类型
     * @return type 统计类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 统计类型
     * @param type 统计类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 是否完成了统计
     * @return is_finished 是否完成了统计
     */
    public Boolean getIsFinished() {
        return isFinished;
    }

    /**
     * 是否完成了统计
     * @param isFinished 是否完成了统计
     */
    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }
}