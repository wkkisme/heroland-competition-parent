package com.heroland.competition.remote;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.api.HeroLandInviteRecordRemoteService;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.qo.HeroLandInviteRecordQO;
import com.heroland.competition.service.HeroLandInviteRecordService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:16 下午
 */
@Service(version = "1.0.0")
@AllArgsConstructor
public class HeroLandInviteRecordRemoteServiceImpl implements HeroLandInviteRecordRemoteService {

    private final HeroLandInviteRecordService inviteRecordService;

    @Override
    public ResponseBody<String> addInvite(HeroLandInviteRecordDP dp) {
        return inviteRecordService.addInvite(dp);
    }

    @Override
    public ResponseBody<String> invite(HeroLandInviteRecordDP dp) {
        return inviteRecordService.invite(dp);
    }

    @Override
    public ResponseBody<Boolean> cancelInvite(HeroLandInviteRecordDP dp) {
        return inviteRecordService.cancelInvite(dp);
    }

    @Override
    public ResponseBody<List<HeroLandInviteRecordDP>> getInvite(HeroLandInviteRecordQO qo) {
        return inviteRecordService.getInvite(qo);
    }

    @Override
    public ResponseBody<Boolean> agreeInvite(HeroLandInviteRecordDP dp) {
        return inviteRecordService.agreeInvite(dp);
    }

    @Override
    public ResponseBody<Boolean> updateInvite(HeroLandInviteRecordDP dp) {
        return inviteRecordService.updateInvite(dp);
    }
}
