package com.heroland.competition.service.impl.task;

import com.anycommon.response.expception.AppSystemException;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.OrderStateEnum;
import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.service.order.HerolandOrderService;
import com.heroland.competition.service.order.HerolandPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 *支付那边是超过30min就不可以再继续支付了
 *这边对于数据库中expireTime已经过去的订单进行关单
 *
 */
@Component
@Slf4j
public class CloseOrderTask {
    @Resource
    private HerolandOrderService herolandOrderService;

    @Resource
    private HerolandPayService herolandPayService;

    private final String CLOSE_REASON = "超時關單";

    /**
     * 0 15 10 * * ? *
     */
//    @Scheduled(cron = "0 27 23 ? * *")
    @Scheduled(fixedRate = 10000)
    public void batchCloseOrder() {
        Date now = new Date();
        log.info("CloseOrderTask begin ## [{}]", now);
        List<HerolandPayDP> payDPS = herolandPayService.getPayByExpireTimeAndState(now, Lists.newArrayList(OrderStateEnum.CREATED.getCode()));
        if (!CollectionUtils.isEmpty(payDPS)){
            //进行关单操作
            //批量关单
            List<Long> ids = payDPS.stream().map(HerolandPayDP::getId).distinct().collect(Collectors.toList());
            herolandPayService.updatePayState(OrderStateEnum.CLOSED.getCode(), ids);
            List<String> bizNos = payDPS.stream().map(HerolandPayDP::getBizNo).distinct().collect(Collectors.toList());
            throw new AppSystemException();
//            herolandOrderService.closeOrders(CLOSE_REASON, now, bizNos);
        }
    }

}
