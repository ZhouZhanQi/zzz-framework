package com.zzz.framework.starter.cache.support;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/9/14-15:29
 * @desc: key值缓存过期通知
 * </pre>
 */
//@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        //失效key
        String expiredKey = message.toString();


        //判断缓存是否需要通知
    }
}
