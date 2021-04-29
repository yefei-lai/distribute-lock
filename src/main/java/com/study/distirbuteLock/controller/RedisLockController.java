package com.study.distirbuteLock.controller;

import com.study.distirbuteLock.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class RedisLockController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("redisLock")
    public String redisLock(){
        log.info("我进入了方法！");
        //jdk1.7之后直接再try中写判断条件
        try(RedisLock redisLock = new RedisLock(redisTemplate, "redisKey", 30)){
            if (redisLock.getLock()){
                log.info("我进入了锁！");
                Thread.sleep(15000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        log.info("方法执行完成");
        return "方法执行完成";
    }
}
