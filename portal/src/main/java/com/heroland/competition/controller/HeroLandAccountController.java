package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.qo.HeroLandAccountQO;
import com.heroland.competition.service.HeroLandAccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 个人账户信息
 *
 * @author mac
 */
@RestController
@RequestMapping("/heroland/account")
public class HeroLandAccountController {

    /**
     * # 逆境英雄 level 3
     */
    @Value("${hero.level.courageousHero}")
    private Integer courageousHero;


    @Resource
    private HeroLandAccountService heroLandAccountService;

    /**
     * 查询同步比赛里统计信息
     *
     * @param heroLandAccountDP h
     * @return e
     * @module 題目組
     */
    @RequestMapping("/queryCurrentCompetitionInfo")
    public ResponseBody<HeroLandAccountDP> getTopicQuestion(@RequestBody HeroLandAccountDP heroLandAccountDP) {

        return heroLandAccountService.getCurrentUserCompetition(heroLandAccountDP);
    }

    /**
     * 查询个人账户信息
     *
     * @param qo h
     * @return e
     * @module 題目組
     */
    @RequestMapping("/queryAccount")
    public ResponseBody<HeroLandAccountDP> getAccount(@RequestBody HeroLandAccountQO qo) {
        ResponseBody<HeroLandAccountDP> result = new ResponseBody<HeroLandAccountDP>();
        ResponseBody<HeroLandAccountDP> account = heroLandAccountService.getAccountByUserId(qo.getUserId(),qo.getTopicType());
        HeroLandAccountDP data = account.getData();
        result.setData(data);
        return result;
    }


}
