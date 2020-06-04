package com.heroland.competition.config;

import com.alijk.bqhealth.sso.facade.HealthCloudAclServiceFacade;
import com.alijk.bqhealth.sso.facade.HealthCloudOrgFacade;
import com.alijk.bqhealth.sso.facade.HealthCloudSsoServiceFacade;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author peng.zhup
 * @project bqhealth-cloud
 * @datetime 2020/02/13 20:03
 * @description TODO 确认下这样注册bean是否存在更好的方案?
 * @readme
 */
@Component
public class DubboBeanConfig {

    @Reference(group = "bqhealth-cloud", version = "${health.dubbo.version}", timeout = 10_000)
    private HealthCloudAclServiceFacade healthCloudAclServiceFacade;

    @Reference(group = "bqhealth-cloud", version = "${health.dubbo.version}", timeout = 10_000)
    private HealthCloudSsoServiceFacade healthCloudSsoServiceFacade;

    @Reference(group = "bqhealth-cloud", version = "${health.dubbo.version}", timeout = 10_000,check = false)
    private HealthCloudOrgFacade healthCloudOrgFacade;

    @Bean
    public HealthCloudAclServiceFacade healthCloudAclServiceFacade() {
        return healthCloudAclServiceFacade;
    }

    @Bean
    public HealthCloudSsoServiceFacade healthCloudSsoServiceFacade() {
        return healthCloudSsoServiceFacade;
    }

    @Bean
    public HealthCloudOrgFacade healthCloudOrgFacade() {
        return healthCloudOrgFacade;
    }

}
