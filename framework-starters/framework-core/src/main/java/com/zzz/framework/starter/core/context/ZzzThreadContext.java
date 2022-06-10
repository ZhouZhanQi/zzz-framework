package com.zzz.framework.starter.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.zzz.framework.starter.core.model.ZzzContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/18-15:21
 * @desc: 令牌信息
 * </pre>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ZzzThreadContext {
    private static final ThreadLocal<ZzzContext> threadLocalMap = new TransmittableThreadLocal<>();

    public static void setContext(ZzzContext zzzContext) {
        threadLocalMap.set(zzzContext);
    }

    public static ZzzContext getContext() {
        return threadLocalMap.get();
    }

    public static void clear() {
        threadLocalMap.remove();
    }
}
