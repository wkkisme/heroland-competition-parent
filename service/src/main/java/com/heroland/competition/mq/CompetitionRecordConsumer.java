package com.heroland.competition.mq;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.common.enums.CompetitionResultEnum;
import com.heroland.competition.common.enums.RedisRocketmqConstant;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.qo.HeroLandCompetitionRecordQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 比赛延时处理
 *
 * @author mac
 */
@RocketMQMessageListener(topic = "competition-record", consumerGroup = "competition_record_consumer")
@Component
public class CompetitionRecordConsumer implements RocketMQListener<HeroLandCompetitionRecordDP> {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Override
    public void onMessage(HeroLandCompetitionRecordDP message) {
        HeroLandCompetitionRecordQO heroLandCompetitionRecordQO = new HeroLandCompetitionRecordQO();
        heroLandCompetitionRecordQO.setInviteRecordId(message.getInviteRecordId());
        ResponseBody<HeroLandCompetitionRecordDP> competitionRecordByInviteRecordId = heroLandCompetitionRecordService.getCompetitionRecordByInviteRecordId(heroLandCompetitionRecordQO);
        HeroLandCompetitionRecordDP data = competitionRecordByInviteRecordId.getData();
        if (data != null) {
            if (data.getResult() == null) {
                message.setRecordId(data.getRecordId());
                message.setId(data.getId());
                // 如果超时了还是空的 要么是前一个打错，要么是都没答 平局
                message.setResult(CompetitionResultEnum.DRAW.getResult());
                heroLandCompetitionRecordService.updateCompetitionRecord(message);
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
