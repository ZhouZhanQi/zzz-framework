//package com.zzz.framework.starter.cache.lock;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * <pre>
// * @author: zhouzhanqi
// * @datetime: 2021/8/25-11:28
// * @desc: redisson分布式锁
// * </pre>
// */
//@Slf4j
//@RequiredArgsConstructor
//public class RedissonLock {
//
//    private final RedissonClient redissonClient;
//
//    /**
//     * redisson 上锁
//     * @param lockKey 锁名称
//     * @param expireTimes 过期时间
//     * @return lock result
//     */
//    public boolean lock(String lockKey, long expireTimes) {
//        return lock(lockKey, expireTimes, TimeUnit.SECONDS);
//    }
//
//    /**
//     * redisson 上锁
//     * @param lockKey 锁名称
//     * @param expireTimes 过期时间
//     * @param timeUnit 时间单位
//     * @return lock result
//     */
//    public boolean lock(String lockKey, long expireTimes, TimeUnit timeUnit) {
//        RLock rLock = redissonClient.getLock(lockKey);
//        boolean lockResult = false;
//        try {
//            lockResult = rLock.tryLock(0, expireTimes, timeUnit);
//            log.info(">>> redisson lock get lockKey: {}, result: {}", lockKey, lockResult);
//        } catch (InterruptedException e) {
//            log.error(">>> redisson lock get error, lockKey: {}", lockKey, e);
//        }
//        return lockResult;
//    }
//
//    /**
//     * 解锁
//     *
//     * @param lockKey 锁名称
//     */
//    public void release(String lockKey) {
//        redissonClient.getLock(lockKey).unlock();
//    }
//}
