package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/24
 */
@Data
public class HeroLandTopicQuestionsPageRequest extends BaseQO implements Serializable {

    /**
     * 比赛组id
     */
    private Long topicId;
}
