package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Data
public class HerolandTopicCanJoinDto implements Serializable {

    /**
     * 赛事id
     */
    private List<HeroLandTopicForSDto> topicList;

    /**
     * 参与人的userId
     */
    private String joinUserId;

    /**
     * 操作人的userId
     * 目前只有班主任可以操作
     */
    private String operator;

}
