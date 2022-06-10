package com.zzz.framework.starter.cache.layer;

import cn.hutool.core.map.MapUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zzz.framework.starter.cache.props.CacheRedisCaffeineProperties;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/11/4-17:59
 * @desc: cafffeine本地 缓存管理
 * </pre>
 */
public class ZzzCaffeineCacheManager extends CaffeineCacheManager {

    private CacheRedisCaffeineProperties cacheRedisCaffeineProperties;

    public ZzzCaffeineCacheManager(CacheRedisCaffeineProperties cacheRedisCaffeineProperties) {
//        super(ArrayUtil.toArray(cacheRedisCaffeineProperties.getCacheNames(), String.class));
        this.cacheRedisCaffeineProperties = cacheRedisCaffeineProperties;
        this.setAllowNullValues(cacheRedisCaffeineProperties.isCacheNullValues());
        super.setCacheNames(cacheRedisCaffeineProperties.getCacheNames());
        super.setCacheLoader(new CacheLoader<Object, Object>() {
            @Override
            public @Nullable Object load(@NonNull Object key) throws Exception {
                return null;
            }

            @Override
            public @Nullable Object reload(@NonNull Object key, @NonNull Object oldValue) throws Exception {
                return oldValue;
            }
        });
        super.setCaffeine(caffeine());
    }

    public Caffeine<Object, Object> caffeine() {
        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();
//        if(cacheRedisCaffeineProperties.getCaffeine().getExpireAfterAccess() > 0) {
//            cacheBuilder.expireAfterAccess(cacheRedisCaffeineProperties.getCaffeine().getExpireAfterAccess(), TimeUnit.MILLISECONDS);
//        }
//        if(cacheRedisCaffeineProperties.getCaffeine().getExpireAfterWrite() > 0) {
//            cacheBuilder.expireAfterWrite(cacheRedisCaffeineProperties.getCaffeine().getExpireAfterWrite(), TimeUnit.MILLISECONDS);
//        }
        if(cacheRedisCaffeineProperties.getCaffeine().getInitialCapacity() > 0) {
            cacheBuilder.initialCapacity(cacheRedisCaffeineProperties.getCaffeine().getInitialCapacity());
        }
        if(cacheRedisCaffeineProperties.getCaffeine().getMaximumSize() > 0) {
            cacheBuilder.maximumSize(cacheRedisCaffeineProperties.getCaffeine().getMaximumSize());
        }
        if(cacheRedisCaffeineProperties.getCaffeine().getRefreshAfterWrite() > 0) {
            cacheBuilder.refreshAfterWrite(cacheRedisCaffeineProperties.getCaffeine().getRefreshAfterWrite(), TimeUnit.MILLISECONDS);
        }
        return cacheBuilder;
    }


    @Override
    protected Cache<Object, Object> createNativeCaffeineCache(String name) {
        Map<String, Long> expires = cacheRedisCaffeineProperties.getCaffeine().getExpires();
        if (MapUtil.isNotEmpty(expires)) {
            if (expires.containsKey(name)) {
                Long timeExpire = expires.get(name);
                return Caffeine.newBuilder().expireAfterWrite(timeExpire, TimeUnit.MILLISECONDS).build(new CacheLoader<Object, Object>() {
                    @Override
                    public @Nullable Object load(@NonNull Object key) throws Exception {
                        return null;
                    }
                    @Override
                    public @Nullable Object reload(@NonNull Object key, @NonNull Object oldValue) throws Exception {
                        return oldValue;
                    }
                });
            }
        }
        return super.createNativeCaffeineCache(name);
    }
}
