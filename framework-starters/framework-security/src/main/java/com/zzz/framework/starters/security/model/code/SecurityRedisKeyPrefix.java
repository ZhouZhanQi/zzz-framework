package com.zzz.framework.starters.security.model.code;

import com.zzz.framework.starter.cache.model.code.RedisKeyPrefix;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhouzq
 * @date: 2022/7/11-13:46
 * @desc:
 */
@AllArgsConstructor
public enum SecurityRedisKeyPrefix implements RedisKeyPrefix {

    OAUTH_SMS_CODE("oauth:sms:code", false),

    /**
     * 客户端信息
     */
    OAUTH_CLIENT("oauth::client", false),

    ;

    private final String key;

    private final Boolean expireNotify;

    @Override
    public String key() {
        return this.key;
    }

    @Override
    public Boolean expireNotify() {
        return this.expireNotify;
    }
}
