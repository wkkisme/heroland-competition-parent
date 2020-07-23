package com.heroland.competition.provider;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.api.HeroLandInviteRecordRemoteService;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.qo.HeroLandInviteRecordQO;
import com.heroland.competition.service.HeroLandInviteRecordService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:16 下午
 */
@DubboService
@AllArgsConstructor
public class HeroLandInviteRecordRemoteServiceImpl implements HeroLandInviteRecordRemoteService {

    private final HeroLandInviteRecordService inviteRecordService;

    @Override
    public ResponseBody<String> addInvite(HeroLandInviteRecordDP dp) {
        return inviteRecordService.addInvite(dp);
    }

    @Override
    public ResponseBody<Boolean> invite(HeroLandInviteRecordDP dp) {
        return null;
    }

    @Override
    public ResponseBody<Boolean> cancelInvite(HeroLandInviteRecordDP dp) {
        return null;
    }

    @Override
    public ResponseBody<List<HeroLandInviteRecordDP>> getInvite(HeroLandInviteRecordQO qo) {
        return inviteRecordService.getInvite(qo);
    }

    @Override
    public ResponseBody<Boolean> agreeInvite(HeroLandInviteRecordDP dp) {
        return null;
    }

    @Override
    public ResponseBody<Boolean> updateInvite(HeroLandInviteRecordDP dp) {
        return inviteRecordService.updateInvite(dp);
    }
}
