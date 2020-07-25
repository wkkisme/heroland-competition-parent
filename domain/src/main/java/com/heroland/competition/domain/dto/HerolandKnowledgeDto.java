package com.heroland.competition.domain.dto;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class HerolandKnowledgeDto extends BaseDO implements Serializable {

    /**
     * 年级
     */
    private String grade;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 科目
     */
    private String course;
    /**
     * 科目名称
     */
    private String courseName;

    /**
     * 知识点
     */
    private String knowledge;

    /**
     * 知识点难度level
     */
    private Integer diff;

    /**
     * 知识点难度
     */
    private String diffName;

}
