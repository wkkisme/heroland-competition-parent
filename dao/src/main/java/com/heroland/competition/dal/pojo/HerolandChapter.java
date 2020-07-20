package com.heroland.competition.dal.pojo;


import com.anycommon.response.common.BaseDO;
import lombok.Data;


@Data
public class HerolandChapter extends BaseDO {

    private String grade;

    private Integer gradeUnit;

    private String course;

    private String edition;

    private String editionType;

    private Integer contentType;

    private String content;

    private Integer order;

    private Long parentId;
}