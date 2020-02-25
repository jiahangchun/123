package com.convert.data.config;

import org.apache.commons.lang3.Validate;

import java.net.URI;


public class DataConfig {

    private URI url;

    public DataConfig(URI dataUrl) {
        this.url = Validate.notNull(dataUrl, "dataUrl must not be null");
    }

    private String originData;

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public String getOriginData() {
        return originData;
    }

    public void setOriginData(String originData) {
        this.originData = originData;
    }
}
