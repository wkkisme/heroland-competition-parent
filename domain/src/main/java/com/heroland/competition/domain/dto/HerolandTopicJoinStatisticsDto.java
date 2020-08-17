package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Data
public class HerolandTopicJoinStatisticsDto implements Serializable {

    /**
     * 赛事id
     */
    private Long topicId;

    /**
     * 人数
     */
    private Integer totalUserCount;

    /**
     * 状态，参与，取消参与
     * "JOINED" 参与
     * "CANCELED" 取消参与
     */
    private String state;

}
