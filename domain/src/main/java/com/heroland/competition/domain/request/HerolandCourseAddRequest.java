package com.heroland.competition.domain.request;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
public class HerolandCourseAddRequest implements Serializable {

    /**
     * 主键，更新时必填
     */
    private Long id;

    /**
     * 年级
     */
    @NotNull
    private String grade;

    /**
     * 年级中的上|下
     * 1 上，2 下
     */
    @NotNull
    private Integer unit;

    /**
     * 科目
     */
    @NotNull
    private String course;

    /**
     * 科目教材版本
     */
    @NotNull
    private String edition;

    /**
     * 在教材中的必修|选修
     * 如必修1，选修2-1等
     */
    private String editionType;


    private List<HerolandSchoolCourseRequest> schoolList = Lists.newArrayList();

}
