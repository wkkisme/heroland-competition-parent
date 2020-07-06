package com.heroland.competition.domain.qo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/6
 */
@Data
public class HerolandLocationDataQO implements Serializable {

    /**
     * 字典编号 地区 学校 年级 班级
     */
    private String code;
    /**
     * 字典详情数据key
     */
    private String dictKey;

    /**
     * 字典详情数据名称
     * 该字段支持模糊查询
     */
    private String childDictName;
}
