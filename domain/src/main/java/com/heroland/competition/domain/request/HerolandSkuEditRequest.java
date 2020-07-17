package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class HerolandSkuEditRequest implements Serializable {

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * spuId
     */
    @NotNull
    private String spuId;

    /**
     * 单价
     */
    @NotNull
    private Long skuPrice;

    /**
     * 单位
     */
    @NotNull
    private Integer unit;

    /**
     * 货币类型
     */
    @NotNull
    private String currencyType;
}