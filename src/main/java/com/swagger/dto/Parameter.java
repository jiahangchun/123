package com.swagger.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;


public class Parameter {
    private String name = null;
    /**
     * 位于请求的哪一个部位
     */
    private String in = null;
    private String description = null;
    private Boolean required = null;
    private String type = null;

    @JSONField(name="default")
    private String defaultValue ;
    /**
     * 对象的应用
     */
    private Map<String,String> schema;

    private String ref;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Map<String, String> getSchema() {
        return schema;
    }

    public void setSchema(Map<String, String> schema) {
        this.schema = schema;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
