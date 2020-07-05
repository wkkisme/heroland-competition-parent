package com.heroland.competition.domain.qo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/5
 */
@Data
public class HeroLandClassQO implements Serializable {

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

    /**
     * 后台管理类型
     */
    private String code;

    /**
     * 后台管理值域
     */
    private String field;

    /**
     * 字典数据key
     */
    private String dictKey;

    /**
     * 字典数据名称
     */
    private String dictValue;
}
