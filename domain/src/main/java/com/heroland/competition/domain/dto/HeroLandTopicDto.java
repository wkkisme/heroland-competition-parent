package com.heroland.competition.domain.dto;

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
     * 题目组
     */
    private List<HeroLandQuestionListForTopicDto> questions;

    /**
     * 题组难度
     */
    private String levelCode;

    /**
     * 题型
     */
    private Integer diff;

    private Integer result;

    private String opponent;

    /**
     * 是否回答正确
     */
    private Boolean correctAnswer;


    /**
     * 得分
     */
    private Integer score;

}
