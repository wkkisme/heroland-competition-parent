package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

@Data
public class HerolandTopicQuestion extends BaseDO {

    private Long questionId;

    private Long topicId;
}