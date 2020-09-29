package com.heroland.competition.domain.request;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HeroLandTopicAssignRequest implements Serializable {

    /**
     * 赛事id
     */
    private Long topicId;

    /**
     * 如果是根据章节选题，需要带上章节id
     */
    private List<HeroLandTopicChapterAssignRequest> chapterQuestions = Lists.newArrayList();

    /**
     * 如果不是根据章节选题，比如直接根据科目，直接用questionIds
     */
    private List<Long> questionIds = Lists.newArrayList();


    private String orgCode;

}