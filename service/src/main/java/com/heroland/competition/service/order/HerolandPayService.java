package com.heroland.competition.service.order;

import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.domain.dto.PrePayDto;
import com.heroland.competition.domain.qo.PrePayQO;

import java.util.Date;
import java.util.List;

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


    HerolandPayDP getPayById(Long id);

    PrePayDto prePay(PrePayQO prePayQO);

    List<HerolandPayDP> getPayByExpireTimeAndState(Date time, List<String> states);

    List<HerolandPayDP> getOrderByState(Date time, List<String> states);

    void updatePayState(String state, List<Long> payId);


}
