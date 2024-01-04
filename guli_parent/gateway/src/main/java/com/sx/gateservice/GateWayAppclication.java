package com.sx.gateservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GateWayAppclication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(GateWayAppclication.class,args);
    }
}
