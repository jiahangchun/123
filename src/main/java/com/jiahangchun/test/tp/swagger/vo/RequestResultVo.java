package com.jiahangchun.test.tp.swagger.vo;

import lombok.Data;

@Data
public class RequestResultVo {

    private String name;

    private String example;

    private String type;

    private String description;

    /**
     * 可能是其他的引用
     */
    private String ref;

}
