package com.heroland.competition.api;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.qo.HerolandDiamRechargeQO;

/**
 * @author smjyouzan
 * @date 2020/8/3
 */
public interface HeroLandRechargeRemoteService {

    /**
     *
     * @param qo
     */
    ResponseBody<Boolean> recharge(HerolandDiamRechargeQO qo);
}
