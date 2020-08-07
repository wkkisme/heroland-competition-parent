package com.heroland.competition.controller;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.constant.ErrMsgEnum;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.qo.HeroLandInviteRecordQO;
import com.heroland.competition.service.HeroLandAccountService;
import com.heroland.competition.service.HeroLandInviteRecordService;
import com.platform.sso.client.sso.util.CookieUtils;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platform.sso.facade.result.RpcResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Resource
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;
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


    /**
     * 获取当前用户是否有正在邀请中的记录
     * @return e
     */
    @RequestMapping("/getInviting")
    public ResponseBody<List<HeroLandInviteRecordDP>> getCurrentInvitingRecord( HttpServletRequest request) {
        HeroLandInviteRecordQO heroLandInviteRecord = new HeroLandInviteRecordQO();
        RpcResult<PlatformSysUserDP> result = platformSsoUserServiceFacade.queryCurrent(CookieUtils.getSessionId(request));
        PlatformSysUserDP data = result.getData();
        if (data != null && data.getUserId() != null){
            heroLandInviteRecord.setBeInviteUserId(data.getUserId());
            heroLandInviteRecord.setInviteUserId(data.getUserId());
            return heroLandInviteRecordService.getCurrentInvitingRecord(heroLandInviteRecord);
        }

        return ResponseBodyWrapper.fail(ErrMsgEnum.PLEASE_LOGIN);

    }


}
