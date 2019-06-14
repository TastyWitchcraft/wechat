package com.tasty.cowechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tasty")
@MapperScan("com.tasty.mybatis.mapper")
public class CowechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(CowechatApplication.class, args);
    }

}
