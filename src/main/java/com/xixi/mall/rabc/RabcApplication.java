package com.xixi.mall.rabc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.xixi.mall"})
@EnableFeignClients(basePackages = {"com.xixi.mall.api.**.feign"})
public class RabcApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabcApplication.class, args);
    }

}
