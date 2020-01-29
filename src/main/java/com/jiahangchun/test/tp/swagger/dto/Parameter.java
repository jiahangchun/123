package com.jiahangchun.test.tp.swagger.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Parameter {
    private String name = null;
    private String in = null;
    private String description = null;
    private Boolean required = null;
    private String type = null;
    @JSONField(name="default")
    private String defaultValue ;
}
