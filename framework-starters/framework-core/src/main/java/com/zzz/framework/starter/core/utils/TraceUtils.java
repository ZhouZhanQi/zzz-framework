package com.zzz.framework.starter.core.utils;

import com.zzz.framework.starter.core.model.constants.CoreConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/20-19:29
 * @desc: 链路工具类
 * </pre>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TraceUtils {

    /**
     * 获取traceId
     * @param request
     * @return
     */
    public static String getTraceId(HttpServletRequest request) {
        String traceId = request.getParameter(CoreConstants.ZZZ_TRACE_ID);
        if (StringUtils.isNotBlank(traceId)) {
            return traceId;
        }
        return request.getHeader(CoreConstants.ZZZ_TRACE_ID);
    }

    /**
     *传递traceId到MDC
     * @param traceId
     */
    public static void mdcTraceId(String traceId) {
        if (StringUtils.isBlank(traceId)) {
            return;
        }
        MDC.put(CoreConstants.LOG_TRACE_ID, traceId);
    }
}
