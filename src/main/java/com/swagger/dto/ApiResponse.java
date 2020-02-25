package com.swagger.dto;


import java.util.Map;


public class ApiResponse {

    private String description = null;

    /**
     * 对象的应用
     */
    private Map<String,String> schema;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getSchema() {
        return schema;
    }

    public void setSchema(Map<String, String> schema) {
        this.schema = schema;
    }
}
