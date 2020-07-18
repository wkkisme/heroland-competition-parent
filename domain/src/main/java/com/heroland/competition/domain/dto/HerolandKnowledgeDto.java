package com.heroland.competition.domain.dto;

import com.anycommon.response.common.BaseDO;
import com.google.common.collect.Lists;
import com.heroland.competition.common.contants.DiffEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
