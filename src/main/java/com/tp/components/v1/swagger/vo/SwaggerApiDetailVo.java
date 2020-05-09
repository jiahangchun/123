package com.tp.components.v1.swagger.vo;

import com.tp.components.v1.common.HttpMethod;

import java.util.List;

public class SwaggerApiDetailVo {

    /**
     * 所属的模块
     */
    private List<String> tags;

    /**
     * 模块的文案
     */
    private String tagStr;

    /**
     * 该接口唯一标识码
     */
    private String key;

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
     * 请求参数
     */
    private List<RequestParamVo> requestParamVos;

    /**
     * 返回结果参数
     */
    private List<RequestResultVo> requestResultVos;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTagStr() {
        return tagStr;
    }

    public void setTagStr(String tagStr) {
        this.tagStr = tagStr;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public List<RequestParamVo> getRequestParamVos() {
        return requestParamVos;
    }

    public void setRequestParamVos(List<RequestParamVo> requestParamVos) {
        this.requestParamVos = requestParamVos;
    }

    public List<RequestResultVo> getRequestResultVos() {
        return requestResultVos;
    }

    public void setRequestResultVos(List<RequestResultVo> requestResultVos) {
        this.requestResultVos = requestResultVos;
    }
}
