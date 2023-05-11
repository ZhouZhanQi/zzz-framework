package com.zzz.framework.starter.security.model.code;

import com.zzz.framework.starter.core.model.code.RedisKeyPrefix;
import lombok.AllArgsConstructor;

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

    /**
     * 授权确认信息
     */
    TOKEN_CONSENT("token:consent", false),

    TOKEN_INFO("token:info", false),
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

    @Override
    public String keySuffix(String... params) {
        return RedisKeyPrefix.super.keySuffix(params);
    }
}
