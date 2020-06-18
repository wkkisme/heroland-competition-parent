package com.heroland.competition.domain.qo;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.common.BaseQO;
import com.heroland.competition.dal.pojo.HeroLandQuestionDP;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value="com.heroland.competition.domain.qo.HeroLandTopicGroupQO")
public class HeroLandTopicGroupQO extends BaseQO implements Serializable {
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
     * 类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 
     */
    @ApiModelProperty(value="type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 ")
    private Integer type;

    /**
     * 题目组id
     */
    @ApiModelProperty(value="topicId题目组id")
    private String topicId;

    @ApiModelProperty(value="questions题目")
    private List<HeroLandQuestionDP> questions;


    public List<HeroLandQuestionDP> getQuestions() {
        return questions;
    }

    public void setQuestions(List<HeroLandQuestionDP> questions) {
        this.questions = questions;
    }

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
     * 题目组id
     * @return topic_id 题目组id
     */
    public String getTopicId() {
        return topicId;
    }

    /**
     * 题目组id
     * @param topicId 题目组id
     */
    public void setTopicId(String topicId) {
        this.topicId = topicId == null ? null : topicId.trim();
    }
}