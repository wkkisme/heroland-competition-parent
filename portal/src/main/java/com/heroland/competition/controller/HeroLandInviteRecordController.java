package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.qo.HeroLandInviteRecordQO;
import com.heroland.competition.service.HeroLandAccountService;
import com.heroland.competition.service.HeroLandInviteRecordService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

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
     * 邀请人
     * @param heroLandInviteRecord h
     * @return e
     */
    @RequestMapping("/toInvite")
    public ResponseBody<String> invite(@RequestBody HeroLandInviteRecordDP heroLandInviteRecord) {

        return heroLandInviteRecordService.invite(heroLandInviteRecord);
    }

    /**
     * 拒绝邀请
     * @param heroLandInviteRecord h
     * @return e
     */
    @RequestMapping("/cancelInvite")
    public ResponseBody<Boolean> cancelInvite(@RequestBody HeroLandInviteRecordDP heroLandInviteRecord) {

        return heroLandInviteRecordService.cancelInvite(heroLandInviteRecord);
    }

    /**
     * 获取邀请信息
     * @param heroLandInviteRecord h
     * @return e
     */
    @RequestMapping("/getInvite")
    public ResponseBody<List<HeroLandInviteRecordDP>> getInvite(@RequestBody HeroLandInviteRecordQO heroLandInviteRecord) {

        return heroLandInviteRecordService.getInvite(heroLandInviteRecord);
    }

    /**
     * 同意邀请
     * @param heroLandInviteRecord h
     * @return e
     */
    @RequestMapping("/agreeInvite")
    public ResponseBody<Boolean> agreeInvite(@RequestBody HeroLandInviteRecordDP heroLandInviteRecord) {

        return heroLandInviteRecordService.agreeInvite(heroLandInviteRecord);
    }

    /**
     * 更新邀请信息
     * @param heroLandInviteRecord h
     * @return e
     */
    @RequestMapping("/updateInvite")
    public ResponseBody<Boolean> updateInvite(@RequestBody HeroLandInviteRecordDP heroLandInviteRecord) {

        return heroLandInviteRecordService.updateInvite(heroLandInviteRecord);
    }

    /**
     * 获取邀请者列表
     * @param heroLandAccountDP h
     * @return e
     */
    @RequestMapping("/getCanInviteList")
    public ResponseBody<Set<Object>> getCanInviteList(@RequestBody HeroLandAccountDP heroLandAccountDP) {

        return heroLandAccountService.getOnLineUserByType(heroLandAccountDP);
    }


}
