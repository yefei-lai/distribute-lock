package com.study.distirbuteLock.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class RedisLock implements AutoCloseable{

    private RedisTemplate redisTemplate;
    private String key;
    private String value;
    //单位：秒
    private int expireTime;

    public RedisLock(RedisTemplate redisTemplate, String key, int expireTime){
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.expireTime = expireTime;
        this.value = UUID.randomUUID().toString();
    }

    /**
     * 获取锁
     * @return
     */
    public boolean getLock(){
        RedisCallback<Boolean> redisCallback = redisConnection -> {
            //设置NX
            RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.ifAbsent();
            //设置过期时间
            Expiration expiration = Expiration.seconds(expireTime);
            //序列化key
            byte[] redisKey = redisTemplate.getKeySerializer().serialize(key);
            //序列化value
            byte[] redisValue = redisTemplate.getValueSerializer().serialize(value);
            //执行setnx操作
            Boolean result = redisConnection.set(redisKey, redisValue, expiration, setOption);
            return result;
        };
        //获取分布式锁
        Boolean lock = (Boolean) redisTemplate.execute(redisCallback);
        return lock;
    }

    /**
     * 释放锁
     * @return
     */
    public boolean unLock(){
        //LUA脚本
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "\t\treturn redis.call(\"del\",KEYS[1])\n" +
                "\telse\n" +
                "\t\treturn 0\n" +
                "\tend";
        RedisScript<Boolean> redisScript = RedisScript.of(script, Boolean.class);
        List<String> keys = Arrays.asList(key);

        Boolean result = (Boolean) redisTemplate.execute(redisScript,keys,value);
        log.info("释放锁的结果：" + result);
        return result;
    }

    /**
     * 继承AutoCloseable接口，自动释放锁，在使用时不用再手动调用unLock方法
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        unLock();
    }
}
