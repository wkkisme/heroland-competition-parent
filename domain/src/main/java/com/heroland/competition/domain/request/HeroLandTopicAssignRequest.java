package com.heroland.competition.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HeroLandTopicAssignRequest implements Serializable {

    /**
     * 赛事id
     */
    private Long topicId;

    /**
     * 题目id
     */
    private List<Long> questionIds;

}