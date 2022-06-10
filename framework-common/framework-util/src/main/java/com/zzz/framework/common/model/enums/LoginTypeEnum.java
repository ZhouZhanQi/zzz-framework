package com.zzz.framework.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/9/14-10:46
 * @desc: 登录类型枚举
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    USERNAME(1, "用户名"),
    MOBIL_PHONE(2, "手机号"),
    ;

    private final Integer code;

    private final String value;
}
