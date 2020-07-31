package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.common.BaseQO;
import com.heroland.competition.common.utils.AssertUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandUserClass")
public class HeroLandUserClassQO extends BaseQO implements Serializable {
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
     * 年级code
     */
    @ApiModelProperty(value="gradeCode年级code")
    private String gradeCode;

    /**
     * 用户类型
     */
    @ApiModelProperty(value="userType用户类型")
    private Integer userType;


    /**
     * 是否班主任
     */
    @ApiModelProperty(value="headTeacher是否班主任")
    private Boolean headTeacher;

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
     * 年级code
     * @return grade_code 年级code
     */
    public String getGradeCode() {
        return gradeCode;
    }

    /**
     * 年级code
     * @param gradeCode 年级code
     */
    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode == null ? null : gradeCode.trim();
    }

    /**
     * 用户类型
     * @return user_type 用户类型
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 用户类型
     * @param userType 用户类型
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 是否班主任
     * @return head_teacher 是否班主任
     */
    public Boolean getHeadTeacher() {
        return headTeacher;
    }

    /**
     * 是否班主任
     * @param headTeacher 是否班主任
     */
    public void setHeadTeacher(Boolean headTeacher) {
        this.headTeacher = headTeacher;
    }
}