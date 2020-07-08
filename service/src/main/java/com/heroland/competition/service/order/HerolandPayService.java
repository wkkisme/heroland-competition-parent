package com.heroland.competition.service.order;

import com.heroland.competition.domain.dp.HerolandPayDP;

/**
 * @author
 *
 */
public interface HerolandPayService {

    /**
     * 创建待付款订单
     */
    Long createPay(HerolandPayDP herolandPay);

    /**
     * 更新
     * @param herolandPay
     */
    void updatePay(HerolandPayDP herolandPay);
}
