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
import java.util.List;
import java.util.UUID;

@Data
@ApiModel(value = "com.heroland.competition.dal.pojo.HeroLandTopicGroup")
public class HeroLandTopicGroupRequest implements Serializable {

    /**
     * 编辑时需要传
     */
    private Long id;
    /**
     * 机构code
     */
    @ApiModelProperty(value = "orgCode机构code")
    private String orgCode;

    /**
     * 题目组名称
     */
    @ApiModelProperty(value = "topicName题目组名称")
    private String topicName;

    /**
     * 年级code
     */
    @ApiModelProperty(value = "gradeCode年级code")
    private String gradeCode;

    /**
     * 班级code
     */
    @ApiModelProperty(value = "classCode班级code")
    private String classCode;

    /**
     * 类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     */
    @ApiModelProperty(value = "type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 ")
    private Integer type;
}