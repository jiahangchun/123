package com.jiahangchun.test.tp.swagger;

import com.jiahangchun.test.tp.common.BizException;
import com.jiahangchun.test.tp.swagger.dto.OpenApi;
import com.jiahangchun.test.tp.swagger.dto.SwaggerApiListDto;
import com.jiahangchun.test.tp.swagger.parm.SwaggerApiListParam;
import com.jiahangchun.test.tp.swagger.vo.DefinitionVo;
import com.jiahangchun.test.tp.swagger.vo.MockRequestVo;
import com.jiahangchun.test.tp.swagger.vo.SwaggerApiDetailVo;
import com.jiahangchun.test.tp.swagger.vo.SwaggerApiListVo;

import java.util.List;

public interface ApiResponseService {

    /**
     * 获取原本的swagger数据
     *
     * @return
     */
    public OpenApi getOriginSwaggerData() throws BizException;

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
