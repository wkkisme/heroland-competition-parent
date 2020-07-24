package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.contants.AdminFieldEnum;
import com.heroland.competition.common.utils.IDGenerateUtils;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ApiModel(value = "com.heroland.competition.dal.pojo.HeroLandTopicGroup")
@Data
public class HeroLandTopicGroupDP extends BaseDO implements Serializable {
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

    /**
     * 题目组id
     */
    @ApiModelProperty(value = "topicId题目组id")
    private String topicId;

    @ApiModelProperty(value = "questions题目")
    private List<HerolandQuestionUniqDP> questions;


    public HeroLandTopicGroupDP addCheckAndInit() {
        if (StringUtils.isAnyBlank(this.getOrgCode(), this.getTopicName())) {
            ResponseBodyWrapper.failParamException();
        }

        if (this.getType() == null) {
            ResponseBodyWrapper.failParamException();
        }
        this.topicId = IDGenerateUtils.getKey(AdminFieldEnum.TOPIC);
        this.beforeInsert();
        return this;
    }

    public HeroLandTopicGroupDP deleteCheck() {
        if (this.getId() == null) {
            ResponseBodyWrapper.failParamException();
        }
        this.beforeDelete();

        return this;
    }
}