package com.heroland.competition.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.common.UserStatusDP;
import com.anycommon.response.constant.UserStatusEnum;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.crossoverjie.cim.route.api.RouteApi;
import com.google.common.collect.Lists;
import com.heroland.competition.common.enums.CommandResType;
import com.heroland.competition.common.enums.InviteStatusEnum;
import com.heroland.competition.dal.mapper.HeroLandInviteRecordExtMapper;
import com.heroland.competition.dal.pojo.HeroLandInviteRecord;
import com.heroland.competition.dal.pojo.HeroLandInviteRecordExample;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.dto.HeroLandQuestionListForTopicDto;
import com.heroland.competition.domain.qo.HeroLandInviteRecordQO;
import com.heroland.competition.domain.request.HeroLandTopicQuestionsPageRequest;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.HeroLandInviteRecordService;
import com.heroland.competition.service.HeroLandQuestionService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        ResponseBody<String> responseBody = addInvite(dp.inviteCheck(redisService));
        // 邀请放到延时队列中
        /*
        delayTimeLevel  默认延迟等级 : 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h，
        传入1代表1s, 2代表5s, 以此类推
         */
        rocketMQTemplate.sendAndReceive("competition-invite", dp,
                new TypeReference<HeroLandInviteRecordDP>() {
                }.getType(), 300, 7);

        // 发送消息给websocket去通知 发给所有在线人，和发给对方；

        UserStatusDP userStatusDP = new UserStatusDP();
        userStatusDP.setUserId(dp.getInviteUserId());
        userStatusDP.setSenderId(dp.getInviteUserId());
        userStatusDP.setAddresseeId(dp.getBeInviteUserId());
        userStatusDP.setStatus(UserStatusEnum.CANT_BE_INVITE.getStatus());
        userStatusDP.setTopicId(dp.getTopicId());
        // 广播所有人和发给对手
        dp.setSenderId(dp.getInviteUserId());
        dp.setAddresseeId(dp.getBeInviteUserId());
        dp.setType(CommandResType.BE_INVITE.getCode());
        try {
            rocketMQTemplate.syncSend("IM_LINE:SINGLE",JSON.toJSONString(dp));
            rocketMQTemplate.syncSend("IM_LINE:CLUSTER",userStatusDP.toJSONString());
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
        // 广播所有人和发给对手
        dp.setType(CommandResType.INVITE_CANCEL.getCode());

        dp.setSenderId(dp.getBeInviteUserId());
        dp.setAddresseeId(dp.getInviteUserId());
        try {
            rocketMQTemplate.syncSend("IM_LINE:SINGLE",JSON.toJSONString(dp));
            rocketMQTemplate.syncSend("IM_LINE:CLUSTER",userStatusDP.toJSONString());
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
            if(qo.getNeedPage()) {
                example.setOrderByClause("gmt_create desc limit " +qo.getStartRow() +","+qo.getPageSize() );
            }else {
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
    public ResponseBody<Boolean> agreeInvite(HeroLandInviteRecordDP dp) {
        dp.setStatus(InviteStatusEnum.AGREE.getStatus());
        //  需要提前将比赛记录初始化进去
        HeroLandCompetitionRecordDP heroLandCompetitionRecordDP = new HeroLandCompetitionRecordDP();
        try {
            BeanUtil.insertConversion(dp, heroLandCompetitionRecordDP);
            heroLandCompetitionRecordDP.setInviteId(dp.getInviteUserId());
            heroLandCompetitionRecordDP.setOpponentId(dp.getBeInviteUserId());
            heroLandCompetitionRecordService.addCompetitionRecord(heroLandCompetitionRecordDP);
            // 发送消息给websocket去通知 发给所有在线人，和发给对方；

            UserStatusDP userStatusDP = new UserStatusDP();
            userStatusDP.setUserId(dp.getInviteUserId());
            userStatusDP.setSenderId(dp.getInviteUserId());
            userStatusDP.setAddresseeId(dp.getBeInviteUserId());
            userStatusDP.setUserStatus(UserStatusEnum.CANT_BE_INVITE.getStatus());
            userStatusDP.setTopicId(dp.getTopicId());
            // 广播所有人和发给对手
            // TODO 先判断是否在线
            dp.setType(CommandResType.INVITE_AGREE.getCode());
            dp.setSenderId(dp.getBeInviteUserId());
            dp.setAddresseeId(dp.getInviteUserId());
            try {
                rocketMQTemplate.syncSend("IM_LINE:SINGLE",JSON.toJSONString(dp));
                rocketMQTemplate.syncSend("IM_LINE:CLUSTER",userStatusDP.toJSONString());
            } catch (Exception ignored) {
            }
            return updateInvite(dp);
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

        if (!CollectionUtils.isEmpty(data)){
            HeroLandInviteRecordDP heroLandInviteRecordDP = data.get(0);
            if (Math.abs(heroLandInviteRecordDP.getGmtCreate().getTime() - System.currentTimeMillis()) > 120000){
                return ResponseBodyWrapper.success();
            }
        }
        return ResponseBodyWrapper.successWrapper(data.get(0));
    }


}
