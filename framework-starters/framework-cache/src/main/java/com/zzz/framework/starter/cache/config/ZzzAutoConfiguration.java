package com.zzz.framework.starter.cache.config;

import com.zzz.framework.starter.cache.layer.ZzzCacheManager;
import com.zzz.framework.starter.cache.props.CacheRedisCaffeineProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/11/5-17:00
 * @desc: 缓存自动配置
 * </pre>
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheRedisCaffeineProperties.class)
@Import({RedisConfiguration.class, CaffeineConfiguration.class})
public class ZzzAutoConfiguration {

    @Autowired
    private CacheRedisCaffeineProperties cacheRedisCaffeineProperties;

    @Bean
    @Primary
    public ZzzCacheManager cacheManager(CaffeineCacheManager caffeineCacheManager, RedisCacheManager redisCacheManager) {
        log.info(">>> zzz enable layer cache");
        ZzzCacheManager cacheManager = new ZzzCacheManager();
        cacheManager.setCaffeineCacheManager(caffeineCacheManager);
        cacheManager.setRedisCacheManager(redisCacheManager);
        cacheManager.setCacheRedisCaffeineProperties(cacheRedisCaffeineProperties);
        return cacheManager;
    }
}
