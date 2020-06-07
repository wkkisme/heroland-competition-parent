package com.heroland.competition.dal.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.PlatformSysUser")
public class PlatformSysUser extends BaseDO implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value="userId用户id")
    private String userId;

    /**
     * 用户名唯一
     */
    @ApiModelProperty(value="userName用户名唯一")
    private String userName;

    /**
     * 英文名称
     */
    @ApiModelProperty(value="enName英文名称")
    private String enName;

    /**
     * 中文名称
     */
    @ApiModelProperty(value="chName中文名称")
    private String chName;

    /**
     * 学校名称
     */
    @ApiModelProperty(value="schoolName学校名称")
    private String schoolName;

    /**
     * 年级_编码
     */
    @ApiModelProperty(value="gradeCode年级_编码")
    private String gradeCode;

    /**
     * 学生证url
     */
    @ApiModelProperty(value="stuCard学生证url")
    private String stuCard;

    /**
     * 密码
     */
    @ApiModelProperty(value="password密码")
    private String password;

    /**
     * 性别
     */
    @ApiModelProperty(value="sex性别")
    private Boolean sex;

    /**
     * 账户类型 0 学生 1 家长 2 教师
     */
    @ApiModelProperty(value="type账户类型 0 学生 1 家长 2 教师")
    private Integer type;

    /**
     * 班级编码
     */
    @ApiModelProperty(value="classCode班级编码")
    private String classCode;

    /**
     * 座位号
     */
    @ApiModelProperty(value="seatNum座位号")
    private String seatNum;

    /**
     * 与孩子关系
     */
    @ApiModelProperty(value="childRelation与孩子关系")
    private String childRelation;

    /**
     * 孩子的用户名
     */
    @ApiModelProperty(value="childUserName孩子的用户名")
    private String childUserName;

    /**
     * 学校组织code
     */
    @ApiModelProperty(value="orgCode学校组织code")
    private String orgCode;

    /**
     * platform_sys_user
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
     * 用户名唯一
     * @return user_name 用户名唯一
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名唯一
     * @param userName 用户名唯一
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 英文名称
     * @return en_name 英文名称
     */
    public String getEnName() {
        return enName;
    }

    /**
     * 英文名称
     * @param enName 英文名称
     */
    public void setEnName(String enName) {
        this.enName = enName == null ? null : enName.trim();
    }

    /**
     * 中文名称
     * @return ch_name 中文名称
     */
    public String getChName() {
        return chName;
    }

    /**
     * 中文名称
     * @param chName 中文名称
     */
    public void setChName(String chName) {
        this.chName = chName == null ? null : chName.trim();
    }

    /**
     * 学校名称
     * @return school_name 学校名称
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * 学校名称
     * @param schoolName 学校名称
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    /**
     * 年级_编码
     * @return grade_code 年级_编码
     */
    public String getGradeCode() {
        return gradeCode;
    }

    /**
     * 年级_编码
     * @param gradeCode 年级_编码
     */
    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode == null ? null : gradeCode.trim();
    }

    /**
     * 学生证url
     * @return stu_card 学生证url
     */
    public String getStuCard() {
        return stuCard;
    }

    /**
     * 学生证url
     * @param stuCard 学生证url
     */
    public void setStuCard(String stuCard) {
        this.stuCard = stuCard == null ? null : stuCard.trim();
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 性别
     * @return sex 性别
     */
    public Boolean getSex() {
        return sex;
    }

    /**
     * 性别
     * @param sex 性别
     */
    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    /**
     * 账户类型 0 学生 1 家长 2 教师
     * @return type 账户类型 0 学生 1 家长 2 教师
     */
    public Integer getType() {
        return type;
    }

    /**
     * 账户类型 0 学生 1 家长 2 教师
     * @param type 账户类型 0 学生 1 家长 2 教师
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 班级编码
     * @return class_code 班级编码
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * 班级编码
     * @param classCode 班级编码
     */
    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }

    /**
     * 座位号
     * @return seat_num 座位号
     */
    public String getSeatNum() {
        return seatNum;
    }

    /**
     * 座位号
     * @param seatNum 座位号
     */
    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum == null ? null : seatNum.trim();
    }

    /**
     * 与孩子关系
     * @return child_relation 与孩子关系
     */
    public String getChildRelation() {
        return childRelation;
    }

    /**
     * 与孩子关系
     * @param childRelation 与孩子关系
     */
    public void setChildRelation(String childRelation) {
        this.childRelation = childRelation == null ? null : childRelation.trim();
    }

    /**
     * 孩子的用户名
     * @return child_user_name 孩子的用户名
     */
    public String getChildUserName() {
        return childUserName;
    }

    /**
     * 孩子的用户名
     * @param childUserName 孩子的用户名
     */
    public void setChildUserName(String childUserName) {
        this.childUserName = childUserName == null ? null : childUserName.trim();
    }

    /**
     * 学校组织code
     * @return org_code 学校组织code
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 学校组织code
     * @param orgCode 学校组织code
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }
}