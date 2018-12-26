package com.kilanowski.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableSpringDataWebSupport
@EnableMongoRepositories(basePackages = "com.kilanowski.app.*")
@ComponentScan(basePackages = {"com.kilanowski.*"})
public class LoanApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LoanApplication.class, args);
    }
}
