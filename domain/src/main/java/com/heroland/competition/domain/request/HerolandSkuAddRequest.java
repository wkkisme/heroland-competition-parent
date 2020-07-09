package com.heroland.competition.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class HerolandSkuAddRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * spuId
     */
    private String spuId;

    /**
     * 单价
     */
    private Long skuPrice;

    /**
     * 单位
     */
    private Integer unit;

    /**
     * 名称
     */
    private String skuName;

    /**
     * 货币类型
     */
    private String currencyType;
}