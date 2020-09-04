package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Data
public class HerolandTopicJoinRequest implements Serializable {

    /**
     * heroland_topic_group表的id
     */
    @NotNull
    private Long topicId;

    /**
     * 用户id
     * 真正报名参赛的学生ids
     */
    @NotNull
    private List<String> joinUsers;

    /**
     * 报名userid 学生自己或者老师
     */
    private String registerUser;

    /**
     * 状态，参与，取消参与
     * 参与 ：JOINED
     * 取消参与：CANCELED
     */
    @NotNull
    private String state;

    /**
     * 取消人userid
     * 学生自己或者老师
     */
    private String cancelUser;

    /**
     * 取消原因
     */
    private String cancelReason;
}
