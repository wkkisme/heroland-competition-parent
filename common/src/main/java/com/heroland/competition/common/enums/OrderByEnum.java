package com.heroland.competition.common.enums;

public enum OrderByEnum {

    /**
     * 总分数
     */
    TOTAL_SCORE_DESC("totalScore desc ", "总分数倒序"),
    TOTAL_SCORE_ASC("totalScore asc ", "总分数正序"),
    COMPLETE_RATE_DESC("completeRate desc ", "完成率倒序"),
    COMPLETE_RATE_ASC("completeRate asc ", "完成率正序"),
    ANSWERRIGHT_RATE_DESC("answerRightRate desc ", "答对率倒序"),
    ANSWERRIGHT_RATE_ASC("answerRightRate asc ", "答对率正序"),
    WIN_RATE_DESC("winRate desc ", "胜率倒序"),
    WIN_RATE_ASC("winRate asc ", "胜率正序"),
    AVERAGE_SCORE_DESC("averageScore desc ", "平均分倒序"),
    AVERAGES_CORE_ASC("averageScore asc ", "平均分正序");

    private final String filed;

    private final String filedName;


    OrderByEnum(String filed, String filedName) {
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
