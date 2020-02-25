package com.swagger.parm;


public class SwaggerApiListParam {

    /**
     * 所属的模块
     */
    private String tag;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
}
