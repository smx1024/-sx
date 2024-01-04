package com.sx.Userservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sx"})
@MapperScan("com.sx.Userservice.mapper")
@EnableDiscoveryClient
public class UserApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(UserApplication.class,args);
    }
}
