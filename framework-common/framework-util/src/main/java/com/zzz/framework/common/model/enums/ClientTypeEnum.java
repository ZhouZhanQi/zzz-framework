package com.zzz.framework.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/9/11-15:59
 * @desc: 设备类型枚举
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum ClientTypeEnum {

    /**
     * app
     */
    APP(1, "APP"),

    /**
     * 小程序
     */
    APPLET(2, "小程序"),

    /**
     * web端
     */
    WEB(3, "Web"),

    /**
     * 开放平台
     */
    COMPONENT_PLATFORM(4, "开放平台"),
    ;

    private final Integer code;

    private final String value;
}
