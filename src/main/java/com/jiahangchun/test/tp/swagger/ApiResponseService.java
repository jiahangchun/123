package com.jiahangchun.test.tp.swagger;

import com.jiahangchun.test.tp.common.BizException;

public interface ApiResponseService {

    /**
     * 获取原本的swagger数据
     * @param url
     * @return
     */
    public OpenApi getOriginSwaggerData(String url) throws BizException;

}
