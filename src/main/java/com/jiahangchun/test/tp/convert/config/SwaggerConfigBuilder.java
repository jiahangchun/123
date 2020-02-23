package com.jiahangchun.test.tp.convert.config;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import javax.naming.ConfigurationException;

/**
 * 一些全局行的配置
 */
public class SwaggerConfigBuilder {
    //一些系统上的配置
    //比如环境 & 自定义的properties文件内容

    private static final String PROPERTIES_DEFAULT = "io/convert/config/default.properties";

    private static ConfigBO configBO=new ConfigBO();

    public SwaggerConfigBuilder(Configuration configuration) {
        CompositeConfiguration compositeConfiguration = new CompositeConfiguration();
        compositeConfiguration.addConfiguration(new SystemConfiguration());
        compositeConfiguration.addConfiguration(configuration);
//        compositeConfiguration.addConfiguration(getDefaultConfiguration());

        //通过上面获取的properties进行赋值操作
        //configBO
    }

    /**
     * Loads the default properties from the classpath.
     *
     * @return the default properties
     */
    private Configuration getDefaultConfiguration() {
        Configurations configs = new Configurations();
        try {
            return configs.properties(PROPERTIES_DEFAULT);
        } catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
            throw new RuntimeException(String.format("Can't load default properties '%s'", PROPERTIES_DEFAULT), e);
        }
    }

    /**
     * 最终获取一个配置
     * @return
     */
    public ConfigBO build() {
        return configBO;
    }
}
