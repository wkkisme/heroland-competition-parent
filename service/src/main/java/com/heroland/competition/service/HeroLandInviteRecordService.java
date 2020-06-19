package com.heroland.competition.service;


import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;

/**
 * heroland-competition-parent
 *  比赛邀请记录
 * @author wangkai
 * @date 2020/6/19
 */

public interface HeroLandInviteRecordService {

    /**
     * 邀请
     * @param dp
     * @return
     */
    ResponseBody<Boolean> invite(HeroLandInviteRecordDP dp);


    /**
     * 拒绝邀请
     * @param dp
     * @return
     */
    ResponseBody<Boolean> cancelInvite(HeroLandInviteRecordDP dp);

    /**
     * 查询邀请
     * @param dp
     * @return
     */
    ResponseBody<Boolean> getInvite(HeroLandInviteRecordDP dp);


}
