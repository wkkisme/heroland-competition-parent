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
public class HerolandSchoolDto implements Serializable {

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
     * 孩子节点
     */
    private List<HerolandSchoolDto> child = Lists.newArrayList();
}
