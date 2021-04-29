package com.study.distirbuteLock.controller;

import com.study.distirbuteLock.dao.DistributeLockMapper;
import com.study.distirbuteLock.model.DistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@Slf4j
public class DemoController {
    //使用可重入式锁ReentrantLock
//    private Lock lock = new ReentrantLock();

    @Resource
    private DistributeLockMapper distributeLockMapper;

    @RequestMapping("singleLock")
    @Transactional(rollbackFor = Exception.class)
    public String singleLock() throws Exception{
        log.info("我进入了方法！");
        //基于数据库实现分布式锁（两个进程请求同一个数据库）
        DistributeLock distributeLock = distributeLockMapper.selectDistributeLock("demo");
        if (distributeLock == null){
            throw new Exception("分布式锁找不到");
        }
//        lock.lock();
        log.info("我进入了锁！");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        lock.unlock();
        return "我已经执行完成！";
    }
}
