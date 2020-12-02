package com.zyx.payimooc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.zyx.payimooc.dao")
public class PayimoocApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayimoocApplication.class, args);
    }

}
