package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandClass")
public class HeroLandClass extends BaseDO implements Serializable {
    /**
     * 年级
     */
    @ApiModelProperty(value="gradeCode年级")
    private String gradeCode;

    /**
     * 可容纳人数
     */
    @ApiModelProperty(value="capacity可容纳人数")
    private Long capacity;

    /**
     * 班级code
     */
    @ApiModelProperty(value="classCode班级code")
    private String classCode;

    /**
     * 学校code
     */
    @ApiModelProperty(value="orgCode学校code")
    private String orgCode;

    /**
     * 班级名称
     */
    @ApiModelProperty(value="className班级名称")
    private String className;

    /**
     * heroland_class
     */
    private static final long serialVersionUID = 1L;

    /**
     * 年级
     * @return grade_code 年级
     */
    public String getGradeCode() {
        return gradeCode;
    }

    /**
     * 年级
     * @param gradeCode 年级
     */
    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode == null ? null : gradeCode.trim();
    }

    /**
     * 可容纳人数
     * @return capacity 可容纳人数
     */
    public Long getCapacity() {
        return capacity;
    }

    /**
     * 可容纳人数
     * @param capacity 可容纳人数
     */
    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    /**
     * 班级code
     * @return class_code 班级code
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * 班级code
     * @param classCode 班级code
     */
    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }

    /**
     * 学校code
     * @return org_code 学校code
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 学校code
     * @param orgCode 学校code
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 班级名称
     * @return class_name 班级名称
     */
    public String getClassName() {
        return className;
    }

    /**
     * 班级名称
     * @param className 班级名称
     */
    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }
}