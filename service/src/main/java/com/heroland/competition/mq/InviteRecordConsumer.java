package com.heroland.competition.mq;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.common.enums.InviteStatusEnum;
import com.heroland.competition.domain.dp.HeroLandInviteRecordDP;
import com.heroland.competition.domain.qo.HeroLandInviteRecordQO;
import com.heroland.competition.service.HeroLandInviteRecordService;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 邀请记录超时处理
 * @author mac
 */
@RocketMQMessageListener(topic = "competition-invite", consumerGroup = "competition_invite_consumer")
@Component
public class InviteRecordConsumer  implements RocketMQListener<HeroLandInviteRecordDP> {

    @Resource
    private HeroLandInviteRecordService heroLandInviteRecordService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void onMessage(HeroLandInviteRecordDP message) {
        // 接收到邀请延时处理
        HeroLandInviteRecordQO qo = new HeroLandInviteRecordQO();
        qo.setRecordId(message.getRecordId());
        qo.setPageSize(1);
        qo.setInviteUserId(message.getInviteUserId());
        qo.setBeInviteUserId(message.getBeInviteUserId());
        qo.setTopicId(message.getTopicId());
        ResponseBody<List<HeroLandInviteRecordDP>> invite = heroLandInviteRecordService.getInvite(qo);
        List<HeroLandInviteRecordDP> inviteData = invite.getData();
        if (!CollectionUtils.isEmpty(inviteData)){

            HeroLandInviteRecordDP dp = inviteData.get(0);
            if (StringUtils.isBlank(dp.getSubjectCode())) {
                dp.setSubjectCode(message.getSubjectCode());
            }
            if (InviteStatusEnum.WAITING.getStatus().equals(dp.getStatus())){
                dp.setStatusRemark("自动同意");
                heroLandInviteRecordService.agreeInvite(dp);
            }
        }
    }
}
