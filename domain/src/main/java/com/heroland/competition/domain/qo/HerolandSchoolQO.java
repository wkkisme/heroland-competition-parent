package com.heroland.competition.domain.qo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
public class HerolandSchoolQO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 字典编号，地区|学校|年级|班级
     */
    private String code;

    /**
     * 详细的节点key
     */
    private String key;
}
