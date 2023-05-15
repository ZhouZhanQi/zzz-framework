package com.zzz.framework.common.exceptions;

import org.dromara.hutool.core.text.StrUtil;
import com.zzz.framework.common.model.code.BaseExceptionCode;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/18-14:39
 * @desc: 业务异常
 * </pre>
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = -850094070297818746L;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, int code) {
        super(message, code);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public BusinessException(Throwable cause, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), cause);
    }

    public <R extends BaseExceptionCode> BusinessException(R responseCode) {
        super(responseCode);
    }

    public <R extends BaseExceptionCode> BusinessException(R responseCode, Object... params) {
        super(responseCode, params);
    }
}
