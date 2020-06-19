package com.heroland.competition;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
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

/**
 * @author wangkai
 */
@SpringBootApplication
//@SpringBootApplication
//@ImportResource(locations = {"classpath*:context-*.xml", "classpath*:cxf.xml"})
@MapperScan("com.heroland.competition.dal")
@RestController
@EnableScheduling
@EnableTransactionManagement
@EnableAsync
@EnableDubbo
//@NacosPropertySource(dataId = "hero_data", groupId = "hero_group", autoRefreshed = true)
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
