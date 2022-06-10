package com.zzz.framework.common.model.code;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/18-14:00
 * @desc: 基础异常编码
 * </pre>
 */
public interface BaseExceptionCode {

    /**
     * 响应编码
     * @return
     */
    int getCode();

    /**
     * 响应消息
     * @return
     */
    String getMessage();
}
