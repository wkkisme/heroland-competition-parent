package com.heroland.competition.remote;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.api.HeroLandAccountRemoteService;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.qo.HeroLandAccountManageQO;
import com.heroland.competition.service.HeroLandAccountService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author wushuaiping
 * @date 2020/8/2 11:36
 */
@Service(version = "1.0.0")
@AllArgsConstructor
public class HeroLandAccountRemoteServiceImpl implements HeroLandAccountRemoteService {

    private final HeroLandAccountService heroLandAccountService;

    /**
     * 减钻石
     *
     * @param dp
     * @return
     */
    @Override
    public ResponseBody<HeroLandAccountDP> decrUserDiamond(HeroLandAccountManageQO dp) {
        return heroLandAccountService.decrUserDiamond(dp);
    }

}
