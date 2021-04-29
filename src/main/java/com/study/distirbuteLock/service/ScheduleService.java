package com.study.distirbuteLock.service;

import com.study.distirbuteLock.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 5秒执行一次
     */
    @Scheduled(cron = "0/5*****?")
    public void sendSms(){
        //使用redis分布式锁发送短信
        try (RedisLock redisLock = new RedisLock(redisTemplate, "autoSms", 30)){
            if (redisLock.getLock()){
                log.info("向138xxxxxxxxx发送短信!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
