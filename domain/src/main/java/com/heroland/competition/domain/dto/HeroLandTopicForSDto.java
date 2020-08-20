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
     * 学生角色参与的状态
     * 参与 和 未参与
     */
    private String studentJoinState;





}
