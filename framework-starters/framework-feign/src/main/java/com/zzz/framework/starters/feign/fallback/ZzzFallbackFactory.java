//package com.zzz.framework.starters.feign.fallback;
//
//import feign.Target;
//import lombok.AllArgsConstructor;
//import org.springframework.cglib.proxy.Enhancer;
//import org.springframework.cloud.openfeign.FallbackFactory;
//
///**
// * <pre>
// * @author: zhouzhanqi
// * @datetime: 2021/9/29-15:25
// * @desc: 暂时未实现统一处理
// * </pre>
// */
//@AllArgsConstructor
//public class ZzzFallbackFactory<T> implements FallbackFactory<T> {
//
//    private final Target<T> target;
//
//    @Override
//    public T create(Throwable cause) {
//        final Class<T> targetType = target.type();
//        final String targetName = target.name();
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(targetType);
//        enhancer.setUseCache(true);
//        enhancer.setCallback(new ZzzFallback<>(targetType, targetName, cause));
//        return (T) enhancer.create();
//    }
//}
