package com.jiahangchun.test.tp.swagger.dto;

import lombok.Data;

import java.util.Map;


@Data
public class ReturnResult {

    private String type;

    private Map<String,String> properties;

}
