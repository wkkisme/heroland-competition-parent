package com.heroland.competition.config;


import com.alijk.bqcommon.search.conf.ElasticSearchConf;
import com.alijk.bqcommon.search.store.ElasticSearchStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bqhealth-cloud
 *
 * @author wangkai
 * @date 2019/11/17
 */


@Configuration
public class EsConfig {


    @Value("${es.cluster.address}")
    private String esAddr;

    @Bean(value = {"elasticSearchStore", "esStore"})
    public ElasticSearchStore elasticSearchStore() {
        ElasticSearchStore elasticSearchStore = new ElasticSearchStore();
        ElasticSearchConf esConf = new ElasticSearchConf();
        esConf.setMultiThread(false);
        esConf.setScheme("http");
        esConf.setShards(5);
        esConf.setReplicas(0);
        /* 市一6个节点数 */
        esConf.setDataNodes(6);
        esConf.setConnectTimeout(5000);
        esConf.setSocketTimeout(60000);
        esConf.setConnectRequestTimeout(60000);
        esConf.setMaxTotalConnection(8);
        esConf.setIndexNames("test");
        esConf.setMultiThread(false);

        try {
            esConf.setAddress(esAddr);
            elasticSearchStore.setEsConf(esConf);
            elasticSearchStore.init();
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException("初始化es失败");
        }
        return elasticSearchStore;
    }

}
