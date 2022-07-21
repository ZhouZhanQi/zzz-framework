package com.zzz.framework.starter.core.utils;

import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /**
     * 获取请求参数集合
     * @param request
     * @return
     */
    public static Map<String, String> getServletRequestParamMap(ServletRequest request) {
        return Optional.ofNullable(request.getParameterMap()).map(parameterMap -> {
            return parameterMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                return String.join(",", entry.getValue());
            }));
        }).orElse(Maps.newHashMap());
    }
}
