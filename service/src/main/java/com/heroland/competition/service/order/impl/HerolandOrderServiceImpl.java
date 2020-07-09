package com.heroland.competition.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.dal.mapper.HerolandOrderMapper;
import com.heroland.competition.dal.pojo.order.HerolandOrder;
import com.heroland.competition.domain.dp.HerolandOrderDP;
import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.domain.qo.PayOrderQO;
import com.heroland.competition.service.order.HerolandOrderService;
import com.heroland.competition.service.order.HerolandPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/8
 */
@Component
@Slf4j
public class HerolandOrderServiceImpl implements HerolandOrderService {

    @Resource
    private HerolandOrderMapper herolandOrderMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private HerolandPayService herolandPayService;

    @Override
    public HerolandOrderDP createOrder(HerolandOrderDP herolandOrderDP){
        //todo 锁校验，防止并发生成多个订单
        HerolandOrderDP orderDP = null;
        try {
            HerolandOrder herolandOrder = BeanUtil.insertConversion(herolandOrderDP.checkAndBuildBeforeCreate(), new HerolandOrder());
            herolandOrderMapper.insert(herolandOrder);
             BeanUtil.conversion(herolandOrder, orderDP);
        } catch (Exception e) {
            log.error("createOrder error, [{}]", JSON.toJSONString(herolandOrderDP));
            ResponseBodyWrapper.failSysException();
        }
        return orderDP;
    }

    @Override
    public Long createPayOrder(HerolandOrderDP herolandOrder) {
        HerolandOrderDP order = createOrder(herolandOrder);
        HerolandPayDP herolandPayDP = new HerolandPayDP();
        herolandPayDP.setBizNo(order.getBizNo());
        herolandPayDP.setBuyId(order.getBuyerId());
        herolandPayDP.setBuyId(order.getBuyerId());
        herolandPayDP.setCurrencyType(order.getCurrencyType());
        herolandPayDP.setSettleAmt(order.getCurrencyAmt());
        return herolandPayService.createPay(herolandPayDP);
    }

    @Override
    public HerolandOrderDP payOrderCallBack(PayOrderQO payOrderQO) {

        return null;
    }

    @Override
    public List<HerolandOrderDP> listOrder(String userId, List<String> status) {
        AssertUtils.notBlank(userId);
        List<HerolandOrder> list = herolandOrderMapper.listOrderByBuyer(userId, status);
        return null;
    }

    @Override
    public void closeOrder(Long orderId, String userId) {

    }
}
