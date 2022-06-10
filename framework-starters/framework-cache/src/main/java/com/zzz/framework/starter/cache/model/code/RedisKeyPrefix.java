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
@Getter
@AllArgsConstructor
public enum RedisKeyPrefix {

    OAUTH_SMS_CODE("oauth:sms:code", false),
    /**
     * 令牌
     */
    OAUTH_ACCESS_TOKEN("oauth:access_token", false),

    /**
     * 刷新令牌
     */
    OAUTH_REFRESH_TOKEN("oauth:refresh_token", false),

    /**
     * 客户端信息
     */
    OAUTH_CLIENT("oauth:client", false),

    /**
     * 用户信息
     */
    OAUTH_TOKEN_USER("oauth:token_user", false),
    ;
    private final String prefix;

    private final boolean expiredNotify;
}
