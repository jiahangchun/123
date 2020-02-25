package com.swagger.vo;


import java.util.List;


public class MockRequestVo {

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
     * 请求方法
     */
    private String method;

    /**
     * 接口简述
     */
    private String description;

    /**
     * mockRequest 请求结果 JSON
     */
    private String mockRequestResult;

    /**
     * mockRequest 请求 JSON
     */
    private String mockRequest;

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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMockRequestResult() {
        return mockRequestResult;
    }

    public void setMockRequestResult(String mockRequestResult) {
        this.mockRequestResult = mockRequestResult;
    }

    public String getMockRequest() {
        return mockRequest;
    }

    public void setMockRequest(String mockRequest) {
        this.mockRequest = mockRequest;
    }
}
