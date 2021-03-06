package com.jiahangchun.test.tp.swagger.dto;

import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.List;

@Data
public class SwaggerApiListDto {

    /**
     * 所属的模块
     */
    private List<String> tags;

    /**
     * 该接口唯一标识码
     */
    private String key;

    /**
     * key Str
     */
    private String keyStr;

    /**
     * 请求接口
     */
    private String url;

    /**
     * 请求地址
     */
    private String host;

    /**
     * 请求方法
     */
    private HttpMethod method;

    /**
     * 接口简述
     */
    private String description;

    /**
     * 参数要求
     */
    private List<Parameter> parameters;

    /**
     * 返回类型
     */
    private ResultData resultData;
}
