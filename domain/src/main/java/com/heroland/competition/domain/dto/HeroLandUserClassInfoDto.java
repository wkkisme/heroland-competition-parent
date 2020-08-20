package com.heroland.competition.domain.dto;

import com.anycommon.response.common.BaseDO;
import com.heroland.competition.common.utils.AssertUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HeroLandUserClassInfoDto extends BaseDO implements Serializable {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 班级id
     */
    private String classCode;

    /**
     * x学校
     */
    private String orgCode;

    /**
     * 年级code
     */
    private String gradeCode;

    /**
     * 用户类型
     */
    private Integer userType;

    private String className;

    private String gradeName;

    private String orgName;

    /**
     * 班级已经有的人数
     */
    private Integer classHasStudentCount;

    /**
     * 班级默认的容量
     */
    private Integer classDefaultStudentCount;



}