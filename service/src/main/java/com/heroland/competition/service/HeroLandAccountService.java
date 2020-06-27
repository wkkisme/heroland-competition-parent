package com.heroland.competition.service;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.dal.pojo.HeroLandAccount;
import com.heroland.competition.domain.dp.HeroLandAccountDP;

import java.util.Set;

/**
 * 账户服务
 * @author wangkai
 */
public interface HeroLandAccountService {
    /**
     * 查询所有的在线用户
     * @param dp
     * @return
     */
    ResponseBody<Set<HeroLandAccountDP>> getOnLineUserByType(HeroLandAccountDP dp);
}
