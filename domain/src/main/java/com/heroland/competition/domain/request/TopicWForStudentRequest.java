package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/8/17
 */
@Data
public class TopicWForStudentRequest implements Serializable {

    /**
     * userId
     */
    @NotNull
    private String userId;

    /**
     * 科目
     */
    private String courseCode;

    /**
     * 查询是报名还是比赛
     * action = REGISTER 表示推荐报名的比赛
     * action = BATTLE 表示弹出已经报过名的比赛
     * REGISTER
     * BATTLE
     */
    private String action;


}
