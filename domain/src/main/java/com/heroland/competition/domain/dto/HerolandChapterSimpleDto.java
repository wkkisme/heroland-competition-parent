package com.heroland.competition.domain.dto;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class HerolandChapterSimpleDto extends BaseDO implements Serializable {

    /**
     * 类型：
     * 1 章|单元 2 课节 3 小节
     */
    private Integer contentType;

    /**
     * 内容
     */
    private String content;

    /**
     * 顺序
     */
    private Integer order;

}
