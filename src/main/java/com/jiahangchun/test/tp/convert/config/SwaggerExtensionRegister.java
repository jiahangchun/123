package com.jiahangchun.test.tp.convert.config;

import lombok.Data;

import java.util.List;

//主要是为了存放一些插件
public interface SwaggerExtensionRegister {

    List<SwaggerExtension> getSwaggerModelExtensions();
}
