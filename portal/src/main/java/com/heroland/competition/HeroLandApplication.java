package com.heroland.competition;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangkai
 */
@SpringBootApplication(scanBasePackages = {"com.heroland.competition","com.anycommon","com.alicp.jetcache","com.crossoverjie.cim"})
//@ImportResource(locations = {"classpath*:heroland-*.xml"})
@MapperScan("com.heroland.competition.dal")
@RestController
@EnableScheduling
@EnableTransactionManagement
@EnableAsync
@EnableDubbo(scanBasePackages = "com.heroland.competition")
@NacosPropertySource(dataId = "hero_data", groupId = "hero_group", autoRefreshed = true)
@EnableMethodCache(basePackages = "com.heroland.competition")
@EnableCreateCacheAnnotation
public class HeroLandApplication {

    private final Logger logger = LoggerFactory.getLogger(HeroLandApplication.class);

    @Value("${socket.netty.port:19999}")
    private int nettyPort;


    public static void main(String[] args) {
        try {
            //System.setProperty("org.quartz.properties", "");
            SpringApplication.run(HeroLandApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/healthCheck")
    public String healthCheck() {
        try {
            return "I'm Ok! \nI'm at ";
        } catch (Exception e) {
            return "I'm OK! \nBut getLocalHost failed!";
        }
    }
}
