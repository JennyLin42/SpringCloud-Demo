package com.pp.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.ApiInfo;

import java.util.ArrayList;

/**
 *  访问连接：http://localhost:6001/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
//    @Bean //配置docket以配置Swagger具体参数
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2);
//    }

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("联系人名字", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱");
        return new ApiInfo(
                "Swagger学习",// 标题
                "学习演示如何配置Swagger",// 描述
                "v1.0", // 版本
                "http://terms.service.url/组织链接",// 组织链接
                contact,// 联系人信息
                "Apach 2.0 许可",// 许可
                "许可链接" // 许可连接
        );
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }


}
