package com.heroland.competition;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: kui.zhouk
 * @date: 2018-10-26
 */
@SpringBootApplication
//@SpringBootApplication
@ImportResource(locations = {"classpath*:context-*.xml","classpath*:cxf.xml"})
@MapperScan("com.heroland.competition.dal")
@RestController
@EnableScheduling
@EnableTransactionManagement
@EnableAsync
@EnableDubbo
@NacosPropertySource(dataId = "bqhealth_cloud_data", groupId = "bqhealth_cloud_group",autoRefreshed = true)
public class HealthCloudApplication {
    public static void main(String[] args) {
        try {
            //System.setProperty("org.quartz.properties", "");
            SpringApplication.run(HealthCloudApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/healthCheck")
    public String healthCheck(){
        try {
            return "I'm Ok! \nI'm at ";
        } catch (Exception e) {
            return "I'm OK! \nBut getLocalHost failed!";
        }
    }
}
