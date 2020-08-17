package com.heroland.competition.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Data
public class QualifiedTopicForSchoolRequest implements Serializable {

    /**
     * 赛事id
     */
    private Long topicId;
}
