package com.heroland.competition.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * bqhealth-cloud
 *
 * @author wangkai
 * @date 2019/8/7
 */

@Configuration
@MapperScan(basePackages = {"com.alijk.bqhealth.cloud.dal.mapper","com.alijk.bqhealth.cloud.dal.mapper.kpi"}, sqlSessionTemplateRef  = "yuhangSqlSessionTemplate")
@PropertySource(value = "classpath:druid-${spring.profiles.active}.properties")
public class DataSourceConfig {


    @Bean(name = "yuhangDataSource")
    @ConfigurationProperties(prefix = "druid.yuhang")
    @Primary
    public DataSource yuhangDataSource() {
        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
    }

    @Bean(name = "yuhangSqlSessionFactory")
    @Primary
    public SqlSessionFactory yuhangSqlSessionFactory(@Qualifier("yuhangDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:sqlmapper/**/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "yuhangTransactionManager")
    @Primary
    public DataSourceTransactionManager yuhangTransactionManager(@Qualifier("yuhangDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "yuhangSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate yuhangSqlSessionTemplate(@Qualifier("yuhangSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
