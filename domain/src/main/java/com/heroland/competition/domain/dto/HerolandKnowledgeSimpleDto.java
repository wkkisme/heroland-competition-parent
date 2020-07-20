package com.heroland.competition.domain.dto;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class HerolandKnowledgeSimpleDto extends BaseDO implements Serializable {

    /**
     * 知识点
     */
    private String knowledge;

}
