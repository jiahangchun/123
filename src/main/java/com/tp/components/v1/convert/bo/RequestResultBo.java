package com.tp.components.v1.convert.bo;



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
    private Object refData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Object getRefData() {
        return refData;
    }

    public void setRefData(Object refData) {
        this.refData = refData;
    }
}
