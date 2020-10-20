package com.heroland.competition.mq;

import com.alibaba.fastjson.JSON;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.constant.UserStatusEnum;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.enums.CompetitionResultEnum;
import com.heroland.competition.common.enums.InviteStatusEnum;
import com.heroland.competition.common.enums.RedisRocketmqConstant;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.dp.OnlineDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.domain.qo.HeroLandInviteRecordQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.HeroLandInviteRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.heroland.competition.domain.dp.HeroLandInviteRecordDP.INVITE_KEY;

/**
 * RobotConsumer
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "robot", consumerGroup = "robot_consumer")
public class RobotConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Resource
    private RedisService redisService;

    @Resource
    private HeroLandInviteRecordService heroLandInviteRecordService;

    @Override
    public void onMessage(MessageExt messages) {

        if (Objects.equals(messages.getTags(), "invite")) {
            String msg = new String(messages.getBody());
            HeroLandInviteRecordDP message = JSON.parseObject(msg, HeroLandInviteRecordDP.class);
            // 接收到邀请延时处理

            HeroLandInviteRecordQO qo = new HeroLandInviteRecordQO();
            qo.setRecordId(message.getRecordId());
            qo.setPageSize(1);
            qo.setInviteUserId(message.getInviteUserId());
            qo.setBeInviteUserId(message.getBeInviteUserId());
            qo.setTopicId(message.getTopicId());
            ResponseBody<List<HeroLandInviteRecordDP>> invite = heroLandInviteRecordService.getInvite(qo);
            List<HeroLandInviteRecordDP> inviteData = invite.getData();
            if (!CollectionUtils.isEmpty(inviteData)) {

                HeroLandInviteRecordDP dp = inviteData.get(0);
                if (InviteStatusEnum.WAITING.getStatus().equals(dp.getStatus())) {
                    dp.setStatusRemark("自动同意");
                    heroLandInviteRecordService.agreeInvite(dp);

                }
            }
        }else if (Objects.equals(messages.getTags(),"competition-record")){
            log.info("机器人比赛记录监听{}",JSON.toJSONString(messages));
            String msg = new String(messages.getBody());
            HeroLandCompetitionRecordDP message = JSON.parseObject(msg, HeroLandCompetitionRecordDP.class);
            log.info("机器人比赛记录监听message:{}",JSON.toJSONString(message));
            HeroLandCompetitionRecordQO heroLandCompetitionRecordQO = new HeroLandCompetitionRecordQO();
            heroLandCompetitionRecordQO.setInviteRecordId(message.getInviteRecordId());
            ResponseBody<HeroLandCompetitionRecordDP> competitionRecordByInviteRecordId = heroLandCompetitionRecordService.getCompetitionRecordByInviteRecordId(heroLandCompetitionRecordQO);
            HeroLandCompetitionRecordDP data = competitionRecordByInviteRecordId.getData();
            if (data != null) {
                if (data.getResult() == null) {
                    String redisKey;
                    if (CompetitionEnum.SYNC.getType().equals(message.getTopicType())) {
                        redisKey = data.getTopicId() + data.getQuestionId() + data.getInviteId() + data.getOpponentId() + data.getId();
                    }else {
                        redisKey = data.getTopicId()  + data.getInviteId() + data.getOpponentId() + data.getId();
                    }

                    message.setRecordId(data.getRecordId());
                    message.setId(data.getId());
                    message.setOpponentScore(0);
                    // 如果超时了还是空的 要么是前一个打错，要么是都没答 平局
                    message.setResult(CompetitionResultEnum.DRAW.getResult());
                    heroLandCompetitionRecordService.updateCompetitionRecord(message);
                    redisService.del(redisKey);
                    redisService.del(INVITE_KEY+ data.getInviteId());
                    redisService.del(INVITE_KEY+ data.getOpponentId());


                    Object user = redisService.get("user:" + data.getInviteId());
                    OnlineDP onlineUser = JSON.parseObject(user.toString(), OnlineDP.class);
                    onlineUser.setUserStatus(UserStatusEnum.ONLINE.getStatus());
                    redisService.set("user:" + data.getInviteId(), JSON.toJSONString(onlineUser), 1000 * 60 * 60 * 2);

                }
                if (data.getInviteEndTime() == null) {
                    message.setSenderId(message.getInviteId());
                    message.setAddresseeId(message.getOpponentId());
                } else {
                    message.setSenderId(message.getOpponentId());
                    message.setAddresseeId(message.getInviteId());
                }
                rocketMQTemplate.syncSend(RedisRocketmqConstant.IM_SINGLE, JSON.toJSONString(message));

            }
        }
    }
}
