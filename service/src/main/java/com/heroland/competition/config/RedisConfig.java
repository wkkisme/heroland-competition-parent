package com.heroland.competition.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * heroland-competition-parent
 *
 * @author wangkai
 * @date 2020/6/19
 */

@Configuration
@ConfigurationProperties("spring.redis.cluster")
public class RedisConfig {

    private List<Integer> ports;

    private String host;

    private JedisPoolConfig poolConfig;

    @Bean
    RedisClusterConfiguration redisClusterConfguration(){

        RedisClusterConfiguration configuration=new RedisClusterConfiguration();

        List<RedisNode>nodes=new ArrayList<>();

        for(Integer port:ports){

            nodes.add(new RedisNode(host,port));

        }

        configuration.setPassword(RedisPassword.of("123@123"));

                configuration.setClusterNodes(nodes);

        return configuration;

    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory(){

        JedisConnectionFactory factory=new JedisConnectionFactory(redisClusterConfiguration(),poolConfig);

        return factory;

    }

    @Bean

    RedisTemplate redisTemplate(){

        RedisTemplate redisTemplate = new RedisTemplate();

        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        return redisTemplate;

    }

    @Bean
    StringRedisTemplate stringRedisTemplate(){

        StringRedisTemplate stringRedisTemplate=new StringRedisTemplate(jedisConnectionFactory());

        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());

        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());

        return stringRedisTemplate;


    }


}
