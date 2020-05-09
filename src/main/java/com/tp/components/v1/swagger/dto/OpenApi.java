package com.tp.components.v1.swagger.dto;


import java.util.LinkedHashMap;
import java.util.List;


public class OpenApi {

    private String swagger;

    private Info info;

    private String host;

    /**
     * 前缀
     */
    private String basePath;

    private List<Tag> tags = null;

    private LinkedHashMap<String, PathItem> paths;

    private LinkedHashMap<String, ReturnResult> definitions;

    public String getSwagger() {
        return swagger;
    }

    public void setSwagger(String swagger) {
        this.swagger = swagger;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public LinkedHashMap<String, PathItem> getPaths() {
        return paths;
    }

    public void setPaths(LinkedHashMap<String, PathItem> paths) {
        this.paths = paths;
    }

    public LinkedHashMap<String, ReturnResult> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(LinkedHashMap<String, ReturnResult> definitions) {
        this.definitions = definitions;
    }
}
