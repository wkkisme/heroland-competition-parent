package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 世界赛简单信息dto
 *
 */
@Data
public class HeroLandTopicForWDto implements Serializable {

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
     * 报名开始时间
     */
    private Date registerbeginTime;

    /**
     * 报名结束时间
     */
    private Date registerEndTime;

    /**
     * 已经报名的人数
     */
    private Long registerCount;

    /**
     * 报名限制人数
     */
    private Long countLimit;

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
     * 已参与 --JOINED
     * 未参与 -- NONJOINED
     * 推荐参赛时是 NONJOINED
     * 弹出即将的比赛时是 JOINED
     *
     */
    private String studentJoinState;

    /**
     * 学生比赛是否完成的状态
     * * 已完成比赛 -- FINISHED
     * * 未参与 -- UNFINISHED
     */
    private String studentFinishState;




}
