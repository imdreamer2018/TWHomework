package com.thoughtworks.homework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Slf4j
@EnableCaching
@EnableSwagger2
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class Application implements WebMvcConfigurer {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
