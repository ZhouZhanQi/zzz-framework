package com.zzz.framework.starter.core.config;

import com.zzz.framework.starter.core.filter.ZzzTraceFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/21-9:50
 * @desc: 请求配置
 * </pre>
 */
@Configuration(proxyBeanMethods = false)
public class RequestConfiguration {

    @Bean
    public ZzzTraceFilter traceFilter() {
        return new ZzzTraceFilter();
    }
}
