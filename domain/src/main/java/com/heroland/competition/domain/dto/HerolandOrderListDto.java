package com.heroland.competition.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HerolandOrderListDto  implements Serializable {

    /**
     * id
     */
    private  Long id;

    /**
     * 订单号
     */
    private String bizNo;

    /**
     *状态
     */
    private String state;

    /**
     * 用户
     */
    private String buyerId;

    /**
     * 宝石id
     */
    private String skuId;

    /**
     * skuId的购买个数
     * 目前一次性只能买1个这个商品
     */
    private Integer skuNum;

    /**
     * 当前sku里包含几个宝石
     */
    private Integer skuUnit;

    /**
     * 宝石名称
     */
    private String skuName;

    /**
     * 付款价格
     */
    private Long currencyAmt;

    /**
     * 付款类型
     */
    private String currencyType;

    /**
     * 付款时间
     */
    private Date paidTime;

    /**
     * 关单时间
     */
    private Date closeTime;

    /**
     * 订单产生时间
     */
    private Date orderCreateTime;

}