package com.heroland.competition.domain.request;

import com.google.common.collect.Lists;
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
     * 报名开始时间
     * 针对世界赛
     */
    @NotNull
    private Date registerBeginTime;

    /**
     * 报名结束时间
     * 针对世界赛
     */
    @NotNull
    private Date registerEndTime;

    /**
     * 比赛的限制人数
     * 针对世界赛
     */
    @NotNull
    private Long countLimit;

    /**
     * 简介
     */
    private String desc;

    /**
     * 添加的学校科目相关的列表
     * 如果是世界赛则不需要分配，该字段不需要
     */
    private List<HerolandTopicAddSchoolCourseRequest> schoolCourses;

    /**
     * 创建世界赛时需要的年级和科目
     */
    private List<HerolandTopicAddSchoolCourseForWorldRequest> gradeCoursesForWorld;

    /**
     * 创建世界赛时选择的题目
     */
    private List<Long> questionIds = Lists.newArrayList();

    private String orgCode;

}