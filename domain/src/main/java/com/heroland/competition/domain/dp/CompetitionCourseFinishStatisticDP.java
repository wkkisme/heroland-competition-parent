package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 科目完成情况统计
 *
 * @author wushuaiping
 * @date 2020/7/25 21:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CompetitionCourseFinishStatisticDP extends BaseDO implements Serializable {

    /**
     * 年级
     */
    private String gradeCode;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 科目
     */
    private String courseCode;

    /**
     * 科目名称
     */
    private String courseName;

    /**
     * 班级code
     */
    private String classCode;

    /**
     * 班级code
     */
    private String orgCode;

    /**
     * 题目数量
     */
    private Integer questionNum = 0;

    /**
     * 完成情况
     */
    private Integer finishQuestion = 0;

    /**
     * 胜率
     */
    private Double winRate;

    /**
     * 课数
     */
    private Integer chapterCount;

    /**
     * 节数
     */
    private Integer sectionCount;

    /**
     * 完成多少节
     */
    private Integer finishSection;


    private List<Long> topicIds;

}
