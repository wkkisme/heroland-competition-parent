package com.heroland.competition.mq;

import com.alibaba.fastjson.JSON;
import com.anycommon.cache.service.RedisService;
import com.heroland.competition.common.constant.RedisConstant;
import com.heroland.competition.domain.dp.OnlineDP;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import static com.heroland.competition.common.constant.RedisConstant.OFFLINE_TAGS;
import static com.heroland.competition.common.constant.RedisConstant.ONLINE_TAGS;

/**
 * websocket 通知上线用户
 *
 * @author mac
 */
@RocketMQMessageListener(nameServer = "${platform.rocketmq.nameServer}", topic = "IM_LINE", consumerGroup = "online_consumer")
@Component
public class NettyUserConsumer implements RocketMQListener<MessageExt> {
    @Resource
    private RedisService redisService;

    @Override
    public void onMessage(MessageExt message) {

        OnlineDP onlineMsg = JSON.parseObject(new String(message.getBody(), Charset.defaultCharset()), OnlineDP.class);
        // 用户上线，全部放redis以供查询
        String tags = message.getTags();


        if (tags.equalsIgnoreCase(ONLINE_TAGS)) {
            Set<String> recent = onlineMsg.getRecent();
            if (recent == null) {
                recent = new HashSet<>();
            }
            recent.add(onlineMsg.getAddresseeId());
            redisService.sAdd(RedisConstant.ONLINE_KEY+onlineMsg.getTopicId(), onlineMsg);
            redisService.set("user:"+onlineMsg.getSenderId(),onlineMsg);
        }else if (tags.equalsIgnoreCase(OFFLINE_TAGS)){
            redisService.sRemove(RedisConstant.ONLINE_KEY+onlineMsg.getTopicId(), onlineMsg);
            redisService.del("user:"+onlineMsg.getSenderId());
        }

    }


}