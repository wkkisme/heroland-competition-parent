package com.heroland.competition.service.order;

import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HerolandOrderDP;
import com.heroland.competition.domain.dto.HerolandOrderListDto;
import com.heroland.competition.domain.qo.HerolandOrderQueryQO;
import com.heroland.competition.domain.qo.PayOrderQO;

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
     * @return
     */
    HerolandOrderDP payOrderCallBack(PayOrderQO qo);

    /**
     * 获取用户的订单列表

     * @return
     */
    PageResponse<HerolandOrderListDto> listOrder(HerolandOrderQueryQO qo);

    /**
     * 关闭订单
     * @param orderId
     * @param userId
     */
    void closeOrder(Long orderId, String userId);

}
