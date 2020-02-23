package com.jiahangchun.test.tp.convert.bo;

import lombok.Data;

@Data
public class RequestResultBo {

    private String name;

    private String example;

    private String type;

    private String description;

    /**
     * 可能是其他的引用
     */
    private String ref;

    /*************************生成文档的过程中产生的数据****************************/

    /**
     * 当ref存在的时候，我们为了它模拟出了一组特殊的数据
     */
    private String refData;

}
