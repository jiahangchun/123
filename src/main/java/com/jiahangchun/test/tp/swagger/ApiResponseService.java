package com.jiahangchun.test.tp.swagger;

import com.jiahangchun.test.tp.common.BizException;
import com.jiahangchun.test.tp.swagger.dto.OpenApi;
import com.jiahangchun.test.tp.swagger.parm.SwaggerApiListParam;
import com.jiahangchun.test.tp.swagger.vo.SwaggerApiListVo;

import java.util.List;

public interface ApiResponseService {

    /**
     * 获取原本的swagger数据
     *
     * @param url
     * @return
     */
    public OpenApi getOriginSwaggerData(String url) throws BizException;

    /**
     * 返回给页面的数据
     *
     * @param
     * @return
     * @throws BizException
     */
    public List<SwaggerApiListVo> getSwaggerData(SwaggerApiListParam swaggerApiListParam) throws BizException;

    /**
     * 获取详情
     *
     * @param key
     * @return
     */
    public SwaggerApiListVo getSwaggerVo(String key);
}
