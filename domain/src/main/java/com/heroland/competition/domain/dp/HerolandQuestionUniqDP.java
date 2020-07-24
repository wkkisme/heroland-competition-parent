package com.heroland.competition.domain.dp;

import lombok.Data;

/**
 * @author smjyouzan
 * @date 2020/7/24
 * 同一个qtId 会有多个snapshot
 */
@Data
public class HerolandQuestionUniqDP {

    /**
     * 题库的主键id
     */
    private Long id;

    /**
     * 题目id
     */
    private String qtId;

    /**
     * 快照id
     */
    private Integer snapshotNo;
}
