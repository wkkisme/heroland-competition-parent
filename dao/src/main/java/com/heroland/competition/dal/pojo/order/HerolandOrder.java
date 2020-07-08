package com.heroland.competition.dal.pojo.order;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HerolandOrder extends BaseDO implements Serializable {
    private Long id;

    private String bizNo;

    private String state;

    private Byte isVisible;

    private String buyerId;

    private String buyerName;

    private String spuId;

    private String spuName;

    private String skuId;

    private Integer skuNum;

    private String skuName;

    private Long skuPrice;

    private Long origPrice;

    private Long currencyAmt;

    private String currencyType;

    private Date paidTime;

    private String closeReason;

    private Date closeTime;

    private String bizExt;

    private Boolean isDeleted;

    private String creator;

    private String modifier;

    private Date gmtCreate;

    private Date gmtModified;
}