package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.heroland.competition.common.constants.OrderStateEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.IDGenerateUtils;
import com.heroland.competition.common.utils.NumberUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class HerolandOrderDP extends BaseDO implements Serializable {

    private String bizNo;

    private String state;

    private Byte isVisible;

    private String buyerId;

    private String buyerName;

    private String spuId;

    private String spuName;

    private String skuId;

    private Long skuPrice;

    private Integer skuNum;

    private String skuName;

    private Long origPrice;

    private Long currencyAmt;

    private String currencyType;

    private Date paidTime;

    private String closeReason;

    private Date closeTime;

    private String bizExt;

    private String skuCurrencyType;


    public HerolandOrderDP checkAndBuildBeforeCreate(){
        AssertUtils.notBlank(buyerId);
        AssertUtils.notBlank(skuId);
        AssertUtils.notBlank(skuCurrencyType);
        AssertUtils.assertThat(!NumberUtils.nullOrZero(skuNum));
        AssertUtils.assertThat(!NumberUtils.nullOrZero(skuPrice));
        this.origPrice = this.skuPrice * this.skuNum;
        //暂时没有优惠
        this.currencyAmt = this.origPrice;
        this.currencyType = skuCurrencyType;
        this.state = OrderStateEnum.CREATED.getCode();
        this.bizNo = IDGenerateUtils.getOrderBizNo();
        return this;
    }
}