package com.zzz.framework.starter.cache.model.code;

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
     * @return
     */
    String key();


    /**
     * 是否开启过期通知
     * @return
     */
    Boolean expireNotify();
}
