package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class HeroLandTopicAddDepartmentRequest implements Serializable {


    /**
     * 编辑时需要传
     * topicId
     */
    private Long id;


    /**
     * 题目组名称
     */
    @NotNull
    private String topicName;


    /**
     * 类型  4 校级赛 5 世界赛
     * 编辑时不能修改
     */
    @NotNull
    private Integer type;

    /**
     * 开始时间
     * 当校际赛已开始时不能再修改
     */
    @NotNull
    private Date startTime;

    /**
     * 结束时间
     * 当校际赛已结束时不能再修改
     */
    @NotNull
    private Date endTime;

    /**
     * 简介
     */
    private String desc;

    /**
     * 添加的学校科目相关的列表
     * 如果是世界赛则不需要分配，该字段不需要
     */
    List<HerolandTopicAddSchoolCourseRequest> schoolCourses;
}