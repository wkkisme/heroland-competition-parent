package com.heroland.competition.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smjyouzan
 * @date 2020/7/8
 */
@Data
public class HerolandOrderDto implements Serializable {


    private String buyerId;

    private String buyerName;

    private String spuId;

    private String spuName;

    private String skuId;

    private String skuName;

    private Integer skuNum;

}
