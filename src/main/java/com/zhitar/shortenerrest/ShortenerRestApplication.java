package com.zhitar.shortenerrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ShortenerRestApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(ShortenerRestApplication.class, args);
    }

}
