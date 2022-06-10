package com.zzz.framework.starter.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/3-13:47
 * @desc: 异步线程池配置
 * </pre>
 */
@Data
@ConfigurationProperties(prefix = "async.thread-pool")
public class AsyncPoolThreadProperties {

    private Integer corePoolSize = 10;

    private Integer maxPoolSize = 100;

    /**
     * 队列
     */
    private Integer queueCapacity = 100;

    /**
     * 线程存活时间
     */
    private Integer keepAliveSeconds = 60;
}
