package com.jiahangchun.test.tp.swagger.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Map;

@Data
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
}
