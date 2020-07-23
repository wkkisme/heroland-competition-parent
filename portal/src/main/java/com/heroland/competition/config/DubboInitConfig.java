package com.heroland.competition.config;

import com.platfrom.payment.api.PrePayRemoteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboInitConfig {

    @DubboReference(group = "heroland-competition",version = "1.0.0")
    private PrePayRemoteService prePayRemoteService;

    @Bean
    public PrePayRemoteService getPrePayRemoteService(){
        return prePayRemoteService;
    }


}
