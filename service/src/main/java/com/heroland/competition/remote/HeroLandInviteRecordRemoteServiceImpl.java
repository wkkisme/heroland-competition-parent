package com.heroland.competition.remote;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.api.HeroLandInviteRecordRemoteService;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.qo.HeroLandInviteRecordQO;

import java.util.List;

/**
 * @author wushuaiping
 * @date 2020/7/13 6:16 下午
 */
public class HeroLandInviteRecordRemoteServiceImpl implements HeroLandInviteRecordRemoteService {
    @Override
    public ResponseBody<Boolean> addInvite(HeroLandInviteRecordDP dp) {
        return null;
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
        return null;
    }

    @Override
    public ResponseBody<Boolean> agreeInvite(HeroLandInviteRecordDP dp) {
        return null;
    }

    @Override
    public ResponseBody<Boolean> updateInvite(HeroLandInviteRecordDP dp) {
        return null;
    }
}
