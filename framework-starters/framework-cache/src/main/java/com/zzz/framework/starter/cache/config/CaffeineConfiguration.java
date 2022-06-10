package com.zzz.framework.starter.cache.config;

import com.zzz.framework.starter.cache.layer.ZzzCaffeineCacheManager;
import com.zzz.framework.starter.cache.props.CacheRedisCaffeineProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/11/5-9:46
 * @desc: caffeine缓存配置
 * </pre>
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(CacheRedisCaffeineProperties.class)
public class CaffeineConfiguration {

    private final CacheRedisCaffeineProperties cacheRedisCaffeineProperties;

    @Bean
    @ConditionalOnMissingBean(CaffeineCacheManager.class)
    public CaffeineCacheManager caffeineCacheManager() {
        ZzzCaffeineCacheManager caffeineCacheManager = new ZzzCaffeineCacheManager(cacheRedisCaffeineProperties);
//        caffeineCacheManager.setCaffeine(caffeine());
        return caffeineCacheManager;
    }
}
