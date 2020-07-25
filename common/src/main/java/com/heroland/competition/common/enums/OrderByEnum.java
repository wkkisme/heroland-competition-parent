package com.heroland.competition.common.enums;

public enum OrderByEnum {

    /**
     * 总分数
     */
    TOTAL_SCORE_DESC("total_score","total_score desc ", "总分数倒序"),
    TOTAL_SCORE_ASC("total_score","total_score asc ", "总分数正序"),
    COMPLETE_RATE_DESC("complete_rate","complete_rate desc ", "完成率倒序"),
    COMPLETE_RATE_ASC("complete_rate","completeRate asc ", "完成率正序"),
    ANSWERRIGHT_RATE_DESC("answer_right_rate","answer_right_rate desc ", "答对率倒序"),
    ANSWERRIGHT_RATE_ASC("answer_right_rate","answer_right_rate asc ", "答对率正序"),
    WIN_RATE_DESC("win_rate","win_rate desc ", "胜率倒序"),
    WIN_RATE_ASC("win_rate","win_rate asc ", "胜率正序"),
    AVERAGE_SCORE_DESC("average_score","average_score desc ", "平均分倒序"),
    AVERAGES_CORE_ASC("average_score","average_score asc ", "平均分正序");


    private final String filed;

    private final String orderByFiled;

    private final String filedName;

    OrderByEnum( String filed, String orderByFiled,String filedName) {
        this.orderByFiled = orderByFiled;
        this.filed = filed;
        this.filedName = filedName;
    }

    public String getFiled() {
        return filed;
    }

    public String getFiledName() {
        return filedName;
    }

    public String getOrderByFiled() {
        return orderByFiled;
    }
}
