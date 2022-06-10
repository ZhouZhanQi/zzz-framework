package com.zzz.framework.common.exceptions;

import cn.hutool.core.util.StrUtil;
import com.zzz.framework.common.model.code.BaseExceptionCode;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/7/20-19:45
 * @desc: 工具类异常
 * </pre>
 */
public class UtilException extends BaseException {

    private static final long serialVersionUID = -8593560132531505913L;

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }

    public UtilException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public UtilException(Throwable cause, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), cause);
    }

    public <R extends BaseExceptionCode> UtilException(R responseCode) {
        super(responseCode);
    }

    public <R extends BaseExceptionCode> UtilException(R responseCode, Object... params) {
        super(responseCode, params);
    }
}
