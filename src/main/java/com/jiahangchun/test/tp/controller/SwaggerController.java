package com.jiahangchun.test.tp.controller;

import com.alibaba.fastjson.JSON;
import com.jiahangchun.test.tp.swagger.ApiResponseService;
import com.jiahangchun.test.tp.swagger.parm.SwaggerApiListParam;
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
        return JSON.toJSONString(apiResponseService.getOriginSwaggerData(null));
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
}
