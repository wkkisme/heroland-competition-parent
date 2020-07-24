package com.heroland.competition.service.diamond;

import com.heroland.competition.domain.dp.HerolandSkuDP;
import com.heroland.competition.domain.dto.HerolandDiamondStockDto;
import com.heroland.competition.domain.qo.HerolandSkuQO;
import com.heroland.competition.domain.request.HerolandDiamMonthRecordRequest;
import com.heroland.competition.domain.request.HerolandDiamRequest;

import java.util.List;

/**
 * @author
 *
 */
public interface HerolandDiamondService {

    /**
     * 创建待付款订单
     */
    void createDiamondSku(HerolandSkuDP herolandSkuDP);

    /**
     * 更新
     * @param herolandPay
     */
    void updateDiamondSku(HerolandSkuDP herolandPay);


    HerolandSkuDP getById(Long id);


    int deleteById(Long id);

    List<HerolandSkuDP> listSku(HerolandSkuQO skuQO);


    Boolean createDiamondRecord(HerolandDiamRequest request);

    HerolandDiamondStockDto recordList(HerolandDiamMonthRecordRequest request);
}
