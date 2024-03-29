package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author smjyouzan
 * @date 2020/9/27
 */
@Data
public class AllCourseResultForWDto implements Serializable {


    private String courseCode;

    /**
     * 科目名称
     */
    private String courseName;

    /**
     * 得分
     */
    private Integer totalScore;

    /**
     * 平均分
     */
    private Integer averageScore;

    /**
     * 排名
     */
    private Integer rank;

    /**
     * 胜率
     */
    private double winRate;

    /**
     * 正确率
     */
    private double rightRate;

    /**
     * 平均用时 秒
     */
    private Integer averageTime;

    /**
     * 总场数
     */
    private Integer totalTopics;

}
