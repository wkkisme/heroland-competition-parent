package com.heroland.competition.api;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.qo.HeroLandAccountManageQO;

/**
 * @author wushuaiping
 * @date 2020/8/2 11:36
 */
public interface HeroLandAccountRemoteService {

    /**
     * 减钻石
     * @param dp
     * @return
     */
    ResponseBody<HeroLandAccountDP> decrUserDiamond(HeroLandAccountManageQO dp);

}
