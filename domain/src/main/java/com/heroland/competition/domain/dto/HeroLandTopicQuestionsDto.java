package com.heroland.competition.domain.dto;

import com.heroland.competition.domain.dp.HerolandQuestionUniqDP;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/24
 */
@Data
public class HeroLandTopicQuestionsDto implements Serializable {

    /**
     * topic id
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
     * 年级code
     */
    private String gradeCode;

    /**
     * 班级code
     */
    private String classCode;

    /**
     * 类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     */
    private Integer type;

    /**
     * 题目组id
     */
    private String topicId;

    /**
     * 题目组
     */
    private List<HeroLandQuestionListForTopicDto> questions;

}
