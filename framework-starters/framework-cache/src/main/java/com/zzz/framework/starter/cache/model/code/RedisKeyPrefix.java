package com.zzz.framework.starter.cache.model.code;

import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/9/14-15:13
 * @desc: 缓存key前缀枚举
 * </pre>
 */
public interface RedisKeyPrefix {

    /**
     * 缓存key
     */
    String key();


    /**
     * 是否开启过期通知
     */
    Boolean expireNotify();

    /**
     * 拼接缓存key
     * @param params
     */
    default String keySuffix(String... params) {
        return String.join(":", key(), String.join(":", params));
    }
}
