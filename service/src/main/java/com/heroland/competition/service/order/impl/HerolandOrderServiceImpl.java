package com.heroland.competition.service.order.impl;

import com.anycommon.cache.service.RedisService;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constant.RedisConstant;
import com.heroland.competition.common.constants.DiamBizGroupEnum;
import com.heroland.competition.common.constants.DiamBizTypeEnum;
import com.heroland.competition.common.constants.OrderStateEnum;
import com.heroland.competition.common.constants.StockEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.dal.mapper.HerolandOrderMapper;
import com.heroland.competition.dal.mapper.HerolandSkuMapper;
import com.heroland.competition.dal.pojo.HerolandQuestionBank;
import com.heroland.competition.dal.pojo.HerolandSku;
import com.heroland.competition.dal.pojo.order.HerolandOrder;
import com.heroland.competition.domain.dp.HerolandOrderDP;
import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.domain.dto.HeroLandQuestionBankSimpleDto;
import com.heroland.competition.domain.dto.HerolandOrderListDto;
import com.heroland.competition.domain.qo.HerolandOrderQueryQO;
import com.heroland.competition.domain.qo.PayOrderQO;
import com.heroland.competition.domain.request.HerolandDiamRequest;
import com.heroland.competition.service.diamond.HerolandDiamondService;
import com.heroland.competition.service.order.HerolandOrderService;
import com.heroland.competition.service.order.HerolandPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private RedisService redisService;

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
        String key = String.format(RedisConstant.ORDER_CREATE_KEY, herolandOrder.getBuyerId(), herolandOrder.getSkuId());
        boolean lock = redisService.setNx(key, herolandOrder, "PT10S");
        if (!lock){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.DUPLICATE_ORDER.getErrorMessage());
        }
        try {
            HerolandOrderDP order = createOrder(herolandOrder);
            HerolandPayDP herolandPayDP = new HerolandPayDP();
            herolandPayDP.setBizNo(order.getBizNo());
            herolandPayDP.setBuyId(order.getBuyerId());
            herolandPayDP.setCurrencyType(order.getCurrencyType());
            herolandPayDP.setSettleAmt(order.getCurrencyAmt());
            return herolandPayService.createPay(herolandPayDP);
        }finally {
            redisService.del(key);
        }

    }

    @Override
    public Boolean payOrderCallBack(PayOrderQO payOrderQO) {
        HerolandPayDP payById = herolandPayService.getPayById(payOrderQO.getPayId());
        if (payById == null){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PAY.getErrorMessage());
        }
        String bizNo = payById.getBizNo();
        List<HerolandOrder> byBizNos = herolandOrderMapper.getByBizNos(Lists.newArrayList(bizNo));
        if (CollectionUtils.isEmpty(byBizNos)){
            return false;
        }
        HerolandOrder herolandOrder = byBizNos.get(0);
        herolandOrder.setState(OrderStateEnum.PAID.getCode());
        herolandOrderMapper.updateByPrimaryKeySelective(herolandOrder);
        payById.setPaymentNo(payOrderQO.getPaymentNo());
        payById.setPayTool(payOrderQO.getPayTool());
        payById.setPayFinishTime(payOrderQO.getPayFinishTime());
        payById.setState(OrderStateEnum.PAID.getCode());
        herolandPayService.updatePay(payById);

        HerolandDiamRequest request = new HerolandDiamRequest();
        request.setBizGroup(DiamBizGroupEnum.BUY.getGroup());
        request.setBizName(DiamBizTypeEnum.PAY.getValue());
        request.setNum(herolandOrder.getSkuNum());
        request.setUserId(herolandOrder.getBuyerId());
        request.setChangeStockType(StockEnum.INCREASE.getLevel());
        herolandDiamondService.createDiamondRecord(request);
        return true;
    }

    @Override
    public Boolean updateStateByBiz(String bizNo, Date paidTime) {
        if (paidTime == null){
            paidTime = new Date();
        }
        herolandOrderMapper.updateStateByBiz(bizNo, paidTime);
        return true;
    }

    @Override
    public PageResponse<HerolandOrderListDto> listOrder(HerolandOrderQueryQO qo) {
        AssertUtils.notBlank(qo.getBuyerId());
        PageResponse<HerolandOrderListDto> pageResult = new PageResponse<>();
        List<HerolandOrderListDto> list = Lists.newArrayList();
        Page<HerolandOrder> data = PageHelper.startPage(qo.getPageIndex(), qo.getPageSize(), true).doSelectPage(
                () -> herolandOrderMapper.listOrderByBuyer(qo.getBuyerId(), qo.getStatus()));
        if (!CollectionUtils.isEmpty(data.getResult())){
            list = data.getResult().stream().map(this::convertToDto).collect(Collectors.toList());
        }

        pageResult.setItems(list);
        pageResult.setPageSize(data.getPageSize());
        pageResult.setPage(data.getPageNum());
        pageResult.setTotal((int) data.getTotal());
       return pageResult;
    }

    private HerolandOrderListDto convertToDto(HerolandOrder order){
        HerolandOrderListDto dto = new HerolandOrderListDto();
        dto.setBizNo(order.getBizNo());
        dto.setBuyerId(order.getBuyerId());
        dto.setSkuId(order.getSkuId());
        dto.setSkuName(order.getSkuName());
        dto.setCurrencyAmt(order.getCurrencyAmt());
        dto.setCurrencyType(order.getCurrencyType());
        dto.setPaidTime(order.getPaidTime());
        dto.setCloseTime(order.getCloseTime());
        dto.setState(order.getState());
        dto.setOrderCreateTime(order.getGmtCreate());
        HerolandSku bySkuId = herolandSkuMapper.getBySkuIdWithDelete(order.getSkuId());
        dto.setSkuUnit(bySkuId.getUnit());
        dto.setSkuNum(order.getSkuNum() * dto.getSkuUnit());
        //如果是创建的订单再去拉一下是否有付款
        if (order.getState().equalsIgnoreCase(OrderStateEnum.CREATED.getCode())){
            //todo
            //rpc 调用一下或者写一个定时器

        }
        return dto;
    }

    @Override
    public void closeOrder(Long orderId, String userId) {

    }

    @Override
    public void closeOrders(String closeReason, Date closeTime, List<String> bizNos) {
        if (CollectionUtils.isEmpty(bizNos)){
            return;
        }
        herolandOrderMapper.closeOrders(closeReason, closeTime, bizNos);
    }
}
