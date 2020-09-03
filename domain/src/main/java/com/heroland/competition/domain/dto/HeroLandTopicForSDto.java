package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 校际赛简单信息dto
 *
 */
@Data
public class HeroLandTopicForSDto implements Serializable {

    /**
     * 校际赛topicId
     */
    private Long topicId;

    /**
     * 校际赛的名称
     */
    private String topicName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 简介
     */
    private String desc;

    /**
     * 状态 已结束 进行中 未开始
     * FINISHED
     * DOING
     * NOTSTART
     */
    private String state;


    /**
     * 学生角色参与的状态
     * 参与 和 未参与
     */
    private String studentJoinState;





}
