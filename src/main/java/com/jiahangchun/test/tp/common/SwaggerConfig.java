//package com.jiahangchun.test.tp.common;
//
//import io.swagger.annotations.Api;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * swagger相关配置
// * http://localhost:8080/platform/swagger-ui.html
// */
//@Configuration
//@EnableSwagger2
//@Slf4j
//public class SwaggerConfig {
//
//
//    /**
//     * 需求版本变更
//     */
//    public static final String COMPLETE_VERSION = "全部列表";
//    public static final String PURCHASE_UPDATE_TAG = "采购改造需求（一）";
//    public static final String CHAT3 = "chat改造（三）";
//    public static final String PRICE_QUERY = "商品价格查询";
//    public static final String SHIP_ID = "多地址改造";
//
//    @Bean
//    public Docket createBaseApi() {
//        List<Parameter> pars = new ArrayList<>();
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .globalOperationParameters(pars)
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    @Value("${server.port}")
//    String port;
//
//    @Value("${project.version:1.1.0}")
//    String version;
//
//    @Value("${spring.application.name:1234TP}")
//    String appName;
//
//
//    private ApiInfo apiInfo() {
//
//
//        return new ApiInfoBuilder()
//                .title(appName)
//                .description("项目开发文档")
//                .version("1.0.0")
//                .termsOfServiceUrl("url")
//                .version(version)
//                .build();
//    }
//
//
//}
