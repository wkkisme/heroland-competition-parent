package com.heroland.competition.mq;

import com.alibaba.fastjson.JSON;
import com.anycommon.cache.service.RedisService;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.constant.UserStatusEnum;
import com.heroland.competition.common.enums.CompetitionEnum;
import com.heroland.competition.common.enums.CompetitionResultEnum;
import com.heroland.competition.common.enums.CompetitionStatusEnum;
import com.heroland.competition.common.enums.RedisRocketmqConstant;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.OnlineDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.heroland.competition.domain.dp.HeroLandInviteRecordDP.INVITE_KEY;

/**
 * 比赛延时处理
 *
 * @author mac
 */
@Slf4j
@RocketMQMessageListener(topic = "competition-record", consumerGroup = "competition_record_consumer")
@Component
public class CompetitionRecordConsumer implements RocketMQListener<HeroLandCompetitionRecordDP> {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Resource
    private RedisService redisService;

    @Override
    public void onMessage(HeroLandCompetitionRecordDP message) {
        log.info("比赛记录监听{}", JSON.toJSONString(message));
        HeroLandCompetitionRecordQO heroLandCompetitionRecordQO = new HeroLandCompetitionRecordQO();
        heroLandCompetitionRecordQO.setInviteRecordId(message.getInviteRecordId());
        ResponseBody<HeroLandCompetitionRecordDP> competitionRecordByInviteRecordId = heroLandCompetitionRecordService.getCompetitionRecordByInviteRecordId(heroLandCompetitionRecordQO);
        HeroLandCompetitionRecordDP data = competitionRecordByInviteRecordId.getData();
        if (data != null) {
            if (data.getResult() == null) {
                String redisKey;
                if (CompetitionEnum.SYNC.getType().equals(message.getTopicType())) {
                    redisKey = data.getTopicId() + data.getQuestionId() + data.getInviteId() + data.getOpponentId() + data.getId();
                } else {
                    redisKey = data.getTopicId() + data.getInviteId() + data.getOpponentId() + data.getId();
                }

                data.setRecordId(data.getRecordId());
                data.setId(data.getId());
                // 如果超时了还是空的 要么是前一个打错，要么是都没答 平局
                data.setResult(CompetitionResultEnum.DRAW.getResult());
                data.setInviteScore(0);
                data.setOpponentScore(0);
                data.setStatus(CompetitionStatusEnum.FINISH.getStatus());
                heroLandCompetitionRecordService.updateCompetitionRecord(data);
                redisService.del(redisKey);
                redisService.del(INVITE_KEY + data.getInviteId());
                redisService.del(INVITE_KEY + data.getOpponentId());
                Object user = redisService.get("user:" + data.getInviteId());
                OnlineDP onlineUser = JSON.parseObject(user.toString(), OnlineDP.class);
                onlineUser.setUserStatus(UserStatusEnum.ONLINE.getStatus());
                redisService.set("user:" + data.getInviteId(), JSON.toJSONString(onlineUser), 1000 * 60 * 60 * 2);

                Object beUser = redisService.get("user:" + data.getOpponentId());
                OnlineDP beOnlineUser = JSON.parseObject(beUser.toString(), OnlineDP.class);
                beOnlineUser.setUserStatus(UserStatusEnum.ONLINE.getStatus());
                redisService.set("user:" + data.getOpponentId(), JSON.toJSONString(beOnlineUser), 1000 * 60 * 60 * 2);

            }
            if (data.getInviteEndTime() == null) {
                data.setSenderId(message.getInviteId());
                data.setAddresseeId(message.getOpponentId());
            } else {
                data.setSenderId(message.getOpponentId());
                data.setAddresseeId(message.getInviteId());
            }
            rocketMQTemplate.syncSend(RedisRocketmqConstant.IM_SINGLE, JSON.toJSONString(data));

        }
    }
}
