package com.heroland.competition.domain.qo;

import com.anycommon.response.annotation.MybatisCriteriaAnnotation;
import com.anycommon.response.common.BaseQO;
import com.heroland.competition.common.enums.GroupByEnum;
import com.heroland.competition.common.enums.OrderByEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ApiModel(value="com.heroland.competition.dal.pojo.HeroLandStatisticsTotal")
@Data
public class HeroLandStatisticsAllQO extends BaseQO implements Serializable {
    /**
     * 学校机构code 必传
     */
    private String orgCode;
    /**
     * 用户名称

     */
    @ApiModelProperty(value="userName用户名称")
    private String userName;

    /**
     * userid
     */
    @ApiModelProperty(value="userIduserid 查询详情时必填")
    private String userId;

    /**
     * 
     */
    @ApiModelProperty(value="gradeCode年级code")
    private String gradeCode;

    /**
     * 
     */
    @ApiModelProperty(value="gradeName年级名称")
    private String gradeName;

    /**
     * 
     */
    @ApiModelProperty(value="classCode班级code")
    private String classCode;

    /**
     * 
     */
    @ApiModelProperty(value="className班级名称")
    private String className;


    /**
     * "type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 "
     */
    private Integer type;

    /**
     * 0不是，1是
     */
    @MybatisCriteriaAnnotation
    private Boolean history;

    private String subjectCode;


    private Boolean groupBySubjectCode;
    private Boolean groupByType;
    private Boolean groupByClassCode;
    private Boolean groupByGradeCode;
    private Boolean groupByUserId;
    private Boolean groupByOrgCode;
    private Boolean groupByInviteId;
    private Boolean groupByOpponentId;

    /**
     *  0 邀请方胜利。1 被邀请方胜利。2 平局
     */
    private Integer resultOpponent;
    private Integer resultInvite;

    private Boolean ifCorrectAnswer;

    private List<Long> topicIds;


    private Map<String,String> topic2Subject;
    private Map<String,List<String>> subject2Topic;

    private Map<String,String> topic2OrgCode;

    public Map<String, String> getTopic2OrgCode() {
        return topic2OrgCode;
    }

    public void setTopic2OrgCode(Map<String, String> topic2OrgCode) {
        this.topic2OrgCode = topic2OrgCode;
    }

    public Map<String, List<String>> getSubject2Topic() {
        return subject2Topic;
    }

    public void setSubject2Topic(Map<String, List<String>> subject2Topic) {
        this.subject2Topic = subject2Topic;
    }

    public Map<String, String> getTopic2Subject() {
        return topic2Subject;
    }

    public void setTopic2Subject(Map<String, String> topic2Subject) {
        this.topic2Subject = topic2Subject;
    }


    public Boolean getIfCorrectAnswer() {
        return ifCorrectAnswer;
    }

    public void setIfCorrectAnswer(Boolean ifCorrectAnswer) {
        this.ifCorrectAnswer = ifCorrectAnswer;
    }

    public Integer getResultOpponent() {
        return resultOpponent;
    }

    public void setResultOpponent(Integer resultOpponent) {
        this.resultOpponent = resultOpponent;
    }

    public Integer getResultInvite() {
        return resultInvite;
    }

    public void setResultInvite(Integer resultInvite) {
        this.resultInvite = resultInvite;
    }

    public Boolean getGroupBySubjectCode() {
        return groupBySubjectCode;
    }

    public void setGroupBySubjectCode(Boolean groupBySubjectCode) {
        this.groupBySubjectCode = groupBySubjectCode;
    }

    public Boolean getGroupByType() {
        return groupByType;
    }

    public void setGroupByType(Boolean groupByType) {
        this.groupByType = groupByType;
    }

    public Boolean getGroupByClassCode() {
        return groupByClassCode;
    }

    public void setGroupByClassCode(Boolean groupByClassCode) {
        this.groupByClassCode = groupByClassCode;
    }

    public Boolean getGroupByGradeCode() {
        return groupByGradeCode;
    }

    public void setGroupByGradeCode(Boolean groupByGradeCode) {
        this.groupByGradeCode = groupByGradeCode;
    }

    public Boolean getGroupByUserId() {
        return groupByUserId;
    }

    public void setGroupByUserId(Boolean groupByUserId) {
        this.groupByUserId = groupByUserId;
    }

    public Boolean getGroupByOrgCode() {
        return groupByOrgCode;
    }

    public void setGroupByOrgCode(Boolean groupByOrgCode) {
        this.groupByOrgCode = groupByOrgCode;
    }

    public Boolean getGroupByInviteId() {
        return groupByInviteId;
    }

    public void setGroupByInviteId(Boolean groupByInviteId) {
        this.groupByInviteId = groupByInviteId;
    }

    public Boolean getGroupByOpponentId() {
        return groupByOpponentId;
    }

    public void setGroupByOpponentId(Boolean groupByOpponentId) {
        this.groupByOpponentId = groupByOpponentId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }



    public Boolean getHistory() {
        return history;
    }

    public void setHistory(Boolean history) {
        this.history = history;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }


    /**
     * 用户名称

     * @return user_name 用户名称

     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名称

     * @param userName 用户名称

     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * userid
     * @return user_id userid
     */
    public String getUserId() {
        return userId;
    }

    /**
     * userid
     * @param userId userid
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 
     * @return grade_code 
     */
    public String getGradeCode() {
        return gradeCode;
    }

    /**
     * 
     * @param gradeCode 
     */
    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode == null ? null : gradeCode.trim();
    }

    /**
     * 
     * @return grade_name 
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * 
     * @param gradeName 
     */
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName == null ? null : gradeName.trim();
    }

    /**
     * 
     * @return class_code 
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * 
     * @param classCode 
     */
    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }

    /**
     * 
     * @return class_name 
     */
    public String getClassName() {
        return className;
    }

    /**
     * 
     * @param className 
     */
    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

}