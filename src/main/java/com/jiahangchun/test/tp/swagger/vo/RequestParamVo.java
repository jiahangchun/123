package com.jiahangchun.test.tp.swagger.vo;

import lombok.Data;

@Data
public class RequestParamVo {

    private String name;

    private Boolean required = null;

    private String type = null;

    private String defaultValue ;

    private String ref;

    private String description = null;

    private String in = null;

}
