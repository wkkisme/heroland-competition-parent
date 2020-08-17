package com.heroland.competition.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Data
public class HerolandTopicJoinRequest implements Serializable {

    /**
     * heroland_topic_group表的id
     */
    private Long topicId;

    /**
     * 用户id
     */
    private String joinUser;

    /**
     * 报名userid
     */
    private String registerUser;

    /**
     * 状态，参与，取消参与
     */
    private String state;

    /**
     * 取消人userid
     */
    private String cancelUser;

    /**
     * 取消原因
     */
    private String cancelReason;
}
