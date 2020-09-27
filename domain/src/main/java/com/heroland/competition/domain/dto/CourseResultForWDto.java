package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author smjyouzan
 * @date 2020/9/27
 */
@Data
public class CourseResultForWDto implements Serializable {
    /**
     * 赛事id
     */
    private Long topicId;

    private String courseCode;

    /**
     * 科目名称
     */
    private String courseName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 得分
     */
    private Integer totalScore;

    /**
     * 排名
     */
    private Integer rank;

    /**
     * 用时
     */
    private Integer totalUseTime;

}
