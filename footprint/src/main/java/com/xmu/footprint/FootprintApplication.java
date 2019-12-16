package com.xmu.footprint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableEurekaServer
@EnableFeignClients
/**
 * @author
 */
public class FootprintApplication {
    public static void main(String[] args) {
        SpringApplication.run(FootprintApplication.class, args);
    }

}
