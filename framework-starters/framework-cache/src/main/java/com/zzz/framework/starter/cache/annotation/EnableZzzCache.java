package com.zzz.framework.starter.cache.annotation;

import com.zzz.framework.starter.cache.config.RedisCaffeineSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/11/5-15:50
 * @desc: 多级缓存
 * </pre>
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ RedisCaffeineSelector.class })
public @interface EnableZzzCache {

    /**
     * 是否开启多级缓存
     * @return
     */
    boolean enableLayerCache() default false;
}
