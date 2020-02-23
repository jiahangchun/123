package com.jiahangchun.test.tp.convert.bo;

import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.List;

@Data
public class SwaggerDetailBo {

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
     * 请求地址
     */
    private String host;

    /**
     * 请求方法
     */
    private HttpMethod method;

    /**
     * 接口简述
     */
    private String description;

    /**
     * 请求参数
     */
    private List<RequestParamBo> requestParamBos;

    /**
     * 返回结果参数
     */
    private List<RequestResultBo> requestResultBos;

}
