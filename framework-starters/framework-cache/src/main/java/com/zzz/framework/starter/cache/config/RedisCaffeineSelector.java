package com.zzz.framework.starter.cache.config;

import cn.hutool.core.util.BooleanUtil;
import com.zzz.framework.starter.cache.annotation.EnableZzzCache;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/7/31-17:18
 * @desc: redis配置
 * </pre>
 */
public class RedisCaffeineSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableZzzCache.class.getName());
        boolean enableLayerCache = BooleanUtil.toBoolean(String.valueOf(attributes.getOrDefault("enableLayerCache", false)));
        if (enableLayerCache) {
            return new String[]{ZzzAutoConfiguration.class.getName()};
        }
        return new String[]{RedisConfiguration.class.getName()};
    }
}
