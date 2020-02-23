package com.jiahangchun.test.tp.convert.data.config;

import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.net.URI;

@Data
public class DataConfig {

    private URI url;

    public DataConfig(URI dataUrl) {
        this.url = Validate.notNull(dataUrl, "dataUrl must not be null");
    }

    private String originData;
}
