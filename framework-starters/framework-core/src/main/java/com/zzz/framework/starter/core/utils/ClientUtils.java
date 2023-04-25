package com.zzz.framework.starter.core.utils;

import com.google.common.collect.Maps;
import com.zzz.framework.common.exceptions.BusinessException;
import com.zzz.framework.common.exceptions.FrameworkException;
import com.zzz.framework.common.model.code.CommonExceptionCode;
import com.zzz.framework.starter.core.context.ZzzThreadContext;
import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.framework.starter.core.model.code.CoreExceptionCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/9/29-10:09
 * @desc: 客户端工具类
 * </pre>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientUtils {

    public static <T> ResponseData<T> serviceCall(ResponseData<T> responseData) {
        Optional<ResponseData<T>> optional = Optional.ofNullable(responseData);
        optional.orElseThrow(() -> new FrameworkException(CoreExceptionCode.SERVICE_CIRCUIT_BREAKER, "", ZzzThreadContext.getContext().getTraceId()));
        return optional.filter(ResponseData::isSuccess).orElseThrow(() -> {
            ResponseData<T> response = optional.get();
            return new BusinessException(response.getErrMsg(), response.getErrCode());
        });
    }

    /**
     * 不抛出一次获取服务调用结果
     * @param responseData
     * @return
     */
    public static <T> ResponseData<T> serviceCallNoThrow(ResponseData<T> responseData) {
        return Optional.ofNullable(responseData).orElse(ResponseData.fail(CommonExceptionCode.SERVICE_ERROR));
    }

    /**
     * 获取返回结果数据
     * @param responseData
     * @return
     * @param <T>
     */
    public static <T> T serviceCallData(ResponseData<T> responseData) {
        Optional<ResponseData<T>> optional = Optional.ofNullable(responseData);
        optional.orElseThrow(() -> new FrameworkException(CoreExceptionCode.SERVICE_CIRCUIT_BREAKER, "", ZzzThreadContext.getContext().getTraceId()));
        return optional.filter(ResponseData::isSuccess).map(ResponseData::getData).orElseThrow(() -> {
            ResponseData<T> response = optional.get();
            return new BusinessException(response.getErrMsg(), response.getErrCode());
        });
    }

    /**
     * 获取返回结果数据-不抛出异常
     * @param responseData
     * @return
     * @param <T>
     */
    public static <T> T serviceCallDataNoThrow(ResponseData<T> responseData) {
        Optional<ResponseData<T>> optional = Optional.ofNullable(responseData);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.filter(ResponseData::isSuccess).map(ResponseData::getData).orElse(null);
    }
}
