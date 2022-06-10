package com.zzz.framework.starter.core.listener;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

/**
 * @author zhouzhanqi
 * @date 2021/7/22 1:43 下午
 * @desc 日志配置监听器
 */
@Order(LoggingApplicationListener.DEFAULT_ORDER - 1)
@Component
public class LoggingListener implements ApplicationListener<ApplicationEvent> {

    /**
     * 提供给日志文件读取配置的key, 使用时需要在前面加上 sys:
     */
    private final static String LOG_PATH = "log.path";

    /**
     * 提供给日志文件读取配置的key, 使用时需要在前面加上 sys:
     */
    private final static String APP_NAME = "app.name";

    /**
     * 应用名称
     */
    private final static String APPLICATION_NAME = "spring.application.name";

    /**
     * 日志路径
     */
    private final static String APPLICATION_LOG_PATH = "zzz.log.path";

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ApplicationEnvironmentPreparedEvent) {
            ConfigurableEnvironment environment = ((ApplicationEnvironmentPreparedEvent) applicationEvent).getEnvironment();
            String appName = environment.getProperty(APPLICATION_NAME);
            if (StrUtil.isNotBlank(appName)) {
                System.setProperty(APP_NAME, appName);
            }

            String filePath = environment.getProperty(APPLICATION_LOG_PATH);
            if (StrUtil.isNotBlank(filePath)) {
                System.setProperty(LOG_PATH, filePath);
            }
        }
    }
}
