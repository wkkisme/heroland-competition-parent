package com.heroland.competition.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum OrderByEnum {

    /**
     * 总分数
     */
    TOTAL_SCORE_DESC("total_score","total_score desc ", "desc","总分数倒序"),
    TOTAL_SCORE_ASC("total_score","total_score asc ","asc", "总分数正序"),
    COMPLETE_RATE_DESC("complete_rate","complete_rate desc ","desc", "完成率倒序"),
    COMPLETE_RATE_ASC("complete_rate","complete_rate asc ", "asc","完成率正序"),
    ANSWERRIGHT_RATE_DESC("answer_right_rate","answer_right_rate desc ", "desc","答对率倒序"),
    ANSWERRIGHT_RATE_ASC("answer_right_rate","answer_right_rate asc ","asc", "答对率正序"),
    WIN_RATE_DESC("win_rate","win_rate desc ","desc", "胜率倒序"),
    WIN_RATE_ASC("win_rate","win_rate asc ", "asc","胜率正序"),
    AVERAGE_SCORE_DESC("average_score","average_score desc ", "desc","平均分倒序"),
    AVERAGES_CORE_ASC("average_score","average_score asc ", "asc","平均分正序");


    private final String filed;

    private final String orderByFiled;

    private final  String orderType;

    private final String filedName;

    OrderByEnum( String filed, String orderByFiled,  String orderType,String filedName) {
        this.orderByFiled = orderByFiled;
        this.filed = filed;
        this.filedName = filedName;
        this.orderType = orderType;
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

    public String getOrderType() {
        return orderType;
    }

    public static  String getOrderType(String orderByFiled){

        if (StringUtils.isBlank(orderByFiled)){
            return "asc";
        }
        for (OrderByEnum value : OrderByEnum.values()) {
            if (value.orderByFiled.equals(orderByFiled)){
                return value.orderType;
            }
        }
        return "asc";
    }
}
