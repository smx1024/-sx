package com.sx.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sx"})
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.sx.staservice.mapper")
public class StaApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(StaApplication.class, args);
    }
}
