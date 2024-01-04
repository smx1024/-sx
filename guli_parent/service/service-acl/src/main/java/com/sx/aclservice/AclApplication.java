package com.sx.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ITNXD
 * @create 2021-11-18 15:34
 */
@EnableDiscoveryClient
@ComponentScan("com.sx")
@MapperScan("com.sx.aclservice.mapper")
@SpringBootApplication
public class AclApplication {

    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }
}
