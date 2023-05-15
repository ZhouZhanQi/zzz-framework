package com.zzz.framework.starter.cache;

import org.dromara.hutool.core.text.StrUtil;
import com.google.common.base.Joiner;
import com.zzz.framework.common.util.JacksonUtils;
import com.zzz.framework.starter.core.model.code.RedisKeyPrefix;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/9/14-15:07
 * @desc: redis缓存帮助类
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
public class RedisCacheHelper<T> {

    private final RedisTemplate<String, T> redisTemplate;

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public T get(RedisKeyPrefix prefix, String key) {
        return redisTemplate.boundValueOps(getKey(prefix, key)).get();
    }

    /**
     * 删除缓存
     * @param prefix
     * @param key
     */
    public void delete(RedisKeyPrefix prefix, String key) {
        if (log.isDebugEnabled()) {
            log.debug(">>> deleting key: {}", key);
        }
        redisTemplate.delete(getKey(prefix, key));
    }

    /**
     * 匹配删除缓存
     * @param prefix
     * @param pattern
     */
    public void deleteByPattern(RedisKeyPrefix prefix, String pattern) {
        Set<String> keys = this.keys(prefix, pattern);
        if (CollectionUtils.isEmpty(keys)) {
            log.warn(">>> deleting keys is empty, pattern: {}", pattern);
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug(">>> deleting keys: {}", JacksonUtils.pojo2Json(keys));
        }
        redisTemplate.delete(keys);
    }

    /**
     * 查询是否存在缓存
     * @param prefix
     * @param key
     * @return
     */
    public boolean hasKey(RedisKeyPrefix prefix, String key) {
        return redisTemplate.hasKey(getKey(prefix, key));
    }

    /**
     * 匹配key
     * @param prefix
     * @param pattern
     * @return
     */
    public Set<String> keys(RedisKeyPrefix prefix, String pattern) {
        return redisTemplate.keys(getKey(prefix, pattern));
    }

    /**
     * 根据前缀删除缓存
     * @param prefix
     */
    public void deleteWithPrefix(RedisKeyPrefix prefix) {
        this.deleteByPattern(prefix, "*");
    }

    /**
     * 批量删除缓存
     * @param prefix
     * @param keys
     */
    public void delete(RedisKeyPrefix prefix, Set<String> keys) {
        Set<String> deleteKeys = keys.stream().map(key -> this.getKey(prefix, key)).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            log.debug(">> deleting keys: {}", JacksonUtils.pojo2Json(deleteKeys));
        }
        redisTemplate.delete(deleteKeys);
    }

    /**
     * 设置缓存
     * @param prefix
     * @param key
     * @param value
     */
    public void set(RedisKeyPrefix prefix, String key, T value) {
        redisTemplate.opsForValue().set(getKey(prefix, key), value);
    }

    /**
     * 设置缓存
     * @param prefix
     * @param key
     * @param value
     * @param expireTimes
     * @param unit
     */
    public void set(RedisKeyPrefix prefix, String key, T value, long expireTimes, TimeUnit unit) {
        redisTemplate.opsForValue().set(getKey(prefix, key), value, expireTimes, unit);
    }

    /**
     * 设置缓存
     * @param prefix
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(RedisKeyPrefix prefix, String key, T value) {
        return redisTemplate.opsForValue().setIfAbsent(getKey(prefix, key), value);
    }

    /**
     * 设置缓存过期时间
     * @param prefix
     * @param key
     * @param expireTimes
     * @param unit
     * @return
     */
    public boolean expire(RedisKeyPrefix prefix, String key, long expireTimes, TimeUnit unit) {
        return redisTemplate.expire(getKey(prefix, key), expireTimes, unit);
    }

    /**
     *
     * @param prefix
     * @param key
     * @param delta
     * @return
     */
    public Long increment(RedisKeyPrefix prefix, String key, int delta) {
        return redisTemplate.opsForValue().increment(getKey(prefix, key), delta);
    }

    private String getKey(RedisKeyPrefix prefix, String key) {
        return Joiner.on(":").join(prefix.key(), StrUtil.isBlank(key) ? "" : key);
    }
}
