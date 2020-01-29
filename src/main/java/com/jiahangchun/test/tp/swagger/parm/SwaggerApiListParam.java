package com.jiahangchun.test.tp.swagger.parm;

import lombok.Data;

@Data
public class SwaggerApiListParam {

    /**
     * 所属的模块
     */
    private String tag;

    /**
     * 该接口唯一标识码
     */
    private String key;

    /**
     * 请求接口
     */
    private String url;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 接口简述
     */
    private String description;
}
