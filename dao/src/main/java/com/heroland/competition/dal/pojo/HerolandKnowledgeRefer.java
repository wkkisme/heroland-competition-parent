package com.heroland.competition.dal.pojo;


import com.anycommon.response.common.BaseDO;
import lombok.Data;

@Data
public class HerolandKnowledgeRefer extends BaseDO {

    private Long referId;

    private Long knowledgeId;

    private Integer type;

}