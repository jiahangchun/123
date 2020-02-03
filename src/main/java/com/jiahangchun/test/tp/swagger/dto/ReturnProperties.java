package com.jiahangchun.test.tp.swagger.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ReturnProperties {

    /**
     * 返回详情
     */
    private Map<String,ReturnProperty> map;
}
