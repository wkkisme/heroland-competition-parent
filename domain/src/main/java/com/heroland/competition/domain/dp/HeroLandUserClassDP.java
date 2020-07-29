package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandUserClass")
public class HeroLandUserClassDP extends BaseDO implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value="userId用户id")
    private String userId;

    /**
     * 课程id
     */
    @ApiModelProperty(value="classCode课程id")
    private String classCode;

    /**
     * x学校
     */
    @ApiModelProperty(value="orgCodex学校")
    private String orgCode;

    /**
     * 班级id
     */
    @ApiModelProperty(value="classId班级id")
    private Long classId;

    /**
     * heroland_user_class
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     * @return user_id 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 课程id
     * @return class_code 课程id
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * 课程id
     * @param classCode 课程id
     */
    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }

    /**
     * x学校
     * @return org_code x学校
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * x学校
     * @param orgCode x学校
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 班级id
     * @return class_id 班级id
     */
    public Long getClassId() {
        return classId;
    }

    /**
     * 班级id
     * @param classId 班级id
     */
    public void setClassId(Long classId) {
        this.classId = classId;
    }
}