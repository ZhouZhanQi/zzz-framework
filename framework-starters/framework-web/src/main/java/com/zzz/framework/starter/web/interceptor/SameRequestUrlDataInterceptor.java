package com.zzz.framework.starter.web.interceptor;

import org.dromara.hutool.core.date.LocalDateTimeUtil;
import com.google.common.collect.Maps;
import com.zzz.framework.common.util.JacksonUtils;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/7/31-14:20
 * @desc: 重复请求地址与数据拦截器
 * </pre>
 */
@Setter
@Component
public class SameRequestUrlDataInterceptor extends RepeatSubmitInterceptor {

    public final String REPEAT_PARAMS = "repeatParams";

    public final String REPEAT_TIME = "repeatTime";

    public final String SESSION_REPEAT_KEY = "repeatData";

    /**
     * 间隔时间，单位:秒 默认10秒
     *
     * 两次相同参数的请求，如果间隔时间大于该参数，系统不会认定为重复提交的数据
     */
    private int intervalTime = 10;

    @Override
    public boolean isRepeatSubmit(HttpServletRequest request) throws Exception {
        String requestJsonParam = JacksonUtils.pojo2Json(request.getParameterMap());
        Map<String, String> paramMap = Maps.newHashMapWithExpectedSize(2);
        //请求参数
        paramMap.put(REPEAT_PARAMS, requestJsonParam);
        //请求时间
        paramMap.put(REPEAT_TIME, Long.toString(LocalDateTimeUtil.toEpochMilli(LocalDateTime.now(ZoneId.systemDefault()))));
        //缓存中获取token
        String requestUri = request.getRequestURI();

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        //从缓存中获取

        return false;
    }
}
