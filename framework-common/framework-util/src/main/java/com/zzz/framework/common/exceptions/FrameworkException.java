package com.zzz.framework.common.exceptions;

import org.dromara.hutool.core.text.StrUtil;
import com.zzz.framework.common.model.code.BaseExceptionCode;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/18-14:40
 * @desc: 框架异常
 * </pre>
 */
public class FrameworkException extends BaseException {

    private static final long serialVersionUID = 1387199319760480695L;

    public FrameworkException() {
    }

    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameworkException(Throwable cause) {
        super(cause);
    }

    public FrameworkException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public FrameworkException(Throwable cause, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), cause);
    }

    public <R extends BaseExceptionCode> FrameworkException(R responseCode) {
        super(responseCode);
    }

    public <R extends BaseExceptionCode> FrameworkException(R responseCode, Object... params) {
        super(responseCode, params);
    }
}
