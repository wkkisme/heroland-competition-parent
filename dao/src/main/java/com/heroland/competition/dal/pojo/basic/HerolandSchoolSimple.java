package com.heroland.competition.dal.pojo.basic;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
public class HerolandSchoolSimple implements Serializable {

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
     * 业务编号，例如学校编号
     */
    private String bizNo;

    /**
     * 详细的节点name
     */
    private String name;
}
