package com.heroland.competition.domain.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HeroLandTopicGroupRequest implements Serializable {

    /**
     * 编辑时需要传
     */
    private Long id;
    /**
     * 机构code
     */
    private String orgCode;

    /**
     * 题目组名称
     */
    private String topicName;

    /**
     * 应试赛|作业赛年级code
     *
     */
    private String gradeCode;

    /**
     *  应试赛|作业赛班级code
     */
    private String classCode;

    /**
     *  应试赛|作业赛科目code
     */
    private String courseCode;

    /**
     * 类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     */
    private Integer type;

    /**
     * 比赛开始时间
     */
    private Date startTime;

    /**
     * 比赛结束时间
     */
    private Date endTime;


}