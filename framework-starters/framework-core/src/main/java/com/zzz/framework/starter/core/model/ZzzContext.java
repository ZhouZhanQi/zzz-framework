package com.zzz.framework.starter.core.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/12/1-10:54
 * @desc: 上下文信息
 * </pre>
 */
@Data
@Builder
public class ZzzContext implements Serializable {

    private static final long serialVersionUID = 1832447871635643649L;

    /**
     * 用户信息
     */
    private ZzzUser user;

    /**
     * 链路Id
     */
    private String traceId;
}
