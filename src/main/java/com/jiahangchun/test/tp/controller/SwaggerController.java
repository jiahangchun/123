package com.jiahangchun.test.tp.controller;

import com.alibaba.fastjson.JSON;
import com.jiahangchun.test.tp.swagger.ApiResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class SwaggerController {

    @Autowired
    private ApiResponseService apiResponseService;

    @GetMapping("/seek/origin/data")
    public String seekOriginData() {
        return JSON.toJSONString(apiResponseService.getOriginSwaggerData(null));
    }
}
