package com.heroland.competition.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandOrderMapper;
import com.heroland.competition.dal.mapper.HerolandPayMapper;
import com.heroland.competition.dal.pojo.basic.HerolandChapter;
import com.heroland.competition.dal.pojo.order.HerolandOrder;
import com.heroland.competition.dal.pojo.order.HerolandPay;
import com.heroland.competition.domain.dp.HerolandOrderDP;
import com.heroland.competition.domain.dp.HerolandPayDP;
import com.heroland.competition.service.order.HerolandPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
}
