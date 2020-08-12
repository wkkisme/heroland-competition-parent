package com.heroland.competition.mq;

import com.alibaba.fastjson.JSON;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.service.HeroLandAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户注册consumer
 * @author wangkai
 */
@Component
@Slf4j
@RocketMQMessageListener( topic = "${platform.register.topic}", consumerGroup = "competition_consumer")
public class RegisterUserConsumer  implements RocketMQListener<String> {
    @Resource
    private HeroLandAccountService heroLandAccountService;

    @Override
    public void onMessage(String s) {
        try {
            heroLandAccountService.saveAccount(JSON.parseObject(s, HeroLandAccountDP.class));
        } catch (Exception e) {
            log.error("e",e);
        }
    }
}
