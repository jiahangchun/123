package com.jiahangchun.test.tp.convert.bo;

import lombok.Data;

@Data
public class RequestParamBo {

    private String name;

    private Boolean required = null;

    private String type = null;

    private String defaultValue;

    private String ref;

    private String description = null;

    private String in = null;

    /*************************生成文档的过程中产生的数据****************************/

    /**
     * 当ref存在的时候，我们为了它模拟出了一组特殊的数据
     */
    private String refData;
}
