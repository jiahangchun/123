package com.swagger.dto;

import com.common.HttpMethod;

import java.util.List;


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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public ResultData getResultData() {
        return resultData;
    }

    public void setResultData(ResultData resultData) {
        this.resultData = resultData;
    }
}
