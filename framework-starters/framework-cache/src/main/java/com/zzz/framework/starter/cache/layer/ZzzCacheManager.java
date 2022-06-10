package com.zzz.framework.starter.cache.layer;

import com.zzz.framework.starter.cache.props.CacheRedisCaffeineProperties;
import com.zzz.framework.starter.cache.support.RedisCaffeineCache;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/11/4-18:28
 * @desc: 缓存管理器
 * </pre>
 */
@Slf4j
public class ZzzCacheManager implements CacheManager {

    private boolean dynamic = true;

    @Setter
    private CaffeineCacheManager caffeineCacheManager;

    @Setter
    private RedisCacheManager redisCacheManager;

    @Setter
    private CacheRedisCaffeineProperties cacheRedisCaffeineProperties;

    private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>();

    @Override
    public Cache getCache(String name) {
        return this.cacheMap.computeIfAbsent(name, cacheName ->
                this.dynamic ? createShadowlayoverCache(cacheName) : null);
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(cacheMap.keySet());
    }

    protected Cache createShadowlayoverCache(String name) {
        CaffeineCache caffeineCache = (CaffeineCache) this.caffeineCacheManager.getCache(name);
        RedisCache redisCache = (RedisCache) this.redisCacheManager.getCache(name);
        return new RedisCaffeineCache(name, redisCache, caffeineCache, cacheRedisCaffeineProperties);
    }
}
