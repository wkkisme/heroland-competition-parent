package com.heroland.competition.domain.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
public class HerolandSchoolSimpleDto implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 字典编号，地区|学校|年级|班级
     */
    private String code;

    /**
     * 详细的节点key，数据库唯一表示key
     */
    private String key;

    /**
     * 某一节点的业务no，比如香港的学校有自己的编号
     */
    private String bizNo;

    /**
     * 业务的国际化表示，比如学校就是有自己的英文名称
     */
    private String bizI18N;

    /**
     * 详细的节点name
     */
    private String name;

    /**
     * 联系人
     */
    private String linkedMan;

    /**
     * 电话
     */
    private String mobile;

    /**
     * email
     */
    private String email;

    /**
     * 地理坐标
     */
    private String axis;

    /**
     * 描述 简介
     */
    private String desc;

    /**
     * 学校节点下的地区名称
     */
    private String areaName;

    /**
     *  学校节点下的地区key
     */
    private String areaKey;

    /**
     * 父节点
     */
    private String parentKey;


    /**
     * 默认值，例如班级的基本容量
     */
    private Integer defaultValue;

    /**
     * 班级的当前人数
     */
    private Integer hadCapacity;

    /**
     * 子节点数据
     */
    private List<HerolandSchoolSimpleDto> subNodeSchoolSimpleDto = Lists.newArrayList();

}
