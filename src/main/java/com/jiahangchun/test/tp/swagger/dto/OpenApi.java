package com.jiahangchun.test.tp.swagger.dto;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
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
}
