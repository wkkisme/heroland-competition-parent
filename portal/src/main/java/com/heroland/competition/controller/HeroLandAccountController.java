package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;
import com.heroland.competition.service.HeroLandAccountService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 个人信息
 * @author mac
 */
@RestController
@RequestMapping("/heroland/account")
public class HeroLandAccountController {


    @Resource
    private HeroLandAccountService  heroLandAccountService;

    /**
     * 查询同步比赛里统计信息
     * @param heroLandAccountDP h
     * @return e
     * @module 題目組
     */
    @RequestMapping("/queryCurrentCompetitionInfo")
    public ResponseBody<HeroLandAccountDP> getTopicQuestion(@RequestBody HeroLandAccountDP heroLandAccountDP){

        return heroLandAccountService.getCurrentUserCompetition(heroLandAccountDP);
    }


}
