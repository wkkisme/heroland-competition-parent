package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import com.heroland.competition.common.constants.SPUEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.domain.dp.HerolandOrderDP;
import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.domain.dto.HerolandOrderListDto;
import com.heroland.competition.domain.dto.HerolandPayDto;
import com.heroland.competition.domain.dto.PrePayDto;
import com.heroland.competition.domain.qo.HerolandOrderQO;
import com.heroland.competition.domain.qo.HerolandOrderQueryQO;
import com.heroland.competition.domain.qo.PrePayQO;
import com.heroland.competition.service.impl.task.QueryOrderTask;
import com.heroland.competition.service.order.HerolandOrderService;
import com.heroland.competition.service.order.HerolandPayService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Resource
    private QueryOrderTask queryOrderTask;

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
        orderDP.setBuyerName(qo.getBuyerName());
        orderDP.setSpuId(SPUEnum.DIAMOND.getDesc());
        orderDP.setSpuName(SPUEnum.DIAMOND.getName());
        orderDP.setSkuId(qo.getSkuId());
        orderDP.setSkuNum(qo.getSkuNum());
        orderDP.setBuyerId(qo.getBuyerId());
//        HerolandOrderDP order = herolandOrderService.createOrder(orderDP);
        result.setData(herolandOrderService.createPayOrder(orderDP));
        return result;
    }

    /**
     * 获取充值记录
     * @param qo
     * @return
     */
    @RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<List<HerolandOrderListDto>> listOrder(@RequestBody HerolandOrderQueryQO qo) {
        ResponseBody<List<HerolandOrderListDto>> result = new ResponseBody<>();
        PageResponse<HerolandOrderListDto> pageResponse = herolandOrderService.listOrder(qo);
        result.setData(pageResponse.getItems());
        Pagination pagination = new Pagination();
        pagination.setPageIndex(pageResponse.getPage());
        pagination.setPageSize(pageResponse.getPageSize());
        pagination.setTotalCount(pageResponse.getTotal());
        pagination.setTotalPage(pageResponse.getTotalPages());
        result.setPage(pagination);
        return result;
    }

    /**
     * 预支付
     * @param prePayQO
     * @return
     */
    @RequestMapping(value = "/prePay", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<PrePayDto> prePay(@RequestBody PrePayQO prePayQO){
        ResponseBody<PrePayDto> result = new ResponseBody<>();
        result.setData(herolandPayService.prePay(prePayQO));
        return result;
    }

//    @RequestMapping(value = "/prePay", produces = "application/json;charset=UTF-8")
//    @org.springframework.web.bind.annotation.ResponseBody
//    public ResponseBody<PrePayDto> prePay(@RequestParam("payId") Long payId){
//        ResponseBody<PrePayDto> result = new ResponseBody<>();
//        PrePayQO prePayQO = new PrePayQO();
//        prePayQO.setPayId(payId);
//        result.setData(herolandPayService.prePay(prePayQO));
//        return result;
//    }

    /**
     * 获取一条充值记录的详细
     * @param id
     * @return
     */
    @RequestMapping(value = "/id", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<HerolandPayDto> get(@RequestParam("id") Long id){
        ResponseBody<HerolandPayDto> result = new ResponseBody<>();
        HerolandPayDP payById = herolandPayService.getPayById(id);
        result.setData(BeanCopyUtils.copyByJSON(payById, HerolandPayDto.class));
        return result;
    }

    @RequestMapping(value = "/trigger", produces = "application/json;charset=UTF-8")
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseBody<Boolean> query(){
       queryOrderTask.queryOrder();
       return null;
    }








}
