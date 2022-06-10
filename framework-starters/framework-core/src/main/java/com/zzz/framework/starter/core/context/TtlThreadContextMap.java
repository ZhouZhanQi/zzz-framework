package com.zzz.framework.starter.core.context;

import cn.hutool.core.map.MapUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.spi.ThreadContextMap;

import java.util.Collections;
import java.util.Map;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/20-16:22
 * @desc: log4j2日志上下文实现类
 * </pre>
 */
public class TtlThreadContextMap implements ThreadContextMap {

    private final ThreadLocal<Map<String, String>> localMap;

    public TtlThreadContextMap() {
        localMap = new TransmittableThreadLocal<>();
    }

    @Override
    public void clear() {
        localMap.remove();
    }

    @Override
    public boolean containsKey(String key) {
        final Map<String, String> map = localMap.get();
        return MapUtil.isNotEmpty(map) && map.containsKey(key);
    }

    @Override
    public String get(String key) {
        final Map<String, String> map = localMap.get();
        return MapUtil.isEmpty(map) ? null : map.get(key);
    }

    @Override
    public Map<String, String> getCopy() {
        return MapUtil.isEmpty(localMap.get()) ? Maps.newHashMap() : Maps.newHashMap(localMap.get());
    }

    @Override
    public Map<String, String> getImmutableMapOrNull() {
        return localMap.get();
    }

    @Override
    public boolean isEmpty() {
        return MapUtil.isEmpty(localMap.get());
    }

    @Override
    public void put(String key, String value) {
        Map<String, String> map = localMap.get();
        map = MapUtil.isEmpty(map) ? Maps.newHashMap() : Maps.newHashMap(map);
        map.put(key, value);
        localMap.set(Collections.unmodifiableMap(map));
    }

    @Override
    public void remove(String key) {
        final Map<String, String> map = localMap.get();
        if (MapUtil.isEmpty(map)) {
            return;
        }
        final Map<String, String> copy = Maps.newHashMap(map);
        copy.remove(key);
        localMap.set(Collections.unmodifiableMap(copy));
    }
}
