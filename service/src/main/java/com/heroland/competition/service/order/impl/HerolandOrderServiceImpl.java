package com.heroland.competition.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.dal.mapper.HerolandOrderMapper;
import com.heroland.competition.dal.mapper.HerolandSkuMapper;
import com.heroland.competition.dal.pojo.HerolandSku;
import com.heroland.competition.dal.pojo.order.HerolandOrder;
import com.heroland.competition.domain.dp.HerolandOrderDP;
import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.domain.dp.HerolandSkuDP;
import com.heroland.competition.domain.qo.PayOrderQO;
import com.heroland.competition.service.diamond.HerolandDiamondService;
import com.heroland.competition.service.order.HerolandOrderService;
import com.heroland.competition.service.order.HerolandPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private HerolandDiamondService herolandDiamondService;

    @Resource
    private HerolandSkuMapper herolandSkuMapper;

    @Override
    public HerolandOrderDP createOrder(HerolandOrderDP herolandOrderDP){
        //todo 锁校验，防止并发生成多个订单
        HerolandOrderDP orderDP = null;

        HerolandSku sku = herolandSkuMapper.getBySkuId(herolandOrderDP.getSkuId());
        if (sku == null){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.NO_DIMOND.getErrorMessage());
        }
        herolandOrderDP.setSkuName(sku.getSkuName());
        herolandOrderDP.setSkuCurrencyType(sku.getCurrencyType());
        herolandOrderDP.setSkuPrice(sku.getSkuPrice());
        herolandOrderDP = herolandOrderDP.checkAndBuildBeforeCreate();
        HerolandOrder herolandOrder = BeanCopyUtils.copyByJSON(herolandOrderDP, HerolandOrder.class);
        herolandOrderMapper.insertSelective(herolandOrder);
        orderDP =  BeanCopyUtils.copyByJSON(herolandOrder, HerolandOrderDP.class);
        return orderDP;
    }

    @Override
    @Transactional
    public Long createPayOrder(HerolandOrderDP herolandOrder) {
        HerolandOrderDP order = createOrder(herolandOrder);
        HerolandPayDP herolandPayDP = new HerolandPayDP();
        herolandPayDP.setBizNo(order.getBizNo());
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
