package com.heroland.competition.remote;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.api.HeroLandRechargeRemoteService;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.domain.qo.PayOrderQO;
import com.heroland.competition.qo.HerolandDiamRechargeQO;
import com.heroland.competition.service.order.HerolandOrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author smjyouzan
 * @date 2020/8/3
 */
@Service(version = "1.0.0")
public class HeroLandRechargeRemoteServiceImpl implements HeroLandRechargeRemoteService {


    @Resource
    private HerolandOrderService herolandOrderService;


    @Override
    public ResponseBody<Boolean> recharge(HerolandDiamRechargeQO qo) {
        ResponseBody<Boolean> result = new ResponseBody<Boolean>();
        if (NumberUtils.nullOrZeroLong(qo.getPayId()) || StringUtils.isBlank(qo.getUserId())){
            return result;
        }
        PayOrderQO payOrderQO = new PayOrderQO();
        payOrderQO.setPayId(qo.getPayId());
        payOrderQO.setPaymentNo(qo.getPaymentNo());
        herolandOrderService.payOrderCallBack(payOrderQO);
        result.setData(true);
        return result;
    }
}
