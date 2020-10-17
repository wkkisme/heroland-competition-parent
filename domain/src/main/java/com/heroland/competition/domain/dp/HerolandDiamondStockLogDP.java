package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constants.DiamBizGroupEnum;
import com.heroland.competition.common.constants.DiamBizTypeEnum;
import com.heroland.competition.common.constants.StockEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.NumberUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class HerolandDiamondStockLogDP extends BaseDO {

    private String userId;

    private Integer type;

    private Integer num;

    private String bizGroup;

    private String bizType;

    public HerolandDiamondStockLogDP checkAndBuildBeforeCreate(){
        AssertUtils.assertThat(!NumberUtils.nullOrZero(type), HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        AssertUtils.assertThat(!NumberUtils.nullOrZero(num),HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        AssertUtils.notBlank(userId,HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        StockEnum stockEnum = StockEnum.valueOfLevel(type);
        if (stockEnum == null){
            ResponseBodyWrapper.failException("宝石使用类型错误");
        }
        AssertUtils.assertThat(StringUtils.isNotBlank(bizGroup), HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        AssertUtils.assertThat(StringUtils.isNotBlank(bizType),HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());

//        DiamBizGroupEnum diamBizGroupEnum = DiamBizGroupEnum.valueOfLevel(bizGroup);
//        if (diamBizGroupEnum == null){
//            ResponseBodyWrapper.failException("宝石使用分组不存在，请先注册");
//        }
//        DiamBizTypeEnum diamBizTypeEnum = DiamBizTypeEnum.valueOfV(bizType);
//        if (diamBizTypeEnum == null){
//            ResponseBodyWrapper.failException("宝石使用场景不存在，请先注册");
//        }
//        List<DiamBizTypeEnum> list = DiamBizTypeEnum.valueOfGroup(bizGroup);
//        boolean contains = list.stream().map(DiamBizTypeEnum::getValue).collect(Collectors.toList()).contains(bizType);
//        if (!contains){
//            ResponseBodyWrapper.failException("宝石使用场景不存在，请先注册");
//        }
        return this;
    }

}