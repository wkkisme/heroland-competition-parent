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
public class TopicQuestionsForSRequest implements Serializable {

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

    /**
     * 科目code
     */
    private String courseCode;


    /**
     * 是否查看
     */
    private Boolean toSee;
}
