package com.heroland.competition.domain.dto;

import com.heroland.competition.domain.request.HerolandTopicAddSchoolCourseForWorldRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/24
 */
@Data
public class HeroLandTopicDto implements Serializable {

    /**
     * topic id
     */
    private Long id;

    /**
     * 机构code
     */
    private String orgCode;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 题目组名称
     */
    private String topicName;

    /**
     * 年级code
     */
    private String gradeCode;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 班级code
     */
    private String classCode;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 科目code
     */
    private String courseCode;

    /**
     * 科目名称
     */
    private String courseName;

    /**
     * 类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     */
    private Integer type;

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
     * 题目组
     */
    private List<HeroLandQuestionListForTopicDto> questions;

    /**
     * 世界赛时需要的年级和科目
     */
    private List<HerolandTopicAddSchoolCourseForWorldDto> gradeCoursesForWorld;

    /**
     * 题组难度
     */
    private String levelCode;

    /**
     * 题型
     */
    private Integer diff;

    private Integer result;

    private Integer opponent;

    /**
     * 是否回答正确
     */
    private Boolean correctAnswer;


    /**
     * 得分
     */
    private Integer score;


    private Long topicId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.topicId = id;
        this.id = id;
    }
}
