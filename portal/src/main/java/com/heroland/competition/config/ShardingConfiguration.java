//package com.alijk.bqhealth.cloud.config;
//
//import com.google.common.io.Resources;
//import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.sql.SQLException;
//
///**
// * @author sujiaxin
// * @date 2019.08.08
// *
// * sharding 配置
// **/
//
//@Configuration
//@MapperScan(basePackages = "com.alijk.bqhealth.cloud.dal.mapper",sqlSessionTemplateRef = "sqlSessionTemplate")
//public class ShardingConfiguration {
//
//    @Profile("dev")
//    @Bean("dataSource")
//    public DataSource dataSourceDev() throws IOException, SQLException {
//
//
//        return ShardingDataSourceFactory.createDataSource(yamlBytes);
//    }
//
//    private byte[] getResource(String filename) throws IOException {
//        return Resources.asByteSource(Resources.getResource(filename)).read();
//    }
//
//    @Profile("pro")
//    @Bean("dataSource")
//    public DataSource dataSourcePro() throws IOException, SQLException {
//        String filename = "sharding.pro.yaml";
//        byte[] yamlBytes = getResource(filename);
//        return ShardingDataSourceFactory.createDataSource(yamlBytes);
//    }
//
//
//
//
//    @Bean(name = "dataCenterSqlSessionFactory")
////    @Primary
//    public SqlSessionFactory dataCenterSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:datasqlmapper/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name = "dataCenterTransactionManager")
////    @Primary
//    public DataSourceTransactionManager dataCenterTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "dataCenterSqlSessionTemplate")
////    @Primary
//    public SqlSessionTemplate dataCenterSqlSessionTemplate(@Qualifier("dataCenterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
