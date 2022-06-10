package com.zzz.framework.starter.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/7/30-15:09
 * @desc: xxl任务配置
 * </pre>
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    @NestedConfigurationProperty
    private XxlAdminProperties admin = new XxlAdminProperties();

    @NestedConfigurationProperty
    private XxlExecutorProperties executor = new XxlExecutorProperties();
}
