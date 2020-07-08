package com.heroland.competition.service.order;

import com.heroland.competition.domain.dp.HerolandOrderDP;

import java.util.List;

/**
 * @author
 */
public interface HerolandOrderService {

    /**
     * 创建待付款订单
     */
    HerolandOrderDP createOrder(HerolandOrderDP herolandOrder);

    Long createPayOrder(HerolandOrderDP herolandOrder);

    /**
     * 支付回调
     * @param orderId
     * @param orderState
     * @return
     */
    HerolandOrderDP payOrderCallBack(long orderId, int orderState);

    /**
     * 获取用户的订单列表
     * @param userId
     * @param status
     * @return
     */
    List<HerolandOrderDP> listOrder(String userId, List<String> status);

    /**
     * 关闭订单
     * @param orderId
     * @param userId
     */
    void closeOrder(Long orderId, String userId);

}
