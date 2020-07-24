package com.heroland.competition.service;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.qo.HeroLandAccountQO;

import java.util.List;
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
    ResponseBody<Set<Object>> getOnLineUserByType(HeroLandAccountDP dp);

     /**
     * 查询当前人的比赛记录
     * @param dp
     * @return
     */
    ResponseBody<HeroLandAccountDP> getCurrentUserCompetition(HeroLandAccountDP dp);

     /**
     * 查询当前人账户信息
     * @param qo
     * @return
     */
    ResponseBody<List<HeroLandAccountDP>> getAccount(HeroLandAccountQO qo);

    /**
     * 查询当前个人账户详情
     * @param qo
     * @return
     */
    ResponseBody<HeroLandAccountDP> getAccountByUserId(String userId);



}
