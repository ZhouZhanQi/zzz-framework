package com.zzz.framework.starter.core.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhouzq
 * @date: 2022/7/6-15:13
 * @desc: 头信息参数名
 */
@Getter
@AllArgsConstructor
public enum ZzzHeadParamNameEnum {

    CLIENT_TYPE("client_type"),

    GRANT_TYPE("grant_type"),
    ;

    private final String code;

}
