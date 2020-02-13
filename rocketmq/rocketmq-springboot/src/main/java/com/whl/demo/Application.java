package com.whl.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @program: cache-redis
 * @description: 启动类
 * @author: Mr.Wang
 * @create: 2018-09-21 09:08
 **/
@EnableAutoConfiguration
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // 去除相应的自动化配置
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
