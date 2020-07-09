package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.contants.PayCurrencyTypeEnum;
import com.heroland.competition.common.contants.SPUEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.NumberUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HerolandSkuDP extends BaseDO implements Serializable {

    /**
     * 宝石类型
     */
    private String spuId;

    /**
     * 宝石类型名称  -- 此次直接定义为钻石
     */
    private String spuName;

    /**
     * 宝石规格id
     */
    private String skuId;

    /**
     * 是否可见
     * 默认可见
     */
    private Byte isVisible;

    /**
     * 金额
     */
    private Long skuPrice;

    /**
     * 单位
     * 10 20 ...
     */
    private Integer unit;

    /**
     * 名称
     */
    private String skuName;

    /**
     * 金额汇率
     */
    private String currencyType;


    public HerolandSkuDP checkAndBuildBeforeCreate() {
        AssertUtils.notBlank(spuId);
        AssertUtils.notBlank(skuName);
        AssertUtils.notBlank(currencyType);
        AssertUtils.assertThat((!NumberUtils.nullOrZero(unit) && unit > 0));
        AssertUtils.assertThat((!NumberUtils.nullOrZeroLong(skuPrice) && skuPrice > 0));

        SPUEnum spuEnum = SPUEnum.valueOfName(spuId);
        if (spuEnum == null){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_DIMOND.getErrorMessage());
        }
        PayCurrencyTypeEnum byType = PayCurrencyTypeEnum.findByType(currencyType);
        if (byType == null){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_CURRENCY.getErrorMessage());
        }
        //diamond_unit_10
        this.skuId = spuId+"_unit_"+unit;
        return this;
    }
}