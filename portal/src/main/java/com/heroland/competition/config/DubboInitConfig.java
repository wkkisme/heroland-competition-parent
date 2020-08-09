package com.heroland.competition.config;

import com.crossoverjie.cim.route.api.RouteApi;
import com.platform.sso.facade.PlatformSsoServiceFacade;
import com.platform.sso.facade.PlatformSsoUserClassServiceFacade;
import com.platform.sso.facade.PlatformSsoUserServiceFacade;
import com.platfrom.payment.api.PrePayRemoteService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboInitConfig {

    @Reference(group = "heroland-competition",version = "1.0.0",check = false)
    private PrePayRemoteService prePayRemoteService;


    @Reference(group = "platform-sso",version = "1.0.0",check = false)
    private PlatformSsoServiceFacade platformSsoServiceFacade;

    @Reference(group = "platform-sso",version = "1.0.0",check = false)
    private PlatformSsoUserClassServiceFacade platformSsoUserClassServiceFacade;


    @Reference(group = "platform-sso",version = "1.0.0",check = false)
    private PlatformSsoUserServiceFacade platformSsoUserServiceFacade;


    @Reference(group = "cim-server",version = "1.0.0",check = false)
    private RouteApi routeApi;

    @Bean("prePayRemoteService")
    public PrePayRemoteService getPrePayRemoteService(){
        return prePayRemoteService;
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
