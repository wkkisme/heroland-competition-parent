package com.heroland.competition.dal.pojo;

import com.anycommon.response.common.BaseDO;
import lombok.Data;

import java.util.Date;

@Data
public class HerolandSku extends BaseDO {

    private String spuId;

    private String skuId;

    private Boolean isVisible = true;

    private Long skuPrice;

    private Integer unit;

    private String skuName;

    private String currencyType;

    private Boolean isDeleted;

    private String creator;

    private String modifier;

    private Date gmtCreate;

    private Date gmtModified;

}