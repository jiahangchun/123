package com.jiahangchun.test.tp.swagger;

import lombok.Data;

import java.util.Map;

@Data
public class Info {

    private String title = null;
    private String description = null;
    private String termsOfService = null;
    private String version = null;
    private Map<String, Object> extensions = null;

}
