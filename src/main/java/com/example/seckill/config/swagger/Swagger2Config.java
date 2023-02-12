package com.example.seckill.config.swagger;

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

/**
 * Swagger2接口文档配置类
 *
 * @Author lizongzai
 * @Since 1.0.0
 */
@Configuration
//@EnableWebMvc
@EnableSwagger2
public class Swagger2Config {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .enable(true)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.example.seckill.controller"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {

    return new ApiInfoBuilder().title("Restful API's Document")
        .description("API Endpoint Decoration")
        .contact(new Contact("DevTeam", "http://localhost:8080/doc.html", "devteam@gmail.com"))
        .license("Apache 2.0")
        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
        .version("1.0")
        .build();
  }

}