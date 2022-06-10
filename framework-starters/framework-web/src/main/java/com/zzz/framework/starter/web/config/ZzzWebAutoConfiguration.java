package com.zzz.framework.starter.web.config;

import com.zzz.framework.starter.web.handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/9/29-14:14
 * @desc:
 * </pre>
 */
@Slf4j
@Configuration
public class ZzzWebAutoConfiguration {

    @Bean
    public GlobalExceptionHandler exceptionHandler() {
        log.info(">>> shadowlayover global exception handler init ...");
        return new GlobalExceptionHandler();
    }
}
