package com.zzz.framework.starter.web.handler;

import com.zzz.framework.common.exceptions.BaseException;
import com.zzz.framework.common.model.code.CommonExceptionCode;
import com.zzz.framework.starter.core.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/3-14:05
 * @desc: 全局异常处理
 * </pre>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BaseException.class)
    public ResponseData<?> handlerBaseException(BaseException e) {
        log.error(">>> zzz base exception", e);
        return ResponseData.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseData<?> handlerException(Exception e) {
        log.error(">>> zzz exception", e);
        return ResponseData.fail(CommonExceptionCode.SERVICE_ERROR);
    }
}
