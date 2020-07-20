package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import lombok.Data;


@Data
public class HerolandQuestionBankDetail extends BaseDO {

    private Long qbId;

    private String option;

    private String optionAnswer;

    private String answer;

    private String parse;
}