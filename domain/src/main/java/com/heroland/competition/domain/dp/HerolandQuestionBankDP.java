package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

@Data
public class HerolandQuestionBankDP extends BaseDO {

    private String gradeCode;

    private String course;

    private String qbId;

    private String title;

    private String type;

    private String subType;

    private String diff;

    private String year;

    private String area;

    private String source;
}