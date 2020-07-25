package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
public class HerolandQuestionKnowledgeSimpleDto implements Serializable {

    /**
     * 题号
     */
    private Long questionId;
    /**
     * 知识点
     */
    private List<String> knowledge;

}
