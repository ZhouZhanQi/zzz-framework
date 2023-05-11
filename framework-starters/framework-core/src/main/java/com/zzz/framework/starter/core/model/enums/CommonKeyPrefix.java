package com.zzz.framework.starter.core.model.enums;

import com.zzz.framework.starter.core.model.code.RedisKeyPrefix;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Project : zzz-framework
 * @Desc : 公共的基础缓存key
 * @Author : Zzz
 * @Datetime : 2023/5/11 10:37
 */
@Getter
@AllArgsConstructor
public enum CommonKeyPrefix implements RedisKeyPrefix {


    ZZZ_USER_INFO("zzz_user_info", true),
    ;

    private final String key;

    private final Boolean expireNotify;

    @Override
    public String key() {
        return this.getKey();
    }

    @Override
    public Boolean expireNotify() {
        return this.getExpireNotify();
    }
}
