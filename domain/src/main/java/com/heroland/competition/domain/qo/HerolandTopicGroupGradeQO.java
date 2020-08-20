package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HerolandTopicGroupGradeQO extends BaseDO implements Serializable {
    /**
     * 机构code
     */
    private String orgCode;

    /**
     * 年级code
     */
    private List<String> gradeCodes;


}