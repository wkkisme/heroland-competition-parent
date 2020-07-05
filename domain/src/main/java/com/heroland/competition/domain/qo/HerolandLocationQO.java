package com.heroland.competition.domain.qo;

import lombok.Data;

/**
 * @author smjyouzan
 * @date 2020/7/4
 */
@Data
public class HerolandLocationQO {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 地区
     */
    private String area;

    /**
     * 学校
     */
    private String school;

    /**
     * 年级
     */
    private String grade;

    /**
     * 班级
     */
    private String clas;
}
