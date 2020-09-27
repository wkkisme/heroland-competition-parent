package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Data
public class TopicWForStudentJoinedRequest implements Serializable {

    /**
     * userId
     */
    @NotNull
    private String userId;

    /**
     * 赛事类型，只支持
     * 4 校际赛
     * 5 世界赛
     */
    @NotNull
    private Integer topicType;



}
