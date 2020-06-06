package com.heroland.competition.config;

import com.heroland.competition.filter.RouteCheckFilter;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Locale;

/**
 * @author wangkai
 */
@Configuration
public class HealthCloudWebMvcConfigurerAdapter implements WebMvcConfigurer {

//    @Resource
//    private CheckUserInterceptor checkUserInterceptor;
//
//
//    @Reference(version = "${health.dubbo.version}",group = "bqhealth-cloud")
//    private HealthCloudSsoServiceFacade healthCloudSsoServiceFacade;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(checkUserInterceptor).addPathPatterns("/**");
//    }

    @Bean
    public CorsFilter corsFilter() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //放行哪些原始域
        config.addAllowedOrigin("*");
        //是否发送Cookie信息
        config.setAllowCredentials(true);
        //放行哪些原始域(请求方式)
        config.addAllowedMethod("*");
        //放行哪些原始域(头部信息)
        config.addAllowedHeader("*");
//        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
//        config.addExposedHeader("*");

        //2.添加映射路径
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        //noinspection NullableProblems
        registry.addFormatter(new Formatter<Date>() {
            @Override
            public Date parse(String date, Locale locale) {
                return new Date(Long.parseLong(date));
            }

            @Override
            public String print(Date date, Locale locale) {
                return Long.valueOf(date.getTime()).toString();
            }
        });
    }

//
//    @Bean
//    public FilterRegistrationBean paramFilterRegister() {
//        FilterRegistrationBean<RouteCheckFilter> registration = new FilterRegistrationBean<>();
//        RouteCheckFilter routeCheckFilter = new RouteCheckFilter();
//        routeCheckFilter.setIndexHtml(FileUtils.getString("static/res/index.html"));
//        routeCheckFilter.setPatientHtml(FileUtils.getString("static/patientindex/index.html"));
//        routeCheckFilter.setReportCenter(FileUtils.getString("static/reportcenter/index.html"));
//        routeCheckFilter.setQuestionnaireIndexHtml(FileUtils.getString("static/hot/index.html"));
//        routeCheckFilter.setServerHtml(FileUtils.getString("static/hulianhutongserver/index.html"));
//        routeCheckFilter.setHealthCloudSsoServiceFacade(healthCloudSsoServiceFacade);
//        routeCheckFilter.setKpiHtml(FileUtils.getString("static/kpi/index.html"));
//        routeCheckFilter.setMainIndexHtml(FileUtils.getString("static/mainindex/index.html"));
//        routeCheckFilter.setDocHtml(FileUtils.getString("static/docpost/index.html"));
//        registration.setFilter(routeCheckFilter);
//        registration.addUrlPatterns("/*");
//        registration.setName("cloudRouteFilter");
//        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
//        return registration;
//    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(50971520);
        multipartResolver.setMaxInMemorySize(1048576);
        return multipartResolver;
    }


}
