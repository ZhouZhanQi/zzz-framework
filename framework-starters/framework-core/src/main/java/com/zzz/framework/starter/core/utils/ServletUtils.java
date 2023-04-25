package com.zzz.framework.starter.core.utils;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    /**
     * 获取请求参数集合
     * @param request
     * @return
     */
    public static MultiValueMap<String, String> getServletRequestParamMultiValueMap(ServletRequest request) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        if (MapUtil.isEmpty(request.getParameterMap())) {
            return paramMap;
        }

        request.getParameterMap().entrySet().stream().forEach(entry -> {
            paramMap.addAll(entry.getKey(), Lists.newArrayList(entry.getValue()));
        });
        return paramMap;
    }


    public static void main(String[] args) {
        String str = null;




        System.out.println(Optional.ofNullable(str).orElse("").isBlank());
    }
}
