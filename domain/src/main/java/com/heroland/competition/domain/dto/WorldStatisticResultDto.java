package com.heroland.competition.domain.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author smjyouzan
 * @date 2020/9/28
 */
@Data
public class WorldStatisticResultDto implements Serializable {

    /**
     * userid
     */
    @ApiModelProperty(value="userIduserid")
    private String userId;

    /**
     * 姓名
     */
    @ApiModelProperty(value="userName姓名")
    private String userName;

    /**
     * 学校code
     */
    private String schoolCode;

    /**
     * 学校name
     */
    private String schoolName;

    /**
     * topicId
     */
    @ApiModelProperty(value="topicIdtopicId")
    private Long topicId;

    /**
     * 总分数
     */
    @ApiModelProperty(value="totalScore总分数")
    private Integer totalScore;

    /**
     * 排名
     */
    @ApiModelProperty(value="totalRank排名")
    private Integer totalRank;

    /**
     * 得分率
     */
    @ApiModelProperty(value="completeRate得分率")
    private Double completeRate;

    /**
     * 正确率
     */
    @ApiModelProperty(value="answerRightRate正确率")
    private Double answerRightRate;

    /**
     * 胜率
     */
    @ApiModelProperty(value="winRate胜率")
    private Double winRate;

    /**
     * 总时长单位s
     */
    @ApiModelProperty(value="totalTime总时长单位s")
    private Integer totalTime;

    /**
     * 平均分
     */
    @ApiModelProperty(value="averageScore平均分")
    private Double averageScore;

    /**
     * 年级code
     */
    @ApiModelProperty(value="gradeCode年级code")
    private String gradeCode;

    /**
     * 年级名称
     */
    @ApiModelProperty(value="gradeName年级名称")
    private String gradeName;

    /**
     * topic的开始时间
     */
    @ApiModelProperty(value="startTimetopic的开始时间")
    private Date startTime;

    /**
     * topic的结束时间
     */
    @ApiModelProperty(value="endTimetopic的结束时间")
    private Date endTime;

    private String topicName;


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
}
