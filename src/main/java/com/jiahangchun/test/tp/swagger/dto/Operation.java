package com.jiahangchun.test.tp.swagger.dto;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class Operation {
    private List<String> tags = null;
    private String summary = null;
    private String operationId = null;
    private List<String> consumes;
    private List<String> produces;
    private List<Parameter> parameters = null;
    private LinkedHashMap<String, ApiResponse> responses = null;
}
