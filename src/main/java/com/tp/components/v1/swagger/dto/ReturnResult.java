package com.tp.components.v1.swagger.dto;


import java.util.Map;



public class ReturnResult {

    private String type;

    private Map<String,ReturnProperty> properties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, ReturnProperty> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, ReturnProperty> properties) {
        this.properties = properties;
    }
}
