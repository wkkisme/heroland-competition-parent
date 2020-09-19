package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Data
public class TopicSCourseForSRequest implements Serializable {

    /**
     * 赛事id
     */
    @NotNull
    private Long topicId;

    /**
     * userId
     */
    @NotNull
    private String userId;


}
