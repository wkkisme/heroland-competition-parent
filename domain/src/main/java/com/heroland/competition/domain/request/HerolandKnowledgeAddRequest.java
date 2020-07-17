package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseDO;
import com.google.common.collect.Lists;
import com.heroland.competition.common.contants.DiffEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 */
@Data
public class HerolandKnowledgeAddRequest implements Serializable {

    /**
     * 年级
     */
    @NotNull
    private String grade;

    /**
     * 科目
     */
    @NotNull
    private String course;

    /**
     * 知识点
     */
    @NotNull
    private String knowledge;


    /**
     * 知识点难度level
     */
    @NotNull
    private Integer diff;

}
