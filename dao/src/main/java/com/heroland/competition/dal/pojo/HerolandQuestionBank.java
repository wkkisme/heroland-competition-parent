package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

@Data
public class HerolandQuestionBank extends BaseDO {

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