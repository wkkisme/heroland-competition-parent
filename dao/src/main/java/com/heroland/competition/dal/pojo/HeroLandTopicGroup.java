package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandTopicGroup")
public class HeroLandTopicGroup extends BaseDO implements Serializable {
    /**
     * 机构code
     */
    @ApiModelProperty(value="orgCode机构code")
    private String orgCode;

    /**
     * 题目组名称
     */
    @ApiModelProperty(value="topicName题目组名称")
    private String topicName;

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
     * 类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 
     */
    @ApiModelProperty(value="type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 ")
    private Integer type;

    /**
     * 比赛开始时间
     */
    @ApiModelProperty(value="startTime比赛开始时间")
    private Date startTime;

    /**
     * 比赛结束时间
     */
    @ApiModelProperty(value="endTime比赛结束时间")
    private Date endTime;

    /**
     * 题组难度
     */
    @ApiModelProperty(value="levelCode题组难度")
    private String levelCode;

    /**
     * 题型
     */
    @ApiModelProperty(value="题型")
    private Integer diff;

    /**
     * heroland_topic_group
     */
    private static final long serialVersionUID = 1L;

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
     * 题目组名称
     * @return topic_name 题目组名称
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * 题目组名称
     * @param topicName 题目组名称
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName == null ? null : topicName.trim();
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
     * 类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 
     * @return type 类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 
     */
    public Integer getType() {
        return type;
    }

    /**
     * 类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 
     * @param type 类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 比赛开始时间
     * @return start_time 比赛开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 比赛开始时间
     * @param startTime 比赛开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 比赛结束时间
     * @return end_time 比赛结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 比赛结束时间
     * @param endTime 比赛结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 题组难度
     * @return level_code 题组难度
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * 题组难度
     * @param levelCode 题组难度
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode == null ? null : levelCode.trim();
    }

    /**
     * 题型
     * @return diff 题型
     */
    public Integer getDiff() {
        return diff;
    }

    /**
     * 题型
     * @param diff 题型
     */
    public void setDiff(Integer diff) {
        this.diff = diff;
    }
}