package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.HerolandTopicGroupPart")
public class HerolandTopicGroupPartDP extends BaseDO implements Serializable {
    /**
     * heroland_topic_group表的id
     */
    @ApiModelProperty(value="topicIdheroland_topic_group表的id")
    private Long topicId;

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
     * heroland_topic_group_part
     */
    private static final long serialVersionUID = 1L;

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
}