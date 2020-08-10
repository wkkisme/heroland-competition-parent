package com.heroland.competition.mq;

import com.alibaba.fastjson.JSON;
import com.anycommon.cache.service.RedisService;
import com.crossoverjie.cim.route.api.RouteApi;
import com.heroland.competition.common.constant.RedisConstant;
import com.heroland.competition.domain.dp.OnlineDP;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
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
@RocketMQMessageListener(nameServer = "${platform.rocketmq.nameServer}", topic = "IM_LINE" +
        "" +
        "" +
        "", consumerGroup = "online_consumer")
@Component
@Slf4j
public class NettyUserConsumer implements RocketMQListener<MessageExt> {
    @Resource
    private RedisService redisService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private RouteApi routeApi;
    @Override
    public void onMessage(MessageExt message) {
    log.info("message{}",JSON.toJSONString(message));

        OnlineDP onlineMsg = JSON.parseObject(new String(message.getBody(), Charset.defaultCharset()), OnlineDP.class);
        log.info("onlineMsg{}",JSON.toJSONString(onlineMsg));
        // 用户上线，全部放redis以供查询
        String tags = message.getTags();


        if (tags.equalsIgnoreCase(ONLINE_TAGS)) {
            log.info("用户online{}",JSON.toJSONString(onlineMsg));
            Set<String> recent = onlineMsg.getRecent();
            if (recent == null) {
                recent = new HashSet<>();
            }
            recent.add(onlineMsg.getAddresseeId());
            redisService.sAdd(RedisConstant.ONLINE_KEY+onlineMsg.getTopicId(),1000*60*60*2, onlineMsg.getSenderId());
            redisService.set("user:"+onlineMsg.getSenderId(),JSON.toJSONString(onlineMsg),1000*60*60*2);
        }else if (tags.equalsIgnoreCase(OFFLINE_TAGS)){
            log.info("用户offline{}",JSON.toJSONString(onlineMsg));
            redisService.sRemove(RedisConstant.ONLINE_KEY+onlineMsg.getTopicId(), onlineMsg.getSenderId());
            redisService.del("user:"+onlineMsg.getSenderId());
            // 通知所有人
            rocketMQTemplate.sendAndReceive("IM_LINE:CLUSTER",JSON.toJSONString(onlineMsg),String.class);

        }

    }


}
