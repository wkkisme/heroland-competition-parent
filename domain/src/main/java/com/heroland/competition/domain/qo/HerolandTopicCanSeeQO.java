package com.heroland.competition.domain.qo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/8/19
 */
@Data
public class HerolandTopicCanSeeQO implements Serializable {

    /**
     *  用户id
     */
    private String userId;

    /**
     * 该用户id可以的操作类型
     *
     */
    private List<String> actionType;

    /**
     * 赛事类型
     */
    private Integer topicType;

    /**
     * 赛事的时间状态
     * 正在进行的
     * 已经过期的
     * 还未开始的
     */
    private String topicState;
}
