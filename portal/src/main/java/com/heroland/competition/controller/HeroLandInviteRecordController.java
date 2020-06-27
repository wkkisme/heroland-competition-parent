package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.qo.HeroLandInviteRecordQO;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;
import com.heroland.competition.service.HeroLandAccountService;
import com.heroland.competition.service.HeroLandInviteRecordService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 邀请
 *
 * @author mac
 */

@RestController
@RequestMapping("/heroland/invite")
public class HeroLandInviteRecordController {

    @Resource
    private HeroLandInviteRecordService heroLandInviteRecordService;

    @Resource
    private HeroLandAccountService heroLandAccountService;
    /**
     * @param heroLandInviteRecord h
     * @return e
     */
    @RequestMapping("/invite")
    public ResponseBody<?> invite(@RequestBody HeroLandInviteRecordDP heroLandInviteRecord) {

        return heroLandInviteRecordService.invite(heroLandInviteRecord);
    }

    /**
     * @param heroLandInviteRecord h
     * @return e
     */
    @RequestMapping("/cancelInvite")
    public ResponseBody<?> cancelInvite(@RequestBody HeroLandInviteRecordDP heroLandInviteRecord) {

        return heroLandInviteRecordService.cancelInvite(heroLandInviteRecord);
    }

    /**
     * @param heroLandInviteRecord h
     * @return e
     */
    @RequestMapping("/getInvite")
    public ResponseBody<?> getInvite(@RequestBody HeroLandInviteRecordQO heroLandInviteRecord) {

        return heroLandInviteRecordService.getInvite(heroLandInviteRecord);
    }

    /**
     * @param heroLandInviteRecord h
     * @return e
     */
    @RequestMapping("/agreeInvite")
    public ResponseBody<?> agreeInvite(@RequestBody HeroLandInviteRecordDP heroLandInviteRecord) {

        return heroLandInviteRecordService.agreeInvite(heroLandInviteRecord);
    }

    /**
     * @param heroLandInviteRecord h
     * @return e
     */
    @RequestMapping("/updateInvite")
    public ResponseBody<?> updateInvite(@RequestBody HeroLandInviteRecordDP heroLandInviteRecord) {

        return heroLandInviteRecordService.updateInvite(heroLandInviteRecord);
    }

    /**
     * @param heroLandAccountDP h
     * @return e
     */
    @RequestMapping("/getCanInviteList")
    public ResponseBody<?> getCanInviteList(@RequestBody HeroLandAccountDP heroLandAccountDP) {

        return heroLandAccountService.getOnLineUserByType(heroLandAccountDP);
    }


}
