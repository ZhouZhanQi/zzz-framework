package com.zzz.framework.starter.cache.config;

//import com.zzz.framework.cache.lock.RedissonLock;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.spring.starter.RedissonProperties;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/18-18:01
 * @desc: redisson自动装配
 * </pre>
 */
//@Slf4j
//@ConditionalOnClass(Redisson.class)
//@Configuration(proxyBeanMethods = false)
//@EnableConfigurationProperties(RedissonProperties.class)
//public class RedissonConfiguration {
//
//    @Bean
//    @ConditionalOnClass(RedissonClient.class)
//    public RedissonLock redissonLock(RedissonClient redissonClient) {
//        RedissonLock redissonLock = new RedissonLock(redissonClient);
//        return redissonLock;
//    }
//}
