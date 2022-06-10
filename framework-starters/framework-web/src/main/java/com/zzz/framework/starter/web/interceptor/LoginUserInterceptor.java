package com.zzz.framework.starter.web.interceptor;

import com.zzz.framework.common.exceptions.FrameworkException;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.framework.common.util.JacksonUtils;
import com.zzz.framework.starter.core.context.ZzzThreadContext;
import com.zzz.framework.starter.core.model.ZzzContext;
import com.zzz.framework.starter.core.model.ZzzUser;
import com.zzz.framework.starter.core.model.constants.CoreConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Objects;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/11/30-19:52
 * @desc: 用户信息令牌拦截器
 * </pre>
 */
@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> userInfoHeaders = request.getHeaders(CoreConstants.ZZZ_USER_INFO);
        Enumeration<String> traceIdHeaders = request.getHeaders(CoreConstants.ZZZ_TRACE_ID);
        //todo 错误编码
        AssertUtils.checkArgument(Objects.nonNull(userInfoHeaders) && Objects.nonNull(traceIdHeaders), new FrameworkException());
        String userInfoJson = userInfoHeaders.nextElement();
        String traceId = traceIdHeaders.nextElement();
        //todo 错误编码
        AssertUtils.checkArgument(StringUtils.isNoneBlank(userInfoJson, traceId), new FrameworkException());
        ZzzThreadContext.setContext(ZzzContext.builder()
                .user(JacksonUtils.json2Pojo(userInfoJson, ZzzUser.class))
                .traceId(traceId).build());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
