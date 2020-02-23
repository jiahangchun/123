package com.jiahangchun.test.tp.convert.data.format;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiahangchun.test.tp.common.CommonUtil;
import com.jiahangchun.test.tp.convert.bo.RequestParamBo;
import com.jiahangchun.test.tp.convert.bo.RequestResultBo;
import com.jiahangchun.test.tp.convert.bo.SwaggerDetailBo;
import com.jiahangchun.test.tp.swagger.dto.*;
import com.jiahangchun.test.tp.swagger.vo.RequestResultVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.assertj.core.util.Lists;
import org.springframework.http.HttpMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class SwaggerDataFormat extends DataFormat<SwaggerDataFormat.Result> {

    private static final String SEP = "###";

    @Override
    public SwaggerDataFormat.Result apply(SwaggerDataFormat.Parameters parameters) {
        //获取原始的数据
        String data = parameters.data;
        Result result = new Result(data);
        //将数据转换成对象格式
        OpenApi openApi = transform2Obj(data);
        //针对openApi解析数据
        this.format(openApi, result);
        return result;
    }

    /**
     * 转换
     *
     * @param swaggerDetailBo
     * @param result
     */
    public static SwaggerDetailBo transform(SwaggerDetailBo swaggerDetailBo, SwaggerDataFormat.Result result) {
        SwaggerApiListDto swaggerApiListDto = result.getSwaggerApiListDto();
        Map<String, List<ResultData>> definitionVoMap = result.getDefinitionMap();
        swaggerDetailBo = CommonUtil.copyProperties(swaggerApiListDto, SwaggerDetailBo.class);

        //填充一些请求参数
        List<RequestParamBo> requestParamBos = Lists.newArrayList();
        List<Parameter> parameterList = swaggerApiListDto.getParameters();
        for (Parameter parameter : parameterList) {
            RequestParamBo paramBo = CommonUtil.copyProperties(parameter, RequestParamBo.class);
            requestParamBos.add(paramBo);
        }
        swaggerDetailBo.setRequestParamBos(requestParamBos);

        //填充返回参数
        ResultData resultData = swaggerApiListDto.getResultData();
        String ref = resultData.getRef();
        if (CommonUtil.isNotEmpty(ref)) {
            String refResult = getInner(ref);
            if (CommonUtil.isNotEmpty(refResult)) {
                ref = refResult;
            }
            List<ResultData> relDataList = definitionVoMap.get(ref);
            if (CommonUtil.isNotEmpty(relDataList)) {
                List<RequestResultBo> requestResultBos = new ArrayList<>();
                for (ResultData sample : relDataList) {
                    RequestResultBo requestResultBo = CommonUtil.copyProperties(sample, RequestResultBo.class);
                    requestResultBos.add(requestResultBo);
                }
                swaggerDetailBo.setRequestResultBos(requestResultBos);
            }
        }

        return swaggerDetailBo;
    }

    /**
     * 查询这个依赖的最底层的结构
     * 为什么 我用正则 和 浏览器里面的正则 不一样？
     *
     * @param line
     * @return
     */
    public static String getInner(String line) {
        StringBuilder innerStr = new StringBuilder();
        Boolean startInner = Boolean.FALSE;
        for (char s : line.toCharArray()) {
            if (Objects.equals(s, "»".charAt(0))) {
                return innerStr.toString();
            }
            if (Objects.equals(s, "«".charAt(0))) {
                innerStr = new StringBuilder();
                startInner = Boolean.TRUE;
            } else {
                if (startInner) {
                    innerStr.append(s);
                }
            }
        }
        return null;
    }

    /**
     * 转换
     *
     * @param openApi
     * @param result
     */
    private void format(OpenApi openApi, Result result) {
        String host = openApi.getHost();
        String platform = openApi.getBasePath();
        //解析出对应的列表
        List<SwaggerApiListDto> swaggerApiListDtos = getSwaggerApiListDtos(openApi);
        //添加基本信息
        swaggerApiListDtos.stream().forEach(x -> {
            x.setUrl(platform + x.getUrl());
            x.setHost(host);
        });
        //获取定义的对象
        Map<String, List<ResultData>> definitionMap = queryDefinitionMap(openApi);
        //将获取到的信息封装到结果参数里面
        result.formatDefinitionMap(definitionMap).formatSwaggerApiList(swaggerApiListDtos);
    }

    /**
     * 查询定义的结构
     *
     * @param openApi
     * @return
     */
    public static Map<String, List<ResultData>> queryDefinitionMap(OpenApi openApi) {
        //解析定义的类
        Map<String, List<ResultData>> definitionMap = new HashMap<>();
        LinkedHashMap<String, ReturnResult> resultLinkedHashMap = openApi.getDefinitions();
        for (Map.Entry<String, ReturnResult> entry : resultLinkedHashMap.entrySet()) {
            String returnKey = entry.getKey();
            ReturnResult returnResult = entry.getValue();
            if (CommonUtil.isEmpty(returnKey) || CommonUtil.isEmpty(returnResult)) {
                continue;
            }
            String type = returnResult.getType();
            Map<String, ReturnProperty> propertyMap = returnResult.getProperties();
            List<ResultData> resultDataList = new ArrayList<>();
            if (Objects.equals(type, "object")) {
                if (CommonUtil.isNotEmpty(propertyMap)) {
                    for (Map.Entry<String, ReturnProperty> propertyEntry : propertyMap.entrySet()) {
                        String returnName = propertyEntry.getKey();
                        ReturnProperty returnProperty = propertyEntry.getValue();
                        String returnType = returnProperty.getType();
                        String description = returnProperty.getDescription();
                        String example = returnProperty.getExample();
                        String ref = "";
                        Map<String, String> items = returnProperty.getItems();
                        if (CommonUtil.isNotEmpty(items)) {
                            ref = items.get("ref");
                        }
                        ResultData resultData = new ResultData();
                        resultData.setName(returnName);
                        resultData.setDescription(description);
                        resultData.setExample(example);
                        resultData.setType(returnType);
                        resultData.setRef(ref);
                        resultDataList.add(resultData);
                    }
                }
            } else {
                //TODO 其他情况之后再加
            }
            definitionMap.put(returnKey, resultDataList);
        }
        return definitionMap;
    }


    public static List<SwaggerApiListDto> getSwaggerApiListDtos(OpenApi openApi) {
        LinkedHashMap<String, PathItem> map = openApi.getPaths();
        List<SwaggerApiListDto> swaggerApiListDtoList = new ArrayList<>(map.size());
        try {
            for (Map.Entry<String, PathItem> entry : map.entrySet()) {
                String url = entry.getKey();
                PathItem pathItem = entry.getValue();
                if (CommonUtil.isEmpty(pathItem)) {
                    continue;
                }
                try {
                    List<SwaggerApiListDto> swaggerApiListDto = formApiInfo(pathItem, url);
                    swaggerApiListDtoList.addAll(swaggerApiListDto);
                } catch (Exception e) {
                    log.error("can not gen apiList for {}", e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            return Lists.newArrayList();
        }
        return swaggerApiListDtoList;
    }

    private static List<SwaggerApiListDto> formApiInfo(PathItem pathItem, String url) throws Exception {
        if (CommonUtil.isEmpty(pathItem)) {
            return new ArrayList<>();
        }
        List<SwaggerApiListDto> apiListDtoList = new ArrayList<>();
        Field[] fields = pathItem.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (Objects.equals(Operation.class.getTypeName(),
                    field.getGenericType().getTypeName())) {
                field.setAccessible(true);
                String methodName = field.getName();
                Method m = (Method) pathItem.getClass().getMethod(
                        "get" + getMethodName(field.getName()));
                Operation operation = (Operation) m.invoke(pathItem);
                if (CommonUtil.isEmpty(operation) || CommonUtil.isEmpty(methodName)) {
                    continue;
                }
                HttpMethod method = CommonUtil.getMethodByName(methodName);
                if (CommonUtil.isEmpty(method)) {
                    continue;
                }
                SwaggerApiListDto apiListDto = resolvingPathItem(operation, url, method);
                if (CommonUtil.isEmpty(apiListDto)) {
                    continue;
                }
                apiListDtoList.add(apiListDto);
            }
        }
        return apiListDtoList;
    }

    private static SwaggerApiListDto resolvingPathItem(Operation operation, String url, HttpMethod httpMethod) {
        if (CommonUtil.isEmpty(operation) || CommonUtil.isEmpty(url) || CommonUtil.isEmpty(httpMethod)) {
            return null;
        }
        SwaggerApiListDto swaggerApiListDto = new SwaggerApiListDto();
        swaggerApiListDto.setMethod(httpMethod);
        swaggerApiListDto.setUrl(url);
        swaggerApiListDto.setDescription(operation.getSummary());
        swaggerApiListDto.setTags(operation.getTags());
        List<Parameter> parameters = operation.getParameters();
        if (CommonUtil.isNotEmpty(parameters)) {
            parameters.stream().forEach(x -> {
                Map<String, String> map = x.getSchema();
                if (CommonUtil.isNotEmpty(map)) {
                    x.setRef(map.get("ref"));
                }
            });
        }
        swaggerApiListDto.setParameters(parameters);
        String keyStr = swaggerApiListDto.getMethod().name() + SEP + swaggerApiListDto.getUrl();
        swaggerApiListDto.setKey(CommonUtil.md5s(keyStr));
        LinkedHashMap<String, ApiResponse> map = operation.getResponses();
        if (CommonUtil.isNotEmpty(map)) {
            for (Map.Entry<String, ApiResponse> entry : map.entrySet()) {
                String code = entry.getKey();
                if (CommonUtil.isEmpty(code) || !Objects.equals(code, "200")) {
                    //只需要正常返回的结果
                    continue;
                }
                ApiResponse apiResponse = entry.getValue();
//                ApiResponse apiResponse = JSON.toJavaObject(entry.getValue(), ApiResponse.class);;
                if (CommonUtil.isNotEmpty(apiResponse)) {
                    String description = apiResponse.getDescription();
                    String ref = "";
                    Map<String, String> schema = apiResponse.getSchema();
                    if (CommonUtil.isNotEmpty(schema)) {
                        ref = schema.get("ref");
                    }
                    ResultData resultData = new ResultData();
                    resultData.setRef(ref);
                    resultData.setDescription(description);
                    swaggerApiListDto.setResultData(resultData);
                }
            }
        }
        return swaggerApiListDto;
    }


    /**
     * 格式
     *
     * @param fieldName
     * @return
     * @throws Exception
     */
    private static String getMethodName(String fieldName) throws Exception {
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }


    /**
     * TODO
     * jackson转换出具体的java对象
     * 缺少：
     * 1/yml结构解析：之后swagger是yml结构？
     * 2/
     *
     * @param data
     */
    public static OpenApi transform2Obj(String data) throws RuntimeException {
        if (CommonUtil.isEmpty(data)) {
            throw new RuntimeException("data empty.");
        }
        data = data.replaceAll("\\$ref", "ref");
        data = data.replaceAll("#/definitions/", "");
        JSONObject userJson = JSONObject.parseObject(data);
        OpenApi openApi = JSON.toJavaObject(userJson, OpenApi.class);
        return openApi;
    }

    public static class Parameters {
        private final String data;

        public Parameters(String data) {
            this.data = Validate.notNull(data, "data must not be null");
        }
    }

    @Data
    public static class Result {
        private final String data;
        private List<SwaggerApiListDto> swaggerApiListDtoList;
        private Map<String, List<ResultData>> definitionMap;
        private SwaggerApiListDto swaggerApiListDto;

        public Result(String data) {
            this.data = Validate.notNull(data, "data must not be null");
        }

        public Result formatSwaggerApiList(List<SwaggerApiListDto> swaggerApiListDtoList) {
            this.swaggerApiListDtoList = Validate.notNull(swaggerApiListDtoList, "swaggerApiListDtoList must not be null");
            return this;
        }

        public Result formatDefinitionMap(Map<String, List<ResultData>> definitionMap) {
            this.definitionMap = Validate.notNull(definitionMap, "definitionMap must not be null");
            return this;
        }

        public Result formCenterApi(SwaggerApiListDto swaggerApiListDto) {
            this.swaggerApiListDto = Validate.notNull(swaggerApiListDto, "swaggerApiListDto must not be null");
            return this;
        }
    }
}
