package com.zzz.framework.starter.web.annotation;

import com.zzz.framework.starter.web.config.AsyncAutoConfiguration;
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
 * @datetime: 2021/8/3-14:02
 * @desc: 自动装配异步处理配置
 * </pre>
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ AsyncAutoConfiguration.class })
public @interface EnableZzzAsync {
}
