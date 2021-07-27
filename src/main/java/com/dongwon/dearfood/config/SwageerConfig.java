package com.dongwon.dearfood.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwageerConfig {
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build();
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("11번가 Restful API 구현 프로젝트").description("11번가 Restful API 구현 프로젝트 - 2021 동원 디어푸드 인턴쉽")
                .version("1.0").build();
    }
}
