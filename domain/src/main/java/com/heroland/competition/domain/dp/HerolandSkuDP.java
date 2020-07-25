package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constants.AdminFieldEnum;
import com.heroland.competition.common.constants.PayCurrencyTypeEnum;
import com.heroland.competition.common.constants.SPUEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.IDGenerateUtils;
import com.heroland.competition.common.utils.NumberUtils;
import lombok.Data;

import java.io.Serializable;

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
    private Boolean isVisible = true;

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
//        AssertUtils.notBlank(skuName);
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
        this.skuName = unit +"颗";
        //diamond_unit_10
        this.skuId = IDGenerateUtils.getKey(AdminFieldEnum.DIAMOND);
        this.setIsDeleted(false);
        return this;
    }


    public HerolandSkuDP checkAndBuildBeforeEdit() {
        AssertUtils.notBlank(spuId);
        AssertUtils.notNull(this.getId());
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
        this.skuName = unit +"颗";
        //diamond_unit_10
        return this;
    }

}