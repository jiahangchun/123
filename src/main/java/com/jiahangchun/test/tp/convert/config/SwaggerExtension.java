package com.jiahangchun.test.tp.convert.config;

import com.jiahangchun.test.tp.convert.SwaggerConverter;
import com.jiahangchun.test.tp.convert.bo.SwaggerDetailBo;

public abstract class SwaggerExtension implements Extension {

    protected SwaggerConverter.Context globalContext;

    /**
     * Global context lazy initialization
     *
     * @param globalContext Global context
     */
    public void setGlobalContext(SwaggerConverter.Context globalContext) {
        this.globalContext = globalContext;
        init(globalContext);
    }

    /**
     * Overridable init event listener.
     *
     * @param globalContext Global context
     */
    public void init(SwaggerConverter.Context globalContext) {
        /* must be left empty */
    }

    /**
     * 实际请求执行
     * @param swaggerDetailBo
     */
    public abstract void apply(SwaggerDetailBo swaggerDetailBo);

}
