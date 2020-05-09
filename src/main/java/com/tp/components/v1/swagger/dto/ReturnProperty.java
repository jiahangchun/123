package com.tp.components.v1.swagger.dto;


import java.util.Map;


public class ReturnProperty {

    private String type;

    /**
     * 描述
     */
    private String description;


    private Map<String, String> items;

    /**
     * 例子
     */
    private String example;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getItems() {
        return items;
    }

    public void setItems(Map<String, String> items) {
        this.items = items;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
