//package com.zzz.framework.starters.feign.fallback;
//
//
//import com.zzz.framework.common.exceptions.BaseException;
//import com.zzz.framework.common.model.code.CommonExceptionCode;
//import com.zzz.framework.starter.core.model.ResponseData;
//import feign.FeignException;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cglib.proxy.MethodInterceptor;
//import org.springframework.cglib.proxy.MethodProxy;
//
//import java.lang.reflect.Method;
//import java.nio.ByteBuffer;
//import java.util.Objects;
//
///**
// * <pre>
// * @author: zhouzhanqi
// * @datetime: 2021/9/29-15:13
// * @desc: 暂时未实现统一处理
// * </pre>
// */
//@Slf4j
//@AllArgsConstructor
//public class ZzzFallback<T> implements MethodInterceptor {
//
//    private final Class<T> targetType;
//
//    private final String targetName;
//
//    private final Throwable cause;
//
//    @Override
//    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        log.error(">>> zzz {}.{} feign fallback, service-id: {}, message: {}", targetType.getName(), method.getName(), targetName, cause.getMessage());
//        Class<?> returnType = method.getReturnType();
//        //暂时不支持 flux，rx，异步等，返回值不是 R，直接返回 null。
//        if (ResponseData.class != returnType) {
//            return null;
//        }
//
//        //基础异常
//        if (cause instanceof BaseException) {
//            BaseException baseException = (BaseException) cause;
//            return ResponseData.fail(baseException.getCode(), baseException.getMessage());
//        }
//        // 非 FeignException
//        if (!(cause instanceof FeignException)) {
//            return ResponseData.fail(CommonExceptionCode.SERVICE_ERROR.getCode(), cause.getMessage());
//        }
//
//        FeignException exception = (FeignException) cause;
//        ByteBuffer content = exception.responseBody().orElse(null);
//        if (Objects.isNull(content)) {
//            return ResponseData.fail(CommonExceptionCode.SERVICE_ERROR.getCode(), cause.getMessage());
//        }
//        return ResponseData.fail(content.toString());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(targetType);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null || getClass() != obj.getClass()) {
//            return false;
//        }
//        ZzzFallback<?> that = (ZzzFallback<?>) obj;
//        return targetType.equals(that.targetType);
//    }
//}
