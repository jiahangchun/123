package com.jiahangchun.test.tp.swagger.vo;

import lombok.Data;

import java.util.List;

@Data
public class MockRequestVo {

    /**
     * 所属的模块
     */
    private List<String> tags;

    /**
     * 模块的文案
     */
    private String tagStr;

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

    /**
     * mockRequest 请求结果 JSON
     */
    private String mockRequestResult;

    /**
     * mockRequest 请求 JSON
     */
    private String mockRequest;
}
