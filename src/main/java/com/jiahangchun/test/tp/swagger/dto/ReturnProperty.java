package com.jiahangchun.test.tp.swagger.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Map;

@Data
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
}
