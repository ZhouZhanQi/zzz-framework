package com.zzz.framework.starter.cache.model.code;

import com.zzz.framework.common.model.code.BaseExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/18-14:30
 * @desc: 缓存包异常编码
 * code 30***
 *
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum CacheExceptionCode implements BaseExceptionCode {

    REDISSON_CONNECT_TYPE_ERROR(30001, "redisson连接类型错误"),
    ;
    private final int code;

    private final String message;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
