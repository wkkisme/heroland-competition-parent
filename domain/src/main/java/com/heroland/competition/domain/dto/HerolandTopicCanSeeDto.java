package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Data
public class HerolandTopicCanSeeDto implements Serializable {

    /**
     * 可以看到的赛事列表
     * 这个针对该年级下的学生和老师
     */
    private List<HeroLandTopicForSDto> canSeeTopicList;

    /**
     * 可以操作的赛事列表
     * 这个只针对班主任
     */
    private List<HeroLandTopicForSDto> canOperatorTopicList;

    /**
     * 可以参加的赛事列表
     * 这个只针对学生
     */
    private List<HeroLandTopicForSDto> canJoinTopicList;

}
