package com.zzz.framework.starter.core.filter;

import com.zzz.framework.starter.core.utils.TraceUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/21-9:40
 * @desc: 调用链过滤器
 * </pre>
 */
@ConditionalOnClass(Filter.class)
public class ZzzTraceFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceId = TraceUtils.getTraceId(request);
        TraceUtils.mdcTraceId(traceId);
        filterChain.doFilter(request, response);
    }
}
