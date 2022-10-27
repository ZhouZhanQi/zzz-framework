package com.zzz.framework.starters.security.model.code;

import com.zzz.framework.common.model.code.BaseExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhouzq
 * @date: 2022/7/6-18:19
 * @desc: 认证异常编码
 */
@Getter
@AllArgsConstructor
public enum SecurityExceptionCode implements BaseExceptionCode {

    OAUTH2_AUTHORIZATION_INFO_IS_NULL(70001, "授权信息不能为空"),

    TOKEN_INFO_IS_EMPTY(70101, "令牌信息不能为空"),

    TOKEN_TYPE_IS_NULL(70102, "令牌类型不能为空"),

    CLIENT_INFO_IS_NULL(70110, "客户端信息不能为空"),

    OAUTH2_AUTHORIZATION_CONSENT_IS_NULL(70201, "授权确认不能为空"),

    TOKEN_INFO_TIMEOUT(70301, "token超时"),
    ;


    private final int code;

    private final String message;
}
