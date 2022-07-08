package com.zzz.framework.starter.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author: zhouzq
 * @date: 2022/7/6-14:50
 * @desc: servlet 工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServletUtils extends WebUtils {

    /**
     * 获取HttpServletRequest
     * @return
     */
    public static Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
    }

    /**
     * 获取HttpServletResponse
     * @return
     */
    public static Optional<HttpServletResponse> getResponse() {
        return Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse());
    }
}
