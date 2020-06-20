package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;
import com.heroland.competition.service.HeroLandInviteRecordService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 邀请
 * @author mac
 */

@RestController
@RequestMapping("/invite")
public class HeroLandInviteRecordController {

    @Resource
    private HeroLandInviteRecordService heroLandInviteRecordService;

    /**
     *
     * @param heroLandInviteRecord h
     * @return e
     */
    @RequestMapping("/queryQuestions")
    public  ResponseBody<?> invite(@RequestBody HeroLandInviteRecordDP heroLandInviteRecord){

        return heroLandInviteRecordService.invite(heroLandInviteRecord);
    }
}
