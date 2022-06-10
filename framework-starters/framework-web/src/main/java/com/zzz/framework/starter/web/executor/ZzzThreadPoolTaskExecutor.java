package com.zzz.framework.starter.web.executor;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/21-9:56
 * @desc: 简单替换{@link ThreadPoolTaskExecutor}, 可搭配TransmittableThreadLocal实现父子线程之间的数据传递
 * </pre>
 */
public class ZzzThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    @Override
    public void execute(Runnable task) {
        super.execute(TtlRunnable.get(task));
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        super.execute(TtlRunnable.get(task), startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(TtlRunnable.get(task));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(TtlCallable.get(task));
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        return super.submitListenable(TtlRunnable.get(task));
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        return super.submitListenable(TtlCallable.get(task));
    }
}
