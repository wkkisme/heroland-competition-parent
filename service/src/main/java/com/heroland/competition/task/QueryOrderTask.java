package com.heroland.competition.task;

import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.OrderStateEnum;
import com.heroland.competition.common.utils.DateUtils;
import com.heroland.competition.dal.mapper.HerolandPayMapper;
import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.service.order.HerolandOrderService;
import com.heroland.competition.service.order.HerolandPayService;
import com.platfrom.payment.api.PayQueryRemoteService;
import com.platfrom.payment.api.PrePayRemoteService;
import com.platfrom.payment.dto.PayOrderDTO;
import com.platfrom.payment.request.QueryPayRequest;
import com.platfrom.payment.response.QueryPayResponse;
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
import java.util.Map;
import java.util.stream.Collectors;


/**
 *
 */
@Component
@Slf4j
public class QueryOrderTask {
    @Resource
    private HerolandOrderService herolandOrderService;

    @Resource
    private HerolandPayService herolandPayService;

    @Resource
    private PayQueryRemoteService payQueryRemoteService;

    @Resource
    private HerolandPayMapper herolandPayMapper;

    private final String CLOSE_REASON = "超時關單";

    /**
     * 0 15 10 * * ? *
     */
    @Scheduled(cron = "0 27 23 ? * *")
    @Transactional(value="defaultTransactionManager",propagation= Propagation.REQUIRED,isolation= Isolation.READ_UNCOMMITTED,rollbackFor=Throwable.class)
    public void queryOrder() {
        Date now = new Date();
        List<HerolandPayDP> payDPS = herolandPayService.getOrderByState(now, Lists.newArrayList(OrderStateEnum.CREATED.getCode()));
        if (!CollectionUtils.isEmpty(payDPS)){
            List<Long> ids = payDPS.stream().map(HerolandPayDP::getId).distinct().collect(Collectors.toList());
            QueryPayRequest request = new QueryPayRequest();
            request.setBizId(ids.stream().map(e -> (e+"")).collect(Collectors.toList()));
            request.setBizType("competition");
            QueryPayResponse payResponse = payQueryRemoteService.batchFindPayOrder(request);
            if (payResponse == null){
                return;
            }
            List<PayOrderDTO> payOrderDTOS = payResponse.getPayOrderDTOS();
            Map<String, List<PayOrderDTO>> stateMap = payOrderDTOS.stream().collect(Collectors.groupingBy(PayOrderDTO::getState));
            for (Map.Entry<String, List<PayOrderDTO>> entry : stateMap.entrySet()){
                if (entry.getKey().equalsIgnoreCase("CREATE")){
                    continue;
                }
                if (entry.getKey().equalsIgnoreCase("SUCCESS")){
                    entry.getValue().stream().forEach(payId -> {
                        HerolandPayDP paydP = herolandPayService.getPayById(Long.parseLong(payId.getBizId()));
                        paydP.setState(OrderStateEnum.PAID.getCode());
                        paydP.setPayFinishTime(DateUtils.string2Date(payId.getPayFinishTime(),"yyyy-MM-dd HH:mm:ss"));
                        herolandPayService.updatePay(paydP);
                        //处理order
                        herolandOrderService.updateStateByBiz(paydP.getBizNo(), paydP.getPayFinishTime());
                    });
                }
                if (entry.getKey().equalsIgnoreCase("FAILED")){
                    List<Long> bizIds = entry.getValue().stream().map(PayOrderDTO::getBizId).map(Long::parseLong).collect(Collectors.toList());
                    herolandPayService.updatePayState(OrderStateEnum.CLOSED.getCode(), ids);
                    List<String> bizNos = payDPS.stream().map(HerolandPayDP::getBizNo).distinct().collect(Collectors.toList());
                    herolandOrderService.closeOrders(CLOSE_REASON, now, bizNos);
                }
            }
        }
    }

}
