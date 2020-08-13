package com.heroland.competition.service.order.impl;

import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.constants.OrderStateEnum;
import com.heroland.competition.common.constants.PayEnvEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandPayMapper;
import com.heroland.competition.dal.pojo.order.HerolandPay;
import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.domain.dto.PrePayDto;
import com.heroland.competition.domain.qo.PrePayQO;
import com.heroland.competition.service.order.HerolandPayService;
import com.platfrom.payment.api.PrePayRemoteService;
import com.platfrom.payment.request.PrePayRequest;
import com.platfrom.payment.response.PrePayResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
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

    @Override
    public Long createPay(HerolandPayDP herolandPayDP) {
        HerolandPay herolandOrder = BeanCopyUtils.copyByJSON(herolandPayDP.checkAndBuildBeforeCreate(), HerolandPay.class);
        herolandPayMapper.insertSelective(herolandOrder);
        return herolandPayDP.getId();
    }

    @Override
    public void updatePay(HerolandPayDP herolandPay) {
        if (StringUtils.isBlank(herolandPay.getBizNo())){
            ResponseBodyWrapper.failSysException();
        }
        try {
            HerolandPay pay = BeanUtil.updateConversion(herolandPay, new HerolandPay());
            herolandPayMapper.updateByBizNo(pay);
        } catch (Exception e) {
            log.error("updatePay error", e);
            ResponseBodyWrapper.failSysException();
        }
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
        PrePayResponse prePayResponse = prePayRemoteService.prePay(request);
        //todo 如果是0元则不用拉出收银台
        prePayDto.setPayId(prePayQO.getPayId());
        if (NumberUtils.nullOrZeroLong(payById.getSettleAmt())){
            prePayDto.setNeedRedirect(false);
        }else {
            prePayDto.setRedirectUrl(prePayResponse.getRedirectUrl());
        }
        return prePayDto;
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
