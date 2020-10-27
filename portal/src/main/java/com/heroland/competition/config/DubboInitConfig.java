package com.heroland.competition.config;

import com.crossoverjie.cim.route.api.RouteApi;
import com.platform.sso.facade.PlatformSsoAclServiceFacade;
import com.platform.sso.facade.PlatformSsoServiceFacade;
import com.platform.sso.facade.PlatformSsoUserClassServiceFacade;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platfrom.payment.api.PayQueryRemoteService;
import com.platfrom.payment.api.PrePayRemoteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboInitConfig {

    @DubboReference(group = "heroland-competition",version = "1.0.0",check = false)
    private PrePayRemoteService prePayRemoteService;

    @DubboReference(group = "heroland-competition",version = "1.0.0",check = false)
    private PayQueryRemoteService payQueryRemoteService;


    @DubboReference(group = "platform-sso",version = "1.0.0",check = false)
    private PlatformSsoServiceFacade platformSsoServiceFacade;

    @DubboReference(group = "platform-sso",version = "1.0.0",check = false)
    private PlatformSsoUserClassServiceFacade platformSsoUserClassServiceFacade;


    @DubboReference(group = "platform-sso",version = "1.0.0",check = false)
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;


    @DubboReference(group = "cim-server",version = "1.0.0",check = false)
    private RouteApi routeApi;

    @DubboReference(group = "platform-sso",version = "1.0.0",check = false)
    private PlatformSsoAclServiceFacade platformSsoAclServiceFacade;

    @Bean("platformSsoAclServiceFacade")
    public PlatformSsoAclServiceFacade getPlatformSsoAclServiceFacade() {
        return platformSsoAclServiceFacade;
    }

    @Bean("prePayRemoteService")
    public PrePayRemoteService getPrePayRemoteService(){
        return prePayRemoteService;
    }

    @Bean("payQueryRemoteService")
    public PayQueryRemoteService getPayQueryRemoteService(){
        return payQueryRemoteService;
    }


    @Bean("platformSsoServiceFacade")
    public PlatformSsoServiceFacade getPlatformSsoServiceFacade() {
        return platformSsoServiceFacade;
    }


    @Bean("platformSsoUserClassServiceFacade")
    public PlatformSsoUserClassServiceFacade getPlatformSsoUserClassServiceFacade() {
        return platformSsoUserClassServiceFacade;
    }

    @Bean("platformSsoUserServiceFacade")
    public PlatformSsoUserServiceFacade getPlatformSsoUserServiceFacade() {
        return platformSsoUserServiceFacade;
    }

    @Bean("routeApi")
    public RouteApi getRouteApi() {
        return routeApi;
    }
}
