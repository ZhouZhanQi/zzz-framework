package com.zzz.framework.common.model.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/18-14:10
 * @desc: 基础响应信息(此错误编码信息会出触发服务熔断, 降级)
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum CommonExceptionCode implements BaseExceptionCode {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 参数错误
     */
    PARAM_ERROR(400, "参数错误"),

    /**
     * 无访问权限
     */
    NO_AUTH(401, "无访问权限"),

    /**
     * 服务未找到
     */
    NOT_FOUND(404, "服务未找到"),

    /**
     * 请求频繁
     */
    TOO_MANY_REQUESTS(429, "请求频繁"),

    /**
     * 服务异常
     */
    SERVICE_ERROR(500, "服务异常"),

    /**
     * 服务限流
     */
    SERVICE_FLOW_LIMIT(501, "服务限流"),

    /**
     * 服务降级
     */
    SERVICE_DOWN_GRADE(502, "服务降级"),

    /**
     * 热点参数限流
     */
    SERVICE_HOT_PARAM_FLOW_LIMIT(503, "热点参数限流"),

    /**
     * 系统规则限制
     */
    SERVICE_SYS_RULE_LIMIT(504, "系统规则限制"),

    /**
     * 授权规则限制
     */
    SERVICE_AUTH_RULE_LIMIT(505, "授权规则限制"),

    SERVICE_CIRCUIT_BREAKER_DETAIL(520, "%s服务熔断, s%"),

    SERVICE_CIRCUIT_BREAKER(520, "%s服务熔断, s%"),
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
