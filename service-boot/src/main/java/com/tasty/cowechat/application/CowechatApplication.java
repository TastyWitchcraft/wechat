package com.tasty.cowechat.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tasty")
@MapperScan("com.tasty.mybatis.mapper")
public class CowechatApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CowechatApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CowechatApplication.class);
    }
}
