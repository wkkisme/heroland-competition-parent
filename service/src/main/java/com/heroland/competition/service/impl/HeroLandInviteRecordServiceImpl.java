package com.heroland.competition.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.common.UserStatusDP;
import com.anycommon.response.constant.UserStatusEnum;
import com.anycommon.response.expception.AppSystemException;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.crossoverjie.cim.route.api.RouteApi;
import com.google.common.collect.Lists;
import com.heroland.competition.common.enums.CommandResType;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.enums.CompetitionStatusEnum;
import com.heroland.competition.common.enums.InviteStatusEnum;
import com.heroland.competition.dal.mapper.HeroLandInviteRecordExtMapper;
import com.heroland.competition.dal.pojo.HeroLandInviteRecord;
import com.heroland.competition.dal.pojo.HeroLandInviteRecordExample;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.dp.OnlineDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.domain.qo.HeroLandInviteRecordQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.HeroLandInviteRecordService;
import com.heroland.competition.service.HeroLandQuestionService;
import com.platform.sso.domain.dp.PlatformSysUserDP;
import com.platform.sso.domain.qo.PlatformSysUserQO;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platform.sso.facade.result.RpcResult;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * heroland-competition-parent
 *
 * @author wangkai
 * @date 2020/6/19
 */

@Service
public class HeroLandInviteRecordServiceImpl implements HeroLandInviteRecordService {
    @Resource
    private RedisService redisService;

    private final Logger logger = LoggerFactory.getLogger(HeroLandInviteRecordServiceImpl.class);
    @Resource
    private HeroLandInviteRecordExtMapper heroLandInviteRecordExtMapper;

    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Resource
    private HeroLandQuestionService heroLandQuestionService;

    @Resource
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;
    @Resource(name = "rocketMQTemplate")
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private RouteApi routeApi;

    @Override
    public ResponseBody<String> addInvite(HeroLandInviteRecordDP dp) {
        logger.info("邀请记录：{}", JSON.toJSONString(dp));
        HeroLandInviteRecord heroLandInviteRecord = null;
        try {
            heroLandInviteRecord = BeanUtil.insertConversion(dp.addCheck(), new HeroLandInviteRecord());
            heroLandInviteRecordExtMapper.insert(heroLandInviteRecord);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.successWrapper(heroLandInviteRecord.getRecordId());
    }

    @Override
    public ResponseBody<String> invite(HeroLandInviteRecordDP dp) {
        ResponseBody<String> responseBody = null;
        try {

            responseBody = addInvite(dp.inviteCheck(redisService));


        } catch (AppSystemException e) {
            return ResponseBodyWrapper.fail(e.getMessage(), "40002");
        }
        // 邀请放到延时队列中
        /*
        delayTimeLevel  默认延迟等级 : 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h，
        传入1代表1s, 2代表5s, 以此类推
         */
        try {
            if (dp.getInviteRobot()) {
                agreeInvite(dp);
            }
            rocketMQTemplate.sendAndReceive("competition-invite", dp,
                    new TypeReference<HeroLandInviteRecordDP>() {
                    }.getType(), 300, 7);

        } catch (Exception ignored) {
        }

        // 发送消息给websocket去通知 发给所有在线人，和发给对方；

        UserStatusDP inviteUser = new UserStatusDP();
        inviteUser.setUserId(dp.getInviteUserId());
        inviteUser.setSenderId(dp.getInviteUserId());
        inviteUser.setAddresseeId(dp.getBeInviteUserId());
        inviteUser.setUserStatus(UserStatusEnum.CANT_BE_INVITE.getStatus());
        inviteUser.setTopicId(dp.getTopicId());
        inviteUser.setType(CommandResType.ONLINE_SUCCESS_REFRESH.getCode());
        // 广播所有人和发给对手
        dp.setSenderId(dp.getInviteUserId());
        dp.setAddresseeId(dp.getBeInviteUserId());
        dp.setType(CommandResType.BE_INVITE.getCode());
        try {
            Object user = redisService.get("user:" + dp.getInviteUserId());
            OnlineDP onlineUser = JSON.parseObject(user.toString(), OnlineDP.class);
            onlineUser.setUserStatus(UserStatusEnum.CANT_BE_INVITE.getStatus());
            redisService.set("user:" + dp.getInviteUserId(), JSON.toJSONString(onlineUser), 1000 * 60 * 60 * 2);


            Object beUser = redisService.get("user:" + dp.getBeInviteUserId());
            OnlineDP beOnlineUser = JSON.parseObject(beUser.toString(), OnlineDP.class);
            beOnlineUser.setUserStatus(UserStatusEnum.CANT_BE_INVITE.getStatus());
            redisService.set("user:" + dp.getBeInviteUserId(), JSON.toJSONString(beOnlineUser), 1000 * 60 * 60 * 2);

            try {
                rocketMQTemplate.syncSend("IM_LINE:SINGLE", JSON.toJSONString(dp));
            } catch (Exception ignored) {
            }
            try {
                rocketMQTemplate.syncSend("IM_LINE:CLUSTER", inviteUser.toJSONString());
            } catch (Exception ignored) {
            }
            inviteUser.setUserId(dp.getBeInviteUserId());
            inviteUser.setSenderId(dp.getBeInviteUserId());
            rocketMQTemplate.syncSend("IM_LINE:CLUSTER", inviteUser.toJSONString());
        } catch (Exception ignored) {
        }

        return responseBody;
    }

    @Override
    public ResponseBody<Boolean> cancelInvite(HeroLandInviteRecordDP dp) {
        dp.setStatus(InviteStatusEnum.DO_NOT_AGREE.getStatus());
        if (updateInvite(dp.updateCheck()).isSuccess()) {
            dp.finishBeInviteUserCompetition(redisService);
            dp.finishInviteUserCompetition(redisService);
        }

        // 发送消息给websocket去通知 发给所有在线人，和发给对方；

        UserStatusDP userStatusDP = new UserStatusDP();
        userStatusDP.setUserId(dp.getInviteUserId());
        userStatusDP.setSenderId(dp.getInviteUserId());
        userStatusDP.setAddresseeId(dp.getBeInviteUserId());
        userStatusDP.setUserStatus(UserStatusEnum.ONLINE.getStatus());
        userStatusDP.setTopicId(dp.getTopicId());
        userStatusDP.setType(CommandResType.ONLINE_SUCCESS_REFRESH.getCode());
        // 广播所有人和发给对手
        dp.setType(CommandResType.INVITE_CANCEL.getCode());

        dp.setSenderId(dp.getBeInviteUserId());
        dp.setAddresseeId(dp.getInviteUserId());
        try {
            Object user = redisService.get("user:" + dp.getInviteUserId());
            OnlineDP onlineUser = JSON.parseObject(user.toString(), OnlineDP.class);
            onlineUser.setUserStatus(UserStatusEnum.ONLINE.getStatus());
            redisService.set("user:" + dp.getInviteUserId(), JSON.toJSONString(onlineUser), 1000 * 60 * 60 * 2);


            Object beUser = redisService.get("user:" + dp.getBeInviteUserId());
            OnlineDP beOnlineUser = JSON.parseObject(beUser.toString(), OnlineDP.class);
            beOnlineUser.setUserStatus(UserStatusEnum.ONLINE.getStatus());
            redisService.set("user:" + dp.getBeInviteUserId(), JSON.toJSONString(beOnlineUser), 1000 * 60 * 60 * 2);

            try {
                rocketMQTemplate.syncSend("IM_LINE:SINGLE", JSON.toJSONString(dp));
            } catch (Exception ignored) {
            }
            try {
                rocketMQTemplate.syncSend("IM_LINE:CLUSTER", userStatusDP.toJSONString());
            } catch (Exception ignored) {

            }
            userStatusDP.setUserId(dp.getBeInviteUserId());
            userStatusDP.setSenderId(dp.getBeInviteUserId());
            rocketMQTemplate.syncSend("IM_LINE:CLUSTER", userStatusDP.toJSONString());
        } catch (Exception ignored) {

        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<List<HeroLandInviteRecordDP>> getInvite(HeroLandInviteRecordQO qo) {
        List<HeroLandInviteRecord> heroLandQuestions = Lists.newArrayList();
        long count = 0L;
        try {
            HeroLandInviteRecordExample example = new HeroLandInviteRecordExample();
            if (qo.getNeedPage()) {
                example.setOrderByClause("gmt_create desc limit " + qo.getStartRow() + "," + qo.getPageSize());
            } else {
                example.setOrderByClause("gmt_create desc");
            }
            HeroLandInviteRecordExample.Criteria criteria = example.createCriteria();

            MybatisCriteriaConditionUtil.createExample(criteria, qo);
            heroLandQuestions = heroLandInviteRecordExtMapper.selectByExample(example);
            count = heroLandInviteRecordExtMapper.countByExample(example);
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }

        return ResponseBodyWrapper.successListWrapper(heroLandQuestions, count, qo, HeroLandInviteRecordDP.class);
    }

    @Override
    public ResponseBody<String> agreeInvite(HeroLandInviteRecordDP dp) {
        dp.setStatus(InviteStatusEnum.AGREE.getStatus());
        //  需要提前将比赛记录初始化进去
        HeroLandCompetitionRecordDP heroLandCompetitionRecordDP = new HeroLandCompetitionRecordDP();
        try {
            BeanUtil.insertConversion(dp, heroLandCompetitionRecordDP);
            heroLandCompetitionRecordDP.setInviteId(dp.getInviteUserId());
            heroLandCompetitionRecordDP.setOpponentId(dp.getBeInviteUserId());
            heroLandCompetitionRecordDP.setInviteStartTime(new Date());
            heroLandCompetitionRecordDP.setOpponentStartTime(heroLandCompetitionRecordDP.getInviteStartTime());
            heroLandCompetitionRecordDP.setStatus(CompetitionStatusEnum.COMPETING.getStatus());
            heroLandCompetitionRecordDP.setInviteRecordId(dp.getRecordId());
            heroLandCompetitionRecordDP.setOpponentLevel(dp.getOpponentLevel());
            heroLandCompetitionRecordDP.setInviteLevel(dp.getInviteLevel());
            heroLandCompetitionRecordDP.setSubjectCode(dp.getSubjectCode());
            ResponseBody<String> stringResponseBody = heroLandCompetitionRecordService.addCompetitionRecord(heroLandCompetitionRecordDP);
            logger.info("recordid -------》：{}", dp.getRecordId());
            redisService.set("competition-record:" + dp.getRecordId(), heroLandCompetitionRecordDP, 180000);
            // 发送消息给websocket去通知 发给所有在线人，和发给对方；

            /*
             *  delayTimeLevel  默认延迟等级 : 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h，
             *         传入1代表1s, 2代表5s, 以此类推
             */
            // 广播所有人和发给对手
            dp.setType(CommandResType.INVITE_AGREE.getCode());
            dp.setSenderId(dp.getBeInviteUserId());
            dp.setAddresseeId(dp.getInviteUserId());
            updateInvite(dp);
            try {
                if (CompetitionEnum.SYNC.getType().equals(dp.getTopicType())) {
                    try {
                        rocketMQTemplate.sendAndReceive("competition-record", heroLandCompetitionRecordDP,
                                new TypeReference<HeroLandCompetitionRecordDP>() {
                                }.getType(), 300, 7);
                    } catch (Exception ignored) {

                    }

                    rocketMQTemplate.sendAndReceive("robot:competition-record", JSON.toJSONString(heroLandCompetitionRecordDP), new TypeReference<String>() {
                    }.getType(), 300, 5);
                } else {
                    rocketMQTemplate.sendAndReceive("robot:competition-record", JSON.toJSONString(heroLandCompetitionRecordDP), new TypeReference<String>() {
                    }.getType(), 300, 14);
                }
            } catch (Exception ignored) {
            }
            try {
                // 最近游戏的人

                redisService.sAdd("recent_user:" + dp.getTopicId() + dp.getInviteUserId(), dp.getBeInviteUserId());
                redisService.sAdd("recent_user:" + dp.getTopicId() + dp.getBeInviteUserId(), dp.getInviteUserId());
                if (!dp.getInviteRobot()) {
                    rocketMQTemplate.syncSend("IM_LINE:SINGLE", JSON.toJSONString(dp));
                }

            } catch (Exception ignored) {
            }
            return ResponseBodyWrapper.successWrapper(dp.getRecordId());
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }


        return ResponseBodyWrapper.success();

    }

    @Override
    public ResponseBody<Boolean> updateInvite(HeroLandInviteRecordDP dp) {

        try {

            heroLandInviteRecordExtMapper.updateByRecordIdSelective(BeanUtil.updateConversion(dp.updateCheck(), new HeroLandInviteRecord()));
        } catch (Exception e) {
            logger.error("", e);
            ResponseBodyWrapper.failSysException();
        }
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<HeroLandInviteRecordDP> getCurrentInvitingRecord(HeroLandInviteRecordQO heroLandInviteRecord) {

        String userId = heroLandInviteRecord.getUserId();
        HeroLandInviteRecordQO qo = new HeroLandInviteRecordQO();
        qo.setBeInviteUserId(null);
        qo.setInviteUserId(heroLandInviteRecord.getInviteUserId());
        qo.setPageSize(1);
        ResponseBody<List<HeroLandInviteRecordDP>> invite = getInvite(qo);
        if (CollectionUtils.isEmpty(invite.getData())) {
            qo.setBeInviteUserId(heroLandInviteRecord.getInviteUserId());
            qo.setInviteUserId(null);
            invite = getInvite(qo);
        }
        List<HeroLandInviteRecordDP> data = invite.getData();

        if (CollectionUtils.isEmpty(data)) {
            return ResponseBodyWrapper.success();
        }


        HeroLandInviteRecordDP heroLandInviteRecordDP = data.get(0);
        if (redisService.get("inviting:" + heroLandInviteRecord.getUserId() + heroLandInviteRecordDP.getRecordId()) != null) {
            return ResponseBodyWrapper.success();
        }
        if (Math.abs(heroLandInviteRecordDP.getGmtCreate().getTime() - System.currentTimeMillis()) > 180000) {
            return ResponseBodyWrapper.success();
        }

        HeroLandCompetitionRecordQO heroLandCompetitionRecordQO = new HeroLandCompetitionRecordQO();
        heroLandCompetitionRecordQO.setInviteRecordId(heroLandInviteRecordDP.getRecordId());
        ResponseBody<HeroLandCompetitionRecordDP> competitionRecordByInviteRecordId = heroLandCompetitionRecordService.getCompetitionRecordByInviteRecordId(heroLandCompetitionRecordQO);

        HeroLandCompetitionRecordDP data1 = competitionRecordByInviteRecordId.getData();
        if (data1 == null) {
            return ResponseBodyWrapper.success();
        }

        if (CompetitionStatusEnum.FINISH.getStatus().equals(data1.getStatus())) {
            return ResponseBodyWrapper.success();
        }
        if (data1.getInviteEndTime() != null && data1.getOpponentEndTime() != null) {
            return ResponseBodyWrapper.success();
        }

        if (data1.getInviteEndTime() == null && !data1.getInviteId().equals(userId) && data1.getOpponentEndTime() != null) {
            return ResponseBodyWrapper.success();
        }

        if (data1.getOpponentEndTime() == null && !data1.getOpponentId().equals(userId) && data1.getInviteEndTime() != null) {
            return ResponseBodyWrapper.success();
        }

        if (data1.getInviteEndTime() == null && data1.getInviteId().equals(userId)) {
            return ResponseBodyWrapper.successWrapper(heroLandInviteRecordDP);
        }

        if (data1.getOpponentEndTime() == null && data1.getOpponentId().equals(userId)) {
            return ResponseBodyWrapper.successWrapper(heroLandInviteRecordDP);
        }
        PlatformSysUserQO platformSysUserQO = new PlatformSysUserQO();
        platformSysUserQO.setUserId(heroLandInviteRecordDP.getBeInviteUserId());
        RpcResult<List<PlatformSysUserDP>> listRpcResult = platformSsoUserServiceFacade.queryUserList(platformSysUserQO);
        if (listRpcResult == null || CollectionUtils.isEmpty(listRpcResult.getData())) {
            return ResponseBodyWrapper.success();
        }

        return ResponseBodyWrapper.successWrapper(heroLandInviteRecordDP);
    }


}
