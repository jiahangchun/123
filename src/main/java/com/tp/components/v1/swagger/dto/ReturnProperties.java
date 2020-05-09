package com.tp.components.v1.swagger.dto;


import java.util.Map;


public class ReturnProperties {

    /**
     * 返回详情
     */
    private Map<String, ReturnProperty> map;

    public Map<String, ReturnProperty> getMap() {
        return map;
    }

    public void setMap(Map<String, ReturnProperty> map) {
        this.map = map;
    }
}
