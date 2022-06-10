package com.zzz.framework.starter.web.config;

import com.zzz.framework.starter.web.executor.ZzzThreadPoolTaskExecutor;
import com.zzz.framework.starter.web.properties.AsyncPoolThreadProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/3-13:46
 * @desc: 异步线程池自动装配
 * </pre>
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableAsync
@EnableAutoConfiguration
@EnableConfigurationProperties({AsyncPoolThreadProperties.class})
public class AsyncAutoConfiguration implements AsyncConfigurer {

    @Autowired
    private AsyncPoolThreadProperties asyncPoolThreadProperties;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ZzzThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncPoolThreadProperties.getCorePoolSize());
        executor.setMaxPoolSize(asyncPoolThreadProperties.getMaxPoolSize());
        executor.setQueueCapacity(asyncPoolThreadProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(asyncPoolThreadProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix("zzz-async-thread-");
        //拒绝策略
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return zzzUncaughtExceptionHandler();
    }

    /**
     * 自定义异常处理
     *
     * @return
     */
    public AsyncUncaughtExceptionHandler zzzUncaughtExceptionHandler() {
        //todo 存储到消息队列中去处理
        return (throwable, method, objects) -> log.error("zzz async thread task error: {}, method: {}, params: {}", throwable.getMessage(), method.getName(), objects);
    }
}
