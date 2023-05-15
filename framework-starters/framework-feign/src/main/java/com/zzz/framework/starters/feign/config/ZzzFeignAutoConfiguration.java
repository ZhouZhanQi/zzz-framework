package com.zzz.framework.starters.feign.config;

import org.dromara.hutool.core.text.StrUtil;
import com.zzz.framework.common.model.constants.CoreConstants;
import com.zzz.framework.starter.core.utils.TraceUtils;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/9/29-10:36
 * @desc: feign自动配置
 * </pre>
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@Import(ZzzCommonFeignConfiguration.class)
public class ZzzFeignAutoConfiguration {

    @Bean("zzzFeignRequestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return template -> {
            //传递上下文信息
            //链路信息传递
            String traceId = MDC.get(CoreConstants.LOG_TRACE_ID);
            if (StrUtil.isNotBlank(traceId)) {
                template.header(CoreConstants.ZZZ_TRACE_ID, traceId);
                return;
            }

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.isNull(attributes) || Objects.isNull(attributes.getRequest().getHeaderNames())) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headName = headerNames.nextElement();
                String headValue = request.getHeader(headName);
                if (headName.equalsIgnoreCase(CoreConstants.ZZZ_TRACE_ID)) {
                    TraceUtils.mdcTraceId(headValue);
                    template.header(headName, headValue);
                }

                if (headName.equalsIgnoreCase(CoreConstants.ZZZ_USER_INFO)) {
                    template.header(headName, headValue);
                }
            }
        };
    }
}
