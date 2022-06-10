package com.zzz.framework.common.exceptions;

import cn.hutool.core.util.StrUtil;
import com.zzz.framework.common.model.code.BaseExceptionCode;
import lombok.Getter;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/7/20-17:41
 * @desc: 基础异常
 * </pre>
 */
@Getter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 6246418401387013153L;

    /**
     * 异常编码
     */
    private int code;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(int code) {
        this.code = code;
    }

    public <R extends BaseExceptionCode> BaseException(R responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    public <R extends BaseExceptionCode> BaseException(R responseCode, Object... params) {
        super(StrUtil.format(responseCode.getMessage(), params));
        this.code = responseCode.getCode();
    }

    public BaseException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BaseException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public BaseException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }
}
