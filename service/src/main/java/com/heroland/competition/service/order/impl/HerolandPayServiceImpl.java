package com.heroland.competition.service.order.impl;

import com.anycommon.cache.service.RedisService;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constant.RedisConstant;
import com.heroland.competition.common.constants.*;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandOrderMapper;
import com.heroland.competition.dal.mapper.HerolandPayMapper;
import com.heroland.competition.dal.pojo.HerolandDiamondStockLog;
import com.heroland.competition.dal.pojo.order.HerolandOrder;
import com.heroland.competition.dal.pojo.order.HerolandPay;
import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.domain.dto.PrePayDto;
import com.heroland.competition.domain.qo.HeroLandAccountManageQO;
import com.heroland.competition.domain.qo.PrePayQO;
import com.heroland.competition.domain.request.HerolandDiamRequest;
import com.heroland.competition.service.HeroLandAccountService;
import com.heroland.competition.service.diamond.HerolandDiamondService;
import com.heroland.competition.service.order.HerolandOrderService;
import com.heroland.competition.service.order.HerolandPayService;
import com.platfrom.payment.api.PrePayRemoteService;
import com.platfrom.payment.request.PrePayRequest;
import com.platfrom.payment.response.PrePayResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 */
@Component
@Slf4j
public class HerolandPayServiceImpl implements HerolandPayService {

    @Resource
    private HerolandPayMapper herolandPayMapper;

    @Resource
    private PrePayRemoteService prePayRemoteService;

    @Resource
    private HerolandOrderService herolandOrderService;

    @Resource
    private HerolandOrderMapper herolandOrderMapper;

    @Resource
    private HerolandDiamondService herolandDiamondService;

    @Resource
    private HeroLandAccountService heroLandAccountService;

    @Resource
    private RedisService redisService;

    private final String CLOSE_REASON = "查詢失敗";


    @Override
    public Long createPay(HerolandPayDP herolandPayDP) {
        HerolandPay herolandOrder = BeanCopyUtils.copyByJSON(herolandPayDP.checkAndBuildBeforeCreate(), HerolandPay.class);
        herolandPayMapper.insertSelective(herolandOrder);
        return herolandPayDP.getId();
    }

    @Override
    public void updatePay(HerolandPayDP herolandPayDP) {
        HerolandPay herolandPay = BeanCopyUtils.copyByJSON(herolandPayDP, HerolandPay.class);
        if (!NumberUtils.nullOrZeroLong(herolandPay.getId())){
           herolandPayMapper.updateByPrimaryKeySelective(herolandPay);
           return;
        }else {
            if (StringUtils.isBlank(herolandPay.getBizNo())){
                ResponseBodyWrapper.failSysException();
            }
            herolandPayMapper.updateByBizNo(herolandPay);
        }
    }

    @Override
    public void completePay(HerolandPayDP herolandPay) {
        String key = String.format(RedisConstant.ORDER_DIAMOND_KEY, herolandPay.getBizNo());

        if (redisService.setNx(key, herolandPay.getBizNo(), "PT1h")) {

            try {
                //1 更新pay
                updatePay(herolandPay);
                //更新order
                herolandOrderService.updateStateByBiz(herolandPay.getBizNo(), herolandPay.getPayFinishTime());

                //加库存日志
                List<HerolandOrder> byBizNos = herolandOrderMapper.getByBizNos(Lists.newArrayList(herolandPay.getBizNo()));
                Integer diamNum = 0;
                if (!CollectionUtils.isEmpty(byBizNos)){
                    HerolandDiamRequest request = new HerolandDiamRequest();
                    request.setBizGroup(DiamBizGroupEnum.BUY.getGroup());
                    request.setBizName(DiamBizTypeEnum.PAY.getValue());
                    request.setNum(byBizNos.get(0).getSkuNum());
                    diamNum = request.getNum();
                    request.setUserId(byBizNos.get(0).getBuyerId());
                    request.setChangeStockType(StockEnum.INCREASE.getLevel());
                    herolandDiamondService.createDiamondRecord(request);
                }
//                //加账户总额
//                HeroLandAccountManageQO accountManageQO = new HeroLandAccountManageQO();
//                accountManageQO.setUserId(herolandPay.getBuyId());
//                accountManageQO.setNum(diamNum);
//                if (NumberUtils.nullOrZero(diamNum)){
//                    return;
//                }
//                heroLandAccountService.incrUserDiamond(accountManageQO);
            }catch (Exception e){
                log.error("completePay error, [{}]", herolandPay.getBizNo(), e);
            }finally {
                redisService.del(key);
            }

        }
    }

    @Override
    public void failPay(Long payId) {
        Date now = new Date();
        updatePayState(OrderStateEnum.CLOSED.getCode(), Lists.newArrayList(payId));
        HerolandPayDP payById = getPayById(payId);
        herolandOrderService.closeOrders(CLOSE_REASON, now, Lists.newArrayList(payById.getBizNo()));
    }

    @Override
    public HerolandPayDP getPayById(Long id) {
        AssertUtils.assertThat(!NumberUtils.nullOrZeroLong(id));
        HerolandPayDP result = null;
        HerolandPay herolandPay = herolandPayMapper.selectByPrimaryKey(id);
        if (Objects.isNull(herolandPay)){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PAY.getErrorMessage());
        }
        result = convertToDP(herolandPay);
        return result;
    }

    @Override
    public PrePayDto prePay(PrePayQO prePayQO) {
        String key = String.format(RedisConstant.ORDER_PREPAY_KEY, prePayQO.getPayId());
        if (Objects.equals(prePayQO.getPayTool(), "PAYPAL")){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.PAYTOOL.getErrorMessage());
        }

        boolean lock = redisService.setNx(key, prePayQO.getPayId(), "PT15S");
        if (!lock){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.DUPLICATE.getErrorMessage());
        }
        try {

            PrePayDto prePayDto = new PrePayDto();
            HerolandPayDP payById = getPayById(prePayQO.getPayId());
            if (!OrderStateEnum.CREATED.getCode().equalsIgnoreCase(payById.getState())){
                ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_PAY_STATE.getErrorMessage());
            }
            //调用预支付
            PrePayRequest request = new PrePayRequest();
            request.setPayEnv(PayEnvEnum.PC.getEnv());
            request.setPayChannel(prePayQO.getPayTool());
            request.setCurrencyType(payById.getCurrencyType());
            request.setCurrencyAmt(payById.getSettleAmt());
            request.setBizId(payById.getId()+"");
            request.setBizType("competition");
            request.setReturnUrl(prePayQO.getReturnUrl());
            PrePayResponse prePayResponse = prePayRemoteService.prePay(request);
            //todo 如果是0元则不用拉出收银台
            prePayDto.setPayId(prePayQO.getPayId());
            if (NumberUtils.nullOrZeroLong(payById.getSettleAmt())){
                prePayDto.setNeedRedirect(false);
            }else {
                prePayDto.setRedirectUrl(prePayResponse.getRedirectUrl());
            }
            return prePayDto;

        }finally {
            redisService.del(key);
        }


    }

    @Override
    public List<HerolandPayDP> getPayByExpireTimeAndState(Date time, List<String> states) {
        List<HerolandPay> pays = herolandPayMapper.selectByExpireTimeAndStates(time, states);
        if (CollectionUtils.isEmpty(pays)){
            return Lists.newArrayList();
        }
        List<HerolandPayDP> result = pays.stream().map(this::convertToDP).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<HerolandPayDP> getOrderByState(Date time, List<String> states) {
        List<HerolandPay> pays = herolandPayMapper.selectByStates(time, states);
        if (CollectionUtils.isEmpty(pays)){
            return Lists.newArrayList();
        }
        List<HerolandPayDP> result = pays.stream().map(this::convertToDP).collect(Collectors.toList());
        return result;
    }

    @Override
    public void updatePayState(String state, List<Long> payIds) {
        if (CollectionUtils.isEmpty(payIds)){
            return;
        }
        herolandPayMapper.updatePayState(state, payIds);
    }

    private HerolandPayDP convertToDP(HerolandPay pay){
        HerolandPayDP payDP = new HerolandPayDP();
        payDP.setId(pay.getId());
        payDP.setBizExt(pay.getBizExt());
        payDP.setBizNo(pay.getBizNo());
        payDP.setBuyId(pay.getBuyId());
        payDP.setCurrencyType(pay.getCurrencyType());
        payDP.setExpireTime(pay.getExpireTime());
        payDP.setStartTime(pay.getStartTime());
        payDP.setPayFinishTime(pay.getPayFinishTime());
        payDP.setPaymentNo(pay.getPaymentNo());
        payDP.setPayTool(pay.getPayTool());
        payDP.setPayWay(pay.getPayWay());
        payDP.setState(pay.getState());
        payDP.setSettleAmt(pay.getSettleAmt());
        payDP.setTradeDesc(pay.getTradeDesc());
        return payDP;
    }
}
