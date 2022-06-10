package com.zzz.framework.starter.web.annotation;

import com.zzz.framework.starter.web.config.XxlJobAutoConfiguration;
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
 * @datetime: 2021/7/30-15:50
 * @desc: 自动装配xxl-job
 * </pre>
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ XxlJobAutoConfiguration.class })
public @interface EnableXxlJob {
}
