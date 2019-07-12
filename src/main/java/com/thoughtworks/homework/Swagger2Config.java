package com.thoughtworks.homework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.thoughtworks.homework"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                //页面标题
                .title("ThoughtWorks")
                //描述
                .description("HomeWork接口文档")
                //创建人
                .contact(new Contact("杨乾", "", ""))
                //版本号
                .version("1.0")
                .build();

    }
}
