package com.heroland.competition.base;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"com.heroland.competition","com.anycommon","com.alicp.jetcache","com.crossoverjie.cim"})
//@ImportResource(locations = {"classpath*:heroland-*.xml"})
@MapperScan("com.heroland.competition.dal")
@RestController
@EnableScheduling
@EnableTransactionManagement
@EnableAsync
@EnableDubbo(scanBasePackages = "com.heroland.competition")
@EnableMethodCache(basePackages = "com.heroland.competition")
@EnableCreateCacheAnnotation
public class ServiceApplicationTest {
}
