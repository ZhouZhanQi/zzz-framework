package com.zzz.framework.starter.core.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/7/20-16:55
 * @desc: resttemplate配置
 * </pre>
 */
@Configuration(proxyBeanMethods = false)
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }
}
