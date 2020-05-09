package com.tp.components.v1.convert.data;

import com.tp.components.v1.common.BizException;
import com.tp.components.v1.common.JMCommonUtil;
import com.tp.components.v1.convert.data.config.DataConfig;
import com.tp.components.v1.convert.data.format.SwaggerDataFormat;
import org.apache.commons.lang3.Validate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class SwaggerDataFetcher {

    private final Context context;

    public SwaggerDataFetcher(Context context) {
        this.context = context;
    }


    public static Builder from(URL swaggerURL) {
        Validate.notNull(swaggerURL, "swaggerURL must not be null");
        return new Builder(swaggerURL);
    }

    /**
     * 调用解析工具生成对象
     * @return
     */
    public SwaggerDataFormat.Result toFormatData(){
        SwaggerDataFormat.Parameters parameters=new SwaggerDataFormat.Parameters(context.originData);
        return new SwaggerDataFormat().apply(parameters);
    }


    public static class Builder {

        private final URI dataUrl;

        Builder(URL swaggerUrl) {
            try {
                this.dataUrl = swaggerUrl.toURI();
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("swaggerURL is in a wrong format", e);
            }
        }

        public SwaggerDataFetcher build() {
            Context context = null;
            try {
                DataConfig dataConfig = new DataConfig(dataUrl);
                //采集原始数据
                String data = this.fetchOriginData(dataConfig);
                context = new Context(dataConfig.getUrl(), data);
            } catch (Exception e) {
                throw new BizException("can not form data.");
            }
            return new SwaggerDataFetcher(context);
        }

        public String fetchOriginData(DataConfig dataConfig) throws Exception {
            URI uri = dataConfig.getUrl();
            if (JMCommonUtil.isEmpty(uri)) {
                throw new BizException("url miss.");
            }
            String data = JMCommonUtil.urlToString(uri.toString());
            return data;
        }

    }


    public static class Context {
        private URI uri;
        private String originData;

        public Context(URI uri, String originData) {
            this.uri = uri;
            this.originData = originData;
        }

        public URI getUri() {
            return uri;
        }

        public void setUri(URI uri) {
            this.uri = uri;
        }

        public void setOriginData(String originData) {
            this.originData = originData;
        }

        public String getOriginData() {
            return this.originData ;
        }
    }

}
