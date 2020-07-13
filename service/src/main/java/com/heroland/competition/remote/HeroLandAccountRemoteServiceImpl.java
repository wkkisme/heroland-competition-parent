package com.heroland.competition.remote;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.api.HeroLandAccountRemoteService;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.qo.HeroLandAccountQO;

import java.util.List;
import java.util.Set;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:15 下午
 */
public class HeroLandAccountRemoteServiceImpl implements HeroLandAccountRemoteService {
    @Override
    public ResponseBody<Set<HeroLandAccountDP>> getOnLineUserByType(HeroLandAccountDP dp) {
        return null;
    }

    @Override
    public ResponseBody<HeroLandAccountDP> getCurrentUserCompetition(HeroLandAccountDP dp) {
        return null;
    }

    @Override
    public ResponseBody<List<HeroLandAccountDP>> getAccount(HeroLandAccountQO qo) {
        return null;
    }
}
