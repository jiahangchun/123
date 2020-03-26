package com.jiahangchun.config.controller;

import com.jiahangchun.config.config.RetryProperties;
import com.jiahangchun.config.service.TestA;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private TestA testA;

    @GetMapping("/test")
    public String seekOriginData() {
       return testA.getName("1--1");
    }


    @ConditionalOnProperty("spring.cloud.config.fail-fast")
    @ConditionalOnClass({Retryable.class, Aspect.class, AopAutoConfiguration.class})
    @Configuration(proxyBeanMethods = false)
    @EnableRetry(proxyTargetClass = true)
    @Import(AopAutoConfiguration.class)
    @EnableConfigurationProperties(RetryProperties.class)
    protected static class RetryConfiguration {

        @Bean
        @ConditionalOnMissingBean(name = "configServerRetryInterceptor")
        public RetryOperationsInterceptor configServerRetryInterceptor(
                RetryProperties properties) {
            return RetryInterceptorBuilder.stateless()
                    .backOffOptions(
                            properties.getInitialInterval(),
                            properties.getMultiplier(),
                            properties.getMaxInterval())
                    .maxAttempts(properties.getMaxAttempts())
                    .build();
        }

    }

}
