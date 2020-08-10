package com.heroland.competition.service;


import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.qo.HeroLandInviteRecordQO;

import java.util.List;

/**
 * heroland-competition-parent
 *  比赛邀请记录
 * @author wangkai
 * @date 2020/6/19
 */

public interface HeroLandInviteRecordService {

    /**
     * 新增邀请
     * @param dp
     * @return
     */
    ResponseBody<String> addInvite(HeroLandInviteRecordDP dp);

    /**
     * 新增邀请
     * @param dp
     * @return
     */
    ResponseBody<String> invite(HeroLandInviteRecordDP dp);


    /**
     * 拒绝邀请
     * @param dp
     * @return
     */
    ResponseBody<Boolean> cancelInvite(HeroLandInviteRecordDP dp);

    /**
     * 查询邀请
     * @param qo
     * @return
     */
    ResponseBody<List<HeroLandInviteRecordDP>> getInvite(HeroLandInviteRecordQO qo);


    /**
     * 同意邀请
     * @param dp
     * @return
     */
    ResponseBody<Boolean> agreeInvite(HeroLandInviteRecordDP dp);



    /**
     * 更新邀请
     * @param dp
     * @return
     */
    ResponseBody<Boolean> updateInvite(HeroLandInviteRecordDP dp);


    /**
     * 查询当前人是否有正在邀请中的记录
     * @param heroLandInviteRecord 查询当前人是否有正在邀请中的记录
     * @return
     */
    ResponseBody<HeroLandInviteRecordDP> getCurrentInvitingRecord(HeroLandInviteRecordQO heroLandInviteRecord);
}
