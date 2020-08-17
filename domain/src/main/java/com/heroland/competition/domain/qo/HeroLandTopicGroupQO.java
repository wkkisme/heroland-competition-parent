package com.heroland.competition.domain.qo;

import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import com.anycommon.response.common.BaseQO;
import com.anycommon.response.utils.MybatisCriteriaMethodEnum;
import com.heroland.competition.domain.dp.HeroLandQuestionDP;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class HeroLandTopicGroupQO extends BaseQO implements Serializable {

    /**
     * topicIds
     */
    private List<Long> topicIds;

    /**
     * 机构code
     */
    private String orgCode;

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
     * 科目code
     */
    private String courseCode;


    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

}