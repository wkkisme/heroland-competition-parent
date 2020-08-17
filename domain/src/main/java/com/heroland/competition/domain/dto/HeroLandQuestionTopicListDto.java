package com.heroland.competition.domain.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @date 2020/7/12
 */
@Data
public class HeroLandQuestionTopicListDto implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 机构code
     */
    private String orgCode;

    private String orgName;

    /**
     * 题目组名称
     */
    private String topicName;

    /**
     * 年级code
     */
    private String gradeCode;

    private String gradeName;

    /**
     * 班级code
     */
    private String classCode;

    private String className;

    /**
     * 科目code
     */
    private String courseCode;

    private String courseName;

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

    /**
     * 题组难度
     */
    private String levelCode;

    /**
     * 题型
     */
    private Integer diff;


    /**
     * 关联的题号和知识点列表
     */
    private List<HeroLandQuestionListForTopicDto> questions = Lists.newArrayList();

}
