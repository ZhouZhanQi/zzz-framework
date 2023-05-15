package com.zzz.framework.starter.core.exceptions;

import org.dromara.hutool.core.text.StrUtil;
import com.zzz.framework.common.exceptions.BaseException;
import com.zzz.framework.common.model.code.BaseExceptionCode;

import java.io.Serial;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/9/26-11:40
 * @desc: 转换异常
 * </pre>
 */
public class ConvertException extends BaseException {

    @Serial
    private static final long serialVersionUID = 2428134591820168372L;

    public ConvertException() {
    }

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertException(Throwable cause) {
        super(cause);
    }

    public ConvertException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public ConvertException(Throwable cause, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), cause);
    }

    public <R extends BaseExceptionCode> ConvertException(R responseCode) {
        super(responseCode);
    }

    public <R extends BaseExceptionCode> ConvertException(R responseCode, Object... params) {
        super(responseCode, params);
    }
}
