package com.jiahangchun.test.tp.controller;

import com.alibaba.fastjson.JSON;
import com.jiahangchun.test.tp.swagger.ApiResponseService;
import com.jiahangchun.test.tp.swagger.dto.SwaggerApiListDto;
import com.jiahangchun.test.tp.swagger.parm.SwaggerApiListParam;
import com.jiahangchun.test.tp.swagger.vo.DefinitionVo;
import com.jiahangchun.test.tp.swagger.vo.MockRequestVo;
import com.jiahangchun.test.tp.swagger.vo.SwaggerApiDetailVo;
import com.jiahangchun.test.tp.swagger.vo.SwaggerApiListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
public class SwaggerController {

    @Autowired
    private ApiResponseService apiResponseService;

    /**
     * 从swagger中获取结构化数据
     *
     * @return
     */
    @GetMapping("/seek/origin/data")
    public String seekOriginData() {
        return JSON.toJSONString(apiResponseService.getOriginSwaggerData());
    }

    /**
     * 查询swagger的列表
     *
     * @return
     */
    @PostMapping("/query/swagger/api/list")
    public List<SwaggerApiListVo> queryApiList(@RequestBody(required = false) SwaggerApiListParam swaggerApiListParam) {
        return apiResponseService.getSwaggerData(swaggerApiListParam);
    }

    /**
     * 获取某个接口的详情
     *
     * @param key
     * @return
     */
    @GetMapping("/get/swagger/api")
    public SwaggerApiDetailVo getApiVo(@RequestParam("key") String key) {
        return apiResponseService.getSwaggerVo(key);
    }

    /**
     * 获取所有额外定义的类
     *
     * @param key
     * @return
     */
    @GetMapping("/get/definition/dto")
    public DefinitionVo getDefinition(@RequestParam("key") String key) {
        return apiResponseService.getDefinitionVo(key);
    }


    /**
     * 请求
     *
     * @param swaggerApiDetailVo
     * @return
     */
    @PostMapping("/mock/request")
    public MockRequestVo mockRequest(@RequestBody SwaggerApiDetailVo swaggerApiDetailVo) {
        return apiResponseService.mockRequest(swaggerApiDetailVo);
    }


}
