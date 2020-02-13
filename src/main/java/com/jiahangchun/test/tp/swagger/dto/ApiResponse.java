package com.jiahangchun.test.tp.swagger.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ApiResponse {

    private String description = null;

    /**
     * 对象的应用
     */
    private Map<String,String> schema;
}
