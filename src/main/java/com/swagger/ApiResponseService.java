package com.swagger;

import com.swagger.dto.OpenApi;
import com.swagger.parm.SwaggerApiListParam;
import com.swagger.vo.DefinitionVo;
import com.swagger.vo.MockRequestVo;
import com.swagger.vo.SwaggerApiDetailVo;
import com.swagger.vo.SwaggerApiListVo;

import java.util.List;

public interface ApiResponseService {

    /**
     * 获取原本的swagger数据
     *
     * @return
     */
    public OpenApi getOriginSwaggerData();

    /**
     * 返回给页面的数据
     *
     * @param
     * @return
     */
    public List<SwaggerApiListVo> getSwaggerData(SwaggerApiListParam swaggerApiListParam) ;

    /**
     * 获取详情
     *
     * @param key
     * @return
     */
    public SwaggerApiDetailVo getSwaggerVo(String key);

    /**
     * 获取某个定义的类
     *
     * @param definitionKey
     * @return
     */
    public DefinitionVo getDefinitionVo(String definitionKey);

    /**
     * 请求
     *
     * @param swaggerApiDetailVo
     * @return
     */
    public MockRequestVo mockRequest(SwaggerApiDetailVo swaggerApiDetailVo);

}
