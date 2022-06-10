package com.zzz.framework.starter.core.convert;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mappings;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/21-18:24
 * @desc: 基础转换器
 * </pre>
 */
public interface IBaseConvert<Source, Target> {

    /**
     * bean object copy
     * @param source
     * @return
     */
    @InheritConfiguration
    Target source2Target(Source source);

    /**
     * bean object copy inverse
     * @param target
     * @return
     */
    @InheritInverseConfiguration
    Source target2Source(Target target);
}
