package com.heroland.competition.dal.pojo.basic;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;

@Data
public class HerolandLocation extends BaseDO implements Serializable {

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