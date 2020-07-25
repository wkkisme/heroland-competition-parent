package com.heroland.competition.domain.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HeroLandTopicChapterAssignRequest implements Serializable {

    /**
     * 章节id
     */
    private Long chapterId;

    /**
     * 题目id
     */
    private List<Long> questionIds;

}