package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseQO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
@Data
public class HerolandChapterQO extends BaseQO implements Serializable {

    private Long id;

    /**
     * 阶段
     */
    private String phaseName;

    /**
     * 科目
     */
    private String courseName;

    /**
     * 版本
     */
    private String editionName;

    /**
     * 章
     */
    private String chapter;
    /**
     * 单元
     */
    private String unit;
    /**
     * 节
     */
    private String section;
}
