package com.study.distirbuteLock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.study.distirbuteLock.dao")
@EnableScheduling
public class DistributeLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributeLockApplication.class,args);
    }
}
