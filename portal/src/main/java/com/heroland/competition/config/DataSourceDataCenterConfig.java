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
@MapperScan(basePackages = "com.alijk.bqhealth.cloud.dal.datamapper", sqlSessionTemplateRef  = "dataCenterSqlSessionTemplate")
@PropertySource(value = "classpath:druid-${spring.profiles.active}.properties")
public class DataSourceDataCenterConfig {


    @Bean(name = "dataCenterDataSource")
    @ConfigurationProperties(prefix = "druid.data")
    public DataSource dataCenterDataSource() {
        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
    }

    @Bean(name = "dataCenterSqlSessionFactory")
    public SqlSessionFactory dataCenterSqlSessionFactorydataCenterSqlSessionFactory(@Qualifier("dataCenterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // TODO 修改了 原来为 classpath:datasqlmapper/*.xml
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:datasqlmapper/**/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "dataCenterTransactionManager")
    public DataSourceTransactionManager dataCenterTransactionManager(@Qualifier("dataCenterDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "dataCenterSqlSessionTemplate")
    public SqlSessionTemplate dataCenterSqlSessionTemplate(@Qualifier("dataCenterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
