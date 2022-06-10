package com.zzz.framework.starter.core.model.code;

import com.zzz.framework.common.model.code.BaseExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/9/11-18:13
 * @desc: 公共异常编码
 * 10***
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum CoreExceptionCode implements BaseExceptionCode {

    PARAM_CHECK_NOT_NULL(10000, "%s不能为空"),

    DATA_QUERY_ERROR(10001, "%s查询失败"),

    DATA_UPDATE_ERROR(10002, "%s更新失败"),

    JSON_DATA_CONVERT_ERROR(10003, "Json数据转换失败"),

    SERVICE_CIRCUIT_BREAKER(1000, "%s服务熔断, s%"),
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
