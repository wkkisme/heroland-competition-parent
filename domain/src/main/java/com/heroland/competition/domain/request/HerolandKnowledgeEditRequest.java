package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 */
@Data
public class HerolandKnowledgeEditRequest implements Serializable {

    /**
     * id
     */
    @NotNull
    private Long id;

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
