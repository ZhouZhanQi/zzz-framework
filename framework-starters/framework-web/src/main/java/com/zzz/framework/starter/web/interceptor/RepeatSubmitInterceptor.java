package com.zzz.framework.starter.web.interceptor;


import com.zzz.framework.starter.web.annotation.ZzzRepeatSubmit;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/7/30-17:27
 * @desc:
 * </pre>
 */
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            ZzzRepeatSubmit annotation = method.getAnnotation(ZzzRepeatSubmit.class);
            if (annotation != null && this.isRepeatSubmit(request)) {
                //todo 返回错误消息，不允许重复提交
                return false;
            }
            return true;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     *
     * @param request
     * @return
     * @throws Exception
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request) throws Exception;
}
