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

    private String spuId;

    private String spuName;

    private String skuId;

    private Byte isVisible;

    private Long skuPrice;

    private Integer unit;

    private String skuName;

    private String currencyType;

    private Boolean isDeleted;

    private String creator;

    private String modifier;

    private Date gmtCreate;

    private Date gmtModified;

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