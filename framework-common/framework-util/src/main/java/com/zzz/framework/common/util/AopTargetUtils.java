package com.zzz.framework.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/2-10:33
 * @desc: aop代理对象类
 * </pre>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AopTargetUtils {

    /**
     * 获取 目标对象
     *
     * @param proxy 代理对象
     * @return
     * @throws Exception
     */
    public static Object getTarget(Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)) {
            //不是代理对象
            return proxy;
        }

        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        }

        //cglib
        return getCglibProxyTargetObject(proxy);
    }


    /**
     * 获取被cglib代理的对象
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field field = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        field.setAccessible(true);
        Object dynamicAdvisedInterceptor = field.get(proxy);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
        return target;
    }


    /**
     * 获取被jdk代理的对象
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field field = proxy.getClass().getSuperclass().getDeclaredField("h");
        field.setAccessible(true);
        AopProxy aopProxy = (AopProxy) field.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
        return target;
    }
}
