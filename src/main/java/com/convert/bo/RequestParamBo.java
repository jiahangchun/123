package com.convert.bo;



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
    private Object refData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public Object getRefData() {
        return refData;
    }

    public void setRefData(Object refData) {
        this.refData = refData;
    }
}
