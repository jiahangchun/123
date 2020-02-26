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
package com.convert.doc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.common.HttpMethod;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.common.CommonUtil;
import com.convert.SwaggerConverter;
import com.convert.bo.RequestParamBo;
import com.convert.bo.RequestResultBo;
import com.convert.bo.SwaggerDetailBo;
import com.swagger.dto.ResultData;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    /**
     * 混乱的格式修正
     *
     * @param data
     * @return
     */
    private String generatorJsonData(String data) {
        if (CommonUtil.isEmpty(data)) {
            return "";
        }
        if (Objects.equals(data, "null")) {
            return "Sorry, we can't combine JSON data based on the given parameters.\n" +
                    "The reason may be that there is no return value in itself, or the data structure may be too complex.";
        }
        String result = "";
        //工具的问题
        result = data.replaceAll("$cglib_prop_", "");
        result = data.replaceAll("\"\\{", "\\{");
        result = result.replaceAll("}\"", "}");
        result = result.replaceAll("\\\\", "");

        try {
            JSONObject object = JSONObject.parseObject(result);
            result = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {
            //给的格式解析不了，老老实实用原来的吧
        }
        return result;
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
            Object refDataStr = CommonUtil.generateObjectByField(refData);
            resultBo.setRefData(refDataStr);
        }

        //实际转化下第一层的数据
        Map<String, Object> map = Maps.newHashMap();
        for (RequestResultBo resultBo : responseResultBos) {
            String name = resultBo.getName();
            Object defaultValue = this.genDefaultValue(resultBo.getExample(), resultBo.getRefData());
            map.put(name, this.formatValue(resultBo.getType(), defaultValue));
        }
        String objStr = CommonUtil.generateObjectByFieldToStr(map);
        markupDocBuilder.documentResponseBody(this.generatorJsonData(objStr));
    }

    /**
     * 按照类型组织默认值
     *
     * @param type
     * @param defaultValue
     */
    private Object formatValue(String type, Object defaultValue) {
        Object result = defaultValue;
        if (CommonUtil.isNotEmpty(type)) {
            switch (type) {
                case "array":
                    List<Object> defList = Lists.newArrayList();
                    for (int i = 0; i < 3; i++) {
                        defList.add(JSON.toJSONString(defaultValue));
                    }
                    result = defList;
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    /**
     * 获取默认值
     *
     * @param defaultValue
     * @param refData
     * @return
     */
    private Object genDefaultValue(String defaultValue, Object refData) {
        if (CommonUtil.isEmpty(defaultValue)) {
            return refData;
        }
        return defaultValue;
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
                                    }, (k1, k2) -> k2));
            Object refDataStr = CommonUtil.generateObjectByField(refData);
            requestParamBo.setRefData(refDataStr);
        }

        //实际转化下第一层的数据
        String objStr = this.formRequestBody(requestParamVos);

        markupDocBuilder.documentNewLine();
        markupDocBuilder.documentRequestBody(this.generatorJsonData(objStr));
        markupDocBuilder.documentNewLine();
    }

    /**
     * 组合请求body
     *
     * @param requestParamVos
     * @return
     */
    private String formRequestBody(List<RequestParamBo> requestParamVos) {
        Map<String, Object> map = Maps.newHashMap();
        for (RequestParamBo requestParamBo : requestParamVos) {
            String in = requestParamBo.getIn();
            String type = requestParamBo.getType();
            String name = requestParamBo.getName();
            Object defaultValue = this.genDefaultValue(requestParamBo.getDefaultValue(), requestParamBo.getRefData());
            Object actVal = this.formatValue(type, defaultValue);
            if (CommonUtil.isNotEmpty(in)) {
                //排除放在请求头里面的一些参数
                if (Objects.equals(in, "header")) {
                    continue;
                }
                //如果已经是requestBody请求方式，那么直接返回
                //这里不支持复杂的操作
                if (Objects.equals(in, "body")) {
                    return JSON.toJSONString(actVal);
                }
            }
            map.put(name, actVal);
        }
        return CommonUtil.generateObjectByFieldToStr(map);
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
            Boolean required = requestParamBo.getRequired();
            if (CommonUtil.isEmpty(required)) {
                required = Boolean.FALSE;
            }
            cc.add(required.toString());
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
        markupDocBuilder.documentNewLine();
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
