package com.jiahangchun.test.tp.swagger.dto;

import lombok.Data;

@Data
public class ResultData {

    private String name;

    private String example;

    private String type;

    //上面三个有点奇怪

    private Integer code;

    private String description;

    /**
     * 可能是其他的引用
     */
    private String ref;
}
