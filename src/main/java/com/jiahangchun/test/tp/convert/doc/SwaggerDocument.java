/*
 * Copyright 2017 Robert Winkler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jiahangchun.test.tp.convert.doc;

import com.google.common.collect.Maps;
import com.jiahangchun.test.tp.common.CommonUtil;
import com.jiahangchun.test.tp.convert.SwaggerConverter;
import com.jiahangchun.test.tp.convert.bo.RequestParamBo;
import com.jiahangchun.test.tp.convert.bo.RequestResultBo;
import com.jiahangchun.test.tp.convert.bo.SwaggerDetailBo;
import com.jiahangchun.test.tp.swagger.dto.ResultData;
import org.apache.commons.lang3.Validate;
import org.assertj.core.util.Lists;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SwaggerDocument extends MarkupComponent<SwaggerDocument.Parameters> {

    public SwaggerDocument(SwaggerConverter.Context context) {
        super(context);
    }

    /**
     * 具体的执行方法
     *
     * @param markupDocBuilder
     * @param parameters
     * @return
     */
    @Override
    public MarkupDocBuilder apply(MarkupDocBuilder markupDocBuilder, Parameters parameters) {
        SwaggerDetailBo swagger = parameters.swagger;
        Map<String, List<ResultData>> definitionVoMap = parameters.definitionVoMap;
        buildDocumentDescription(markupDocBuilder, swagger.getDescription());
        buildDocumentURL(markupDocBuilder, swagger.getUrl());
        buildDocumentMethod(markupDocBuilder, swagger.getMethod());
        buildDocumentRequestParam(markupDocBuilder, swagger, definitionVoMap);
        buildDocumentRequestBody(markupDocBuilder, swagger.getRequestParamBos(), definitionVoMap);
        buildDocumentResponseParam(markupDocBuilder, swagger, definitionVoMap);
        buildDocumentResponseBody(markupDocBuilder, swagger.getRequestResultBos(), definitionVoMap);
        return markupDocBuilder;
    }

    private void buildDocumentResponseBody(
            MarkupDocBuilder markupDocBuilder,
            List<RequestResultBo> responseResultBos,
            Map<String, List<ResultData>> definitionVoMap) {
        if (CommonUtil.isEmpty(responseResultBos)) {
            return;
        }
        //首先对参数中引用的部分设置默认的值
        for (RequestResultBo resultBo : responseResultBos) {
            String ref = resultBo.getRef();
            if (CommonUtil.isEmpty(ref)) {
                continue;
            }
            List<ResultData> resultData = definitionVoMap.get(ref);
            if (CommonUtil.isEmpty(resultData)) {
                return;
            }
            Map<String, Object> refData =
                    resultData.stream().collect(
                            Collectors.toMap(
                                    ResultData::getName,
                                    x -> {
                                        String type = x.getType();
                                        if (CommonUtil.isEmpty(type)) {
                                            return "";
                                        }
                                        String defaultValue = CommonUtil.getDefaultValue(type);
                                        return defaultValue;
                                    },
                                    (k1, k2) -> k2));
            String refDataStr = CommonUtil.generateObjectByFieldToStr(refData);
            resultBo.setRefData(refDataStr);
        }

        //实际转化下第一层的数据
        Map<String, Object> map = Maps.newHashMap();
        for (RequestResultBo resultBo : responseResultBos) {
            String name = resultBo.getName();
            String defaultValue = resultBo.getExample();
            if (CommonUtil.isEmpty(defaultValue)) {
                defaultValue = resultBo.getRefData();
            }
            map.put(name, defaultValue);
        }
        String objStr = CommonUtil.generateObjectByFieldToStr(map);
        markupDocBuilder.documentResponseBody(objStr);
    }

    private void buildDocumentResponseParam(MarkupDocBuilder markupDocBuilder,
                                            SwaggerDetailBo swagger,
                                            Map<String, List<ResultData>> definitionVoMap) {
        List<RequestResultBo> requestResultBos = swagger.getRequestResultBos();
        if (CommonUtil.isEmpty(requestResultBos)) {
            return;
        }
        List<String> columns = Lists.newArrayList("参数名称", "参数类型", "描述说明", "默认值");
        List<List<String>> cells = Lists.newArrayList();
        for (RequestResultBo requestResultBo : requestResultBos) {
            List<String> cc = Lists.newArrayList();
            cc.add(requestResultBo.getName());
            cc.add(requestResultBo.getType());
            cc.add(requestResultBo.getDescription());
            cc.add(requestResultBo.getExample());
            cells.add(cc);
            String ref = requestResultBo.getRef();
            if (CommonUtil.isNotEmpty(ref)) {
                List<ResultData> resultDataList = definitionVoMap.get(ref);
                if (CommonUtil.isNotEmpty(resultDataList)) {
                    List<List<String>> result2Param = resultDataList.stream().map(x -> {
                        List<String> a = Lists.newArrayList();
                        a.add("&emsp; -" + x.getName());
                        a.add("");
                        a.add("");
                        a.add(x.getType());
                        a.add(x.getDescription());
                        a.add(x.getExample());
                        return a;
                    }).collect(Collectors.toList());
                    cells.addAll(result2Param);
                }
            }
        }
        markupDocBuilder.documentResponseParam(" ");
        markupDocBuilder.table(columns, cells);
    }

    private void buildDocumentRequestBody(MarkupDocBuilder markupDocBuilder,
                                          List<RequestParamBo> requestParamVos,
                                          Map<String, List<ResultData>> definitionVoMap) {
        if (CommonUtil.isEmpty(requestParamVos)) {
            return;
        }
        //首先对参数中引用的部分设置默认的值
        for (RequestParamBo requestParamBo : requestParamVos) {
            String ref = requestParamBo.getRef();
            if (CommonUtil.isEmpty(ref)) {
                continue;
            }
            List<ResultData> resultData = definitionVoMap.get(ref);
            if (CommonUtil.isEmpty(resultData)) {
                return;
            }
            Map<String, Object> refData =
                    resultData.stream().collect(
                            Collectors.toMap(
                                    ResultData::getName,
                                    x -> {
                                        String type = x.getType();
                                        if (CommonUtil.isEmpty(type)) {
                                            return "";
                                        }
                                        String defaultValue = CommonUtil.getDefaultValue(type);
                                        return defaultValue;
                                    },
                                    (k1, k2) -> k2));
            String refDataStr = CommonUtil.generateObjectByFieldToStr(refData);
            requestParamBo.setRefData(refDataStr);
        }

        //实际转化下第一层的数据
        Map<String, Object> map = Maps.newHashMap();
        for (RequestParamBo requestParamBo : requestParamVos) {
            String name = requestParamBo.getName();
            String defaultValue = requestParamBo.getDefaultValue();
            if (CommonUtil.isEmpty(defaultValue)) {
                defaultValue = requestParamBo.getRefData();
            }
            map.put(name, defaultValue);
        }
        String objStr = CommonUtil.generateObjectByFieldToStr(map);
        markupDocBuilder.documentRequestBody(objStr);
    }

    //warn:只支持两层结构的转化，多层结构不考虑（入参和出参有必要这么复杂么？）
    //所以如果要用这个工具，拜托不要设计很复杂的结构
    private void buildDocumentRequestParam(
            MarkupDocBuilder markupDocBuilder,
            SwaggerDetailBo swagger,
            Map<String, List<ResultData>> definitionVoMap) {
        List<RequestParamBo> requestParamVos = swagger.getRequestParamBos();
        if (CommonUtil.isEmpty(requestParamVos)) {
            return;
        }
        List<String> columns = Lists.newArrayList("参数名称", "参数位置", "是否必须", "参数类型", "描述说明", "默认值");
        List<List<String>> cells = Lists.newArrayList();
        for (RequestParamBo requestParamBo : requestParamVos) {
            List<String> cc = Lists.newArrayList();
            cc.add(requestParamBo.getName());
            cc.add(requestParamBo.getIn());
            cc.add(requestParamBo.getRequired().toString());
            cc.add(requestParamBo.getType());
            cc.add(requestParamBo.getDescription());
            cc.add(requestParamBo.getDefaultValue());
            cells.add(cc);
            String ref = requestParamBo.getRef();
            if (CommonUtil.isNotEmpty(ref)) {
                List<ResultData> resultDataList = definitionVoMap.get(ref);
                if (CommonUtil.isNotEmpty(resultDataList)) {
                    List<List<String>> result2Param = resultDataList.stream().map(x -> {
                        List<String> a = Lists.newArrayList();
                        a.add("&emsp; -" + x.getName());
                        a.add("");
                        a.add("");
                        a.add(x.getType());
                        a.add(x.getDescription());
                        a.add(x.getExample());
                        return a;
                    }).collect(Collectors.toList());
                    cells.addAll(result2Param);
                }
            }
        }
        markupDocBuilder.documentRequestParam(" ");
        markupDocBuilder.table(columns, cells);
    }

    private void buildDocumentMethod(MarkupDocBuilder markupDocBuilder, HttpMethod method) {
        String methodStr = "";
        if (CommonUtil.isNotEmpty(method)) {
            methodStr = method.name();
        }
        markupDocBuilder.documentMethod(methodStr);
    }

    private void buildDocumentURL(MarkupDocBuilder markupDocBuilder, String url) {
        markupDocBuilder.documentUrl(url);
    }

    private void buildDocumentDescription(MarkupDocBuilder markupDocBuilder, String description) {
        markupDocBuilder.documentDescription(description);
    }


    public static class Parameters {
        private final SwaggerDetailBo swagger;
        private final Map<String, List<ResultData>> definitionVoMap;

        public Parameters(SwaggerDetailBo swagger, Map<String, List<ResultData>> definitionVoMap) {
            this.swagger = Validate.notNull(swagger, "Swagger must not be null");
            this.definitionVoMap = Validate.notNull(definitionVoMap, "DefinitionVoMap must not be null");
        }
    }

    public static SwaggerDocument.Parameters parameters(SwaggerDetailBo swagger, Map<String, List<ResultData>> definitionVoMap) {
        return new SwaggerDocument.Parameters(swagger, definitionVoMap);
    }


}
