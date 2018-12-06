package com.kilanowski.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableMongoRepositories(basePackages = "com.kilanowski.*")
@ComponentScan(basePackages = {"com.kilanowski.*"})
public class LoadApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LoadApplication.class, args);
    }
}
