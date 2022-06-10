package com.zzz.framework.starter.data.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/9/28-15:15
 * @desc: mybatis-plus配置
 * </pre>
 */
@Data
@ConfigurationProperties(prefix = "zzz.mybatis-plus")
public class ZzzMybatisPlusProperties {

    /**
     * 多租户配置
     */
    private MultiTenant multiTenant = new MultiTenant();

    @Data
    public class MultiTenant {
        private Set<String> ignoreTables = new HashSet<>();;
    }
}
