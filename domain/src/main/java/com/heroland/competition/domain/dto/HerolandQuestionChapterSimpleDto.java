package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
public class HerolandQuestionChapterSimpleDto implements Serializable {

    /**
     * 题号
     */
    private Long chapterId;

    /**
     * 类型
     * 章 课节 小节
     */
    private Integer chapterType;

    /**
     * 知识点
     */
    private List<String> knowledge;

}
