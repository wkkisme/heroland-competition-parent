package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

@Data
public class HerolandCourse extends BaseDO {


    private String grade;

    private Integer gradeSlice;

    private String course;

    private String edition;

    private String subType;
}