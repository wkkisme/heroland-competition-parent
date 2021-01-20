package com.heroland.competition.dal.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value="com.heroland.competition.dal.pojo.HerolandTopicGroupPart")
public class HerolandTopicGroupPart implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="id主键")
    private Long id;

    /**
     * heroland_topic_group表的id
     */
    @ApiModelProperty(value="topicIdheroland_topic_group表的id")
    private Long topicId;

    /**
     * 赛事类型
     */
    @ApiModelProperty(value="topicType赛事类型")
    private Integer topicType;

    /**
     * 机构code
     */
    @ApiModelProperty(value="orgCode机构code")
    private String orgCode;

    /**
     * 年级code
     */
    @ApiModelProperty(value="gradeCode年级code")
    private String gradeCode;

    /**
     * 班级code
     */
    @ApiModelProperty(value="classCode班级code")
    private String classCode;

    /**
     * 科目code
     */
    @ApiModelProperty(value="courseCode科目code")
    private String courseCode;

    /**
     * 创建人
     */
    @ApiModelProperty(value="creator创建人")
    private String creator;

    /**
     * 修改人
     */
    @ApiModelProperty(value="modifier修改人")
    private String modifier;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="gmtCreate创建时间")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="gmtModified修改时间")
    private Date gmtModified;

    /**
     * 是否删除(0有效,1作废)
     */
    @ApiModelProperty(value="isDeleted是否删除(0有效,1作废)")
    private Boolean isDeleted;

    /**
     * heroland_topic_group_part
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * heroland_topic_group表的id
     * @return topic_id heroland_topic_group表的id
     */
    public Long getTopicId() {
        return topicId;
    }

    /**
     * heroland_topic_group表的id
     * @param topicId heroland_topic_group表的id
     */
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    /**
     * 赛事类型
     * @return topic_type 赛事类型
     */
    public Integer getTopicType() {
        return topicType;
    }

    /**
     * 赛事类型
     * @param topicType 赛事类型
     */
    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    /**
     * 机构code
     * @return org_code 机构code
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 机构code
     * @param orgCode 机构code
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
     * 科目code
     * @return course_code 科目code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * 科目code
     * @param courseCode 科目code
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode == null ? null : courseCode.trim();
    }

    /**
     * 创建人
     * @return creator 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建人
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 修改人
     * @return modifier 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 修改人
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 创建时间
     * @return gmt_create 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 创建时间
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 修改时间
     * @return gmt_modified 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 修改时间
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 是否删除(0有效,1作废)
     * @return is_deleted 是否删除(0有效,1作废)
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除(0有效,1作废)
     * @param isDeleted 是否删除(0有效,1作废)
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}