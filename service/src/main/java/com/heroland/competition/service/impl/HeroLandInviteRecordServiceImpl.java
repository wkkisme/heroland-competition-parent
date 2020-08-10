package com.heroland.competition.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.MybatisCriteriaConditionUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.crossoverjie.cim.route.api.RouteApi;
import com.google.common.collect.Lists;
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
                }.getType(), 30000, 7);

        // 发送消息给websocket去通知 1 发送消息出去，2 回流消息
        Object o = routeApi.sendMsg(dp, JSON.toJSONString(dp.getCurrentUser()));

        return responseBody;
    }

    @Override
    public ResponseBody<Boolean> cancelInvite(HeroLandInviteRecordDP dp) {
        dp.setStatus(InviteStatusEnum.DO_NOT_AGREE.getStatus());
        if (updateInvite(dp.updateCheck()).isSuccess()) {
            dp.finishBeInviteUserCompetition(redisService);
            dp.finishInviteUserCompetition(redisService);
        }

        // todo 发消息
        Object o = routeApi.sendMsg(dp, JSON.toJSONString(dp.getCurrentUser()));
        return ResponseBodyWrapper.success();
    }

    @Override
    public ResponseBody<List<HeroLandInviteRecordDP>> getInvite(HeroLandInviteRecordQO qo) {
        List<HeroLandInviteRecord> heroLandQuestions = Lists.newArrayList();
        long count = 0L;
        try {
            HeroLandInviteRecordExample example = new HeroLandInviteRecordExample();
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
            // 发消息
            Object o = routeApi.sendMsg(dp, JSON.toJSONString(dp.getCurrentUser()));
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
        qo.setInviteUserId(heroLandInviteRecord.getInviteUserId());
        qo.setStatus(InviteStatusEnum.WAITING.getStatus());
        qo.setPageSize(1);
        ResponseBody<List<HeroLandInviteRecordDP>> invite = getInvite(qo);
        if (CollectionUtils.isEmpty(invite.getData())) {
            qo.setInviteUserId(null);
            qo.setBeInviteUserId(heroLandInviteRecord.getBeInviteUserId());
            invite = getInvite(qo);
        }
        return invite == null ? null : ResponseBodyWrapper.successWrapper(invite.getData().get(0));
    }


}
