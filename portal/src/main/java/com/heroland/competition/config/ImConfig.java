package com.heroland.competition.config;

import com.crossoverjie.cim.server.api.ImMessageService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImConfig {

    @DubboReference(group = "cim-server",version = "1.0.0",cluster = "singlecluster",check = false)
    private ImMessageService singleMessageService;

    @DubboReference(group = "cim-server",version = "1.0.0",cluster = "broadcastcluster",check = false)
    private ImMessageService allMessageService;

    @Bean(name = "singleMessageService")
    public ImMessageService getImServerService() {
        return singleMessageService;
    }

    @Bean(name = "allMessageService")
    public ImMessageService getAllMessageService() {
        return allMessageService;
    }
}
