package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HeroLandTopicQuestionsPageRequest extends BaseQO implements Serializable {

    /**
     * 比赛组id
     */
    private List<Long> topicIds;

    private Long topicId;

    /**
     * 题目id
     */
    private Long questionId;
}
