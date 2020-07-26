package com.heroland.competition.common.enums;

/**
 * group 字段
 * @author mac
 */

public enum GroupByEnum {

    /**
     * 总分数
     */
    org_code("org_code", "学校"),
    class_code("class_code", "班级"),
    grade_code("grade_code", "年级"),
    invite_id("invite_id", "邀请者id"),
    opponent_id("opponent_id", "被邀请者id"),
    user_id("user_id", "用户id"),
    topic_type("topic_type", "比赛类型"),
    subject_code("win_rate", "科目");


    private final String filed;


    private final String filedName;

    GroupByEnum(String filed, String filedName) {
        this.filed = filed;
        this.filedName = filedName;
    }

    public String getFiled() {
        return filed;
    }

    public String getFiledName() {
        return filedName;
    }

}
