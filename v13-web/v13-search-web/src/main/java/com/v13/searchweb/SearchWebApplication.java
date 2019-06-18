package com.v13.searchweb;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class SearchWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchWebApplication.class, args);
    }

}
