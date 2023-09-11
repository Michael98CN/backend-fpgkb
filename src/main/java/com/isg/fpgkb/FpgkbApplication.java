package com.isg.fpgkb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@MapperScan("com.isg.fpgkb.mapper")
@SpringBootApplication
public class FpgkbApplication {

    public static void main(String[] args) {
        SpringApplication.run(FpgkbApplication.class, args);
    }

}
