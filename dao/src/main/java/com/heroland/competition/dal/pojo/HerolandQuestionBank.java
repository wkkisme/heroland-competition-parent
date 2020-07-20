package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import lombok.Data;


@Data
public class HerolandQuestionBank extends BaseDO {

    private String gradeCode;

    private String course;

    private Integer type;

    private Integer subType;

    private Integer diff;

    private String year;

    private String area;

    private String source;

    private Integer paperTpye;

    private String title;

}