package com.v13.itemweb;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class ItemWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemWebApplication.class, args);
    }

}
