package com.heroland.competition.domain.request;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.domain.dp.HeroLandQuestionDP;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class HeroLandTopicGroupRequest implements Serializable {

    /**
     * 编辑时需要传
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
     * 科目code
     */
    private String courseCode;

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
}