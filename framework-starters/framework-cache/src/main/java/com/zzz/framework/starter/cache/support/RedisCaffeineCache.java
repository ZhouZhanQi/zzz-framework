package com.zzz.framework.starter.cache.support;

import com.zzz.framework.starter.cache.props.CacheRedisCaffeineProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.cache.RedisCache;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/3-9:54
 * @desc:
 * </pre>
 */
@Slf4j
public class RedisCaffeineCache extends AbstractValueAdaptingCache {

    private String name;

    private RedisCache redisCache;

    private CaffeineCache caffeineCache;

    private boolean clearRemoteOnExit;

    public RedisCaffeineCache(String name, RedisCache redisCache, CaffeineCache caffeineCache, CacheRedisCaffeineProperties cacheRedisCaffeineProperties) {
        super(cacheRedisCaffeineProperties.isCacheNullValues());
        this.name = name;
        this.redisCache = redisCache;
        this.caffeineCache = caffeineCache;
        this.clearRemoteOnExit = cacheRedisCaffeineProperties.isClearRemoteOnExit();
    }

    @Override
    protected Object lookup(Object key) {
        Object value = caffeineCache.get(key);
        if(Objects.nonNull(value)) {
            log.debug(">>> get cache value from caffeine, key: {}", key);
            return value;
        }

        value = redisCache.get(key);
        if(Objects.nonNull(value)) {
            log.debug(">>> get cache value from caffeine and put value to redis, key : {}", key);
            caffeineCache.put(key, value);
        }
        return value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Callable<T> callable) {
        return (T) caffeineCache.getNativeCache().get(key, k -> getRedisStoreValue(k, callable));
    }

    private <T> Object getRedisStoreValue(Object key, Callable<T> callable) {
        T value = redisCache.get(key, callable);
        return toStoreValue(value);
    }

    @Override
    public void put(Object key, Object value) {
        if (!super.isAllowNullValues() && value == null) {
            this.evict(key);
            return;
        }

        redisCache.put(key, toStoreValue(value));
//        push(new CacheMessage(this.name, key));
        caffeineCache.put(key, value);
    }

    @Override
    public void evict(Object key) {
        // 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内如果先清除caffeine缓存后其他请求会再从redis里加载到caffeine中
       redisCache.evict(key);
       caffeineCache.evict(key);
    }

    @Override
    public void clear() {
        // 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内如果先清除caffeine缓存后其他请求会再从redis里加载到caffeine中
//        redisCache.clear();
        if (clearRemoteOnExit) {
            redisCache.clear();
        }
        caffeineCache.clear();
    }

}
