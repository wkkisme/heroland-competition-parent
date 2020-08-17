package com.heroland.competition.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Data
public class HerolandTopicJoinStatisticsRequest implements Serializable {

    /**
     * heroland_topic_group表的id
     */
    private Long topicId;

    /**
     * 状态，参与，取消参与
     * "JOINED" 参与
     * "CANCELED" 取消参与
     */
    private String state;

}
