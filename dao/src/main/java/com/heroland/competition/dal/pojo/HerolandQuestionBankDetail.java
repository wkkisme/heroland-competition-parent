package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;


@Data
public class HerolandQuestionBankDetail extends BaseDO {

    private Long qbId;

    private String option;

    private String optionAnswer;

    private String answer;

    private String parse;


    /**
     * 胡乱答案
     */
    private String stormAnswer;

    /**
     * 答案解析
     */
    private String analysis;

    /**
     * 示例
     */
    private String information;

    private String similarQt;


    /**
     * 相似题id
     * qtId
     */
    private String optionZ;

    /**
     * 不知道是啥
     * 可暂时不管
     */
    private String passage;
}