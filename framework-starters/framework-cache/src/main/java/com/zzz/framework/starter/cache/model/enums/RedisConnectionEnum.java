package com.zzz.framework.starter.cache.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/18-17:53
 * @desc: redis链接枚举类
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum RedisConnectionEnum {

    /**
     * 单节点部署方式
     */
    STANDALONE("standalone", "单节点部署方式"),

    /**
     * 哨兵部署方式
     */
    SENTINEL("sentinel", "哨兵部署方式"),

    /**
     * 集群部署方式
     */
    CLUSTER("cluster", "集群方式"),

    /**
     * 主从部署方式
     */
    MASTERSLAVE("masterslave", "主从部署方式"),

    ;

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String desc;


    /**
     * 根据类型获取连接枚举
     * @param type connection type
     */
    public static RedisConnectionEnum fromType(String type) {
        return Arrays.stream(RedisConnectionEnum.values())
                .filter(redisConnection -> redisConnection.getType().equals(type))
                .findFirst().orElse(null);
    }
}
