package com.convert.config;

import java.util.List;

import static java.util.ServiceLoader.load;
import static org.apache.commons.collections4.IteratorUtils.toList;

public class SwaggerExtensionBuilder {

    /**
     * 插件的一些内容
     */
    private final Context context;

    /**
     * 加载的插件
     */
    private static class Context {
        public final List<SwaggerExtension> swaggerModelExtensions;

        public Context(List<SwaggerExtension> swaggerModelExtensions) {
            this.swaggerModelExtensions = swaggerModelExtensions;
        }
    }

    /**
     * 默认的插件注册中心
     */
    static class DefaultSwaggerExtensionRegistry implements SwaggerExtensionRegister {
        private final Context context;

        DefaultSwaggerExtensionRegistry(Context context) {
            this.context = context;
        }

        @Override
        public List<SwaggerExtension> getSwaggerModelExtensions() {
            return context.swaggerModelExtensions;
        }
    }

    /**
     * 加载这个类的时候就将插件添加进来
     */
    public SwaggerExtensionBuilder() {
        List<SwaggerExtension> swaggerModelExtensions = toList(load(SwaggerExtension.class).iterator());
        this.context = new Context(swaggerModelExtensions);
    }

    /**
     * 返回注册中心实体
     *
     * @return
     */
    public SwaggerExtensionRegister build() {
        return new DefaultSwaggerExtensionRegistry(context);
    }
}
