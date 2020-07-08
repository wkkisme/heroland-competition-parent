package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HerolandChapterDP;
import com.heroland.competition.domain.dp.HerolandOrderDP;
import com.heroland.competition.domain.qo.HerolandChapterQO;
import com.heroland.competition.domain.qo.HerolandOrderQO;
import com.heroland.competition.domain.qo.HerolandOrderQueryQO;
import com.heroland.competition.service.admin.HeroLandChapterService;
import com.heroland.competition.service.order.HerolandOrderService;
import com.heroland.competition.service.order.HerolandPayService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 订单
 */
@RestController
@RequestMapping("/heroland/order")
public class HeroLandOrderController {

    @Resource
    private HerolandOrderService herolandOrderService;

    @Resource
    private HerolandPayService herolandPayService;

    /**
     * 创建订单
     * @param qo
     * @return
     */
    @RequestMapping(value = "/create", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Long> createOrder(@RequestBody HerolandOrderQO qo) {
        ResponseBody<Long> result = new ResponseBody<Long>();
        HerolandOrderDP orderDP = new HerolandOrderDP();
        orderDP.setBuyerName("XX");
        orderDP.setSpuId("diamond");
        orderDP.setSpuName("diamond");
        orderDP.setSkuId(qo.getSkuId());
        orderDP.setSkuName(qo.getSkuId());
        orderDP.setSkuNum(qo.getSkuNum());
        orderDP.setSkuCurrencyType("HKD");
        orderDP.setSkuPrice(100L);
        HerolandOrderDP order = herolandOrderService.createOrder(orderDP);
        result.setData(herolandOrderService.createPayOrder(order));
        return result;
    }

    /**
     * 获取充值记录
     * @param qo
     * @return
     */
    @RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandOrderDP>> listOrder(@RequestBody HerolandOrderQueryQO qo) {
        ResponseBody<List<HerolandOrderDP>> result = new ResponseBody<>();

        List<HerolandOrderDP> herolandOrderDPS = herolandOrderService.listOrder(qo.getBuyerId(), qo.getStatus());
        result.setData(herolandOrderDPS);
        return result;
    }



}
