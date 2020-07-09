package com.heroland.competition.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.contants.OrderStateEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandPayMapper;
import com.heroland.competition.dal.pojo.order.HerolandPay;
import com.heroland.competition.domain.dp.HerolandOrderDP;
import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.domain.dto.PrePayDto;
import com.heroland.competition.domain.qo.PrePayQO;
import com.heroland.competition.service.order.HerolandPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 */
@Component
@Slf4j
public class HerolandPayServiceImpl implements HerolandPayService {

    @Resource
    private HerolandPayMapper herolandPayMapper;

    @Override
    public Long createPay(HerolandPayDP herolandPayDP) {

        HerolandOrderDP orderDP = null;
        try {
            HerolandPay herolandOrder = BeanUtil.insertConversion(herolandPayDP.checkAndBuildBeforeCreate(), new HerolandPay());
            herolandPayMapper.insert(herolandOrder);
            BeanUtil.conversion(herolandOrder, orderDP);
        } catch (Exception e) {
            log.error("createPay error, [{}]", JSON.toJSONString(herolandPayMapper));
            ResponseBodyWrapper.failSysException();
        }
        return herolandPayDP.getId();
    }

    @Override
    public void updatePay(HerolandPayDP herolandPay) {
        if (StringUtils.isBlank(herolandPay.getBizNo())){
            ResponseBodyWrapper.failSysException();;
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

        try {
           result  = BeanUtil.conversion(herolandPay, new HerolandPayDP());
        } catch (Exception e) {
            log.error("getPayById error", e);
            ResponseBodyWrapper.failSysException();
        }
        return result;
    }

    @Override
    public PrePayDto prePay(PrePayQO prePayQO) {
        PrePayDto prePayDto = new PrePayDto();
        HerolandPayDP payById = getPayById(prePayQO.getPayId());
        if (OrderStateEnum.CREATED.getCode() != payById.getState()){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_PAY_STATE.getErrorMessage());
        }
        //todo 调用预支付

        //todo 如果是0元则不用拉出收银台
        prePayDto.setPayId(prePayQO.getPayId());
        return prePayDto;
    }
}
