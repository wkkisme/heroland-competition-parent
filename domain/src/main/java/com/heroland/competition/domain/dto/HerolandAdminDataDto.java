package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
public class HerolandAdminDataDto implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 字典编号
     */
    private String code;

    /**
     * 详细的字典key，数据库唯一表示key
     */
    private String key;


    /**
     * 详细的字典name
     */
    private String name;

}
