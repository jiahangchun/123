package com.jiahangchun.test.tp.swagger.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.jiahangchun.test.tp.common.BizException;
import com.jiahangchun.test.tp.common.CommonUtil;
import com.jiahangchun.test.tp.swagger.ApiResponseService;
import com.jiahangchun.test.tp.swagger.dto.*;
import com.jiahangchun.test.tp.swagger.parm.SwaggerApiListParam;
import com.jiahangchun.test.tp.swagger.vo.SwaggerApiListVo;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 处理
 */
@Slf4j
@Service("apiResponseService")
public class ApiResponseServiceImpl implements ApiResponseService {

    /**
     * 默认的swagger地址
     */
    private static final String SEP = "###";
    private static final String CONVENIENT_TEST_SWAGGER_URL = "http://erp.test.jimuitech.com//platform/v2/api-docs";
    private static final String CONVENIENT_LOCAL_SWAGGER_URL = "http://localhost:8080//platform/v2/api-docs";
    private static final String ACCEPT_HEADER_VALUE = "application/json, application/yaml, */*";
    private static final String USER_AGENT_HEADER_VALUE = "Apache-HttpClient/Swagger";
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String SAMPLE_SWAGGER_DATA = "{\"swagger\":\"2.0\",\"info\":{\"description\":\"项目开发文档\",\"version\":\"1.1.0\",\"title\":\"service-convenient-jiahangchun\",\"termsOfService\":\"192.168.2.198:8080\"},\"host\":\"localhost:8080\",\"basePath\":\"/platform\",\"tags\":[{\"name\":\"多地址改造\",\"description\":\"Ship Id Controller\"}],\"paths\":{\"/crm/shipId/bind/{receiveAddressId}/{userId}\":{\"post\":{\"tags\":[\"多地址改造\"],\"summary\":\"将某个shipId和某个user关联在一起\",\"operationId\":\"bindShipIdUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"token\",\"in\":\"header\",\"description\":\"令牌校验\",\"required\":true,\"type\":\"string\",\"default\":\"7IRRIl%2FdWT76pV2mLBipp8%2BHSO0gCdU8XSidi8vhtEdCk01o5PX67XKsfbtduWZC0WxUdTj%2Fkbz5zxYmKAYKpMDi4JmF9bqqGvg451EjVyJe1XC0RSIn5UoT8%2BpPF%2B45MYzLBvrd1tJGJUZFl625nQ%3D%3D\"},{\"name\":\"countryId\",\"in\":\"header\",\"description\":\"countryId\",\"required\":true,\"type\":\"string\",\"default\":\"1001\"},{\"name\":\"receiveAddressId\",\"in\":\"path\",\"description\":\"receiveAddressId\",\"required\":true,\"type\":\"integer\",\"format\":\"int64\"},{\"name\":\"userId\",\"in\":\"path\",\"description\":\"userId\",\"required\":true,\"type\":\"integer\",\"format\":\"int64\"}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/返回类«boolean»\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/crm/shipId/create/shipId\":{\"post\":{\"tags\":[\"多地址改造\"],\"summary\":\"创建全新的shipId\",\"operationId\":\"createShipIdUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"token\",\"in\":\"header\",\"description\":\"令牌校验\",\"required\":true,\"type\":\"string\",\"default\":\"7IRRIl%2FdWT76pV2mLBipp8%2BHSO0gCdU8XSidi8vhtEdCk01o5PX67XKsfbtduWZC0WxUdTj%2Fkbz5zxYmKAYKpMDi4JmF9bqqGvg451EjVyJe1XC0RSIn5UoT8%2BpPF%2B45MYzLBvrd1tJGJUZFl625nQ%3D%3D\"},{\"name\":\"countryId\",\"in\":\"header\",\"description\":\"countryId\",\"required\":true,\"type\":\"string\",\"default\":\"1001\"},{\"in\":\"body\",\"name\":\"createShipIdParam\",\"description\":\"createShipIdParam\",\"required\":true,\"schema\":{\"$ref\":\"#/definitions/创建shipId的参数\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/返回类«boolean»\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/crm/shipId/create/shipId/pre\":{\"post\":{\"tags\":[\"多地址改造\"],\"summary\":\"创建全新的shipId的校验\",\"operationId\":\"createShipIdPreUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"token\",\"in\":\"header\",\"description\":\"令牌校验\",\"required\":true,\"type\":\"string\",\"default\":\"7IRRIl%2FdWT76pV2mLBipp8%2BHSO0gCdU8XSidi8vhtEdCk01o5PX67XKsfbtduWZC0WxUdTj%2Fkbz5zxYmKAYKpMDi4JmF9bqqGvg451EjVyJe1XC0RSIn5UoT8%2BpPF%2B45MYzLBvrd1tJGJUZFl625nQ%3D%3D\"},{\"name\":\"countryId\",\"in\":\"header\",\"description\":\"countryId\",\"required\":true,\"type\":\"string\",\"default\":\"1001\"},{\"in\":\"body\",\"name\":\"createShipIdParam\",\"description\":\"createShipIdParam\",\"required\":true,\"schema\":{\"$ref\":\"#/definitions/创建shipId的参数\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/返回类«List«创建shipId时的预参数»»\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/crm/shipId/crm/list\":{\"post\":{\"tags\":[\"多地址改造\"],\"summary\":\"CRM 所有人的shipId列表查询\",\"operationId\":\"queryCrmShipIdListUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"token\",\"in\":\"header\",\"description\":\"令牌校验\",\"required\":true,\"type\":\"string\",\"default\":\"7IRRIl%2FdWT76pV2mLBipp8%2BHSO0gCdU8XSidi8vhtEdCk01o5PX67XKsfbtduWZC0WxUdTj%2Fkbz5zxYmKAYKpMDi4JmF9bqqGvg451EjVyJe1XC0RSIn5UoT8%2BpPF%2B45MYzLBvrd1tJGJUZFl625nQ%3D%3D\"},{\"name\":\"countryId\",\"in\":\"header\",\"description\":\"countryId\",\"required\":true,\"type\":\"string\",\"default\":\"1001\"},{\"in\":\"body\",\"name\":\"queryShipIdParam\",\"description\":\"queryShipIdParam\",\"required\":true,\"schema\":{\"$ref\":\"#/definitions/查询参数\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/返回类«Page«客户主档返回列表»»\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/crm/shipId/eggBuy/shipId/{userId}\":{\"post\":{\"tags\":[\"多地址改造\"],\"summary\":\"eggBuy可以选择的shipId列表\",\"operationId\":\"eggBuyShipIdListUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"token\",\"in\":\"header\",\"description\":\"令牌校验\",\"required\":true,\"type\":\"string\",\"default\":\"7IRRIl%2FdWT76pV2mLBipp8%2BHSO0gCdU8XSidi8vhtEdCk01o5PX67XKsfbtduWZC0WxUdTj%2Fkbz5zxYmKAYKpMDi4JmF9bqqGvg451EjVyJe1XC0RSIn5UoT8%2BpPF%2B45MYzLBvrd1tJGJUZFl625nQ%3D%3D\"},{\"name\":\"countryId\",\"in\":\"header\",\"description\":\"countryId\",\"required\":true,\"type\":\"string\",\"default\":\"1001\"},{\"name\":\"userId\",\"in\":\"path\",\"description\":\"userId\",\"required\":true,\"type\":\"integer\",\"format\":\"int64\"},{\"in\":\"body\",\"name\":\"queryShipIdParam\",\"description\":\"queryShipIdParam\",\"required\":true,\"schema\":{\"$ref\":\"#/definitions/查询参数\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/返回类«Page«客户主档返回列表»»\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/crm/shipId/get/default/shipId\":{\"post\":{\"tags\":[\"多地址改造\"],\"summary\":\"获取客户默认的shipId\",\"operationId\":\"getDefaultShipIdUsingPOST_1\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"token\",\"in\":\"header\",\"description\":\"令牌校验\",\"required\":true,\"type\":\"string\",\"default\":\"7IRRIl%2FdWT76pV2mLBipp8%2BHSO0gCdU8XSidi8vhtEdCk01o5PX67XKsfbtduWZC0WxUdTj%2Fkbz5zxYmKAYKpMDi4JmF9bqqGvg451EjVyJe1XC0RSIn5UoT8%2BpPF%2B45MYzLBvrd1tJGJUZFl625nQ%3D%3D\"},{\"name\":\"countryId\",\"in\":\"header\",\"description\":\"countryId\",\"required\":true,\"type\":\"string\",\"default\":\"1001\"},{\"in\":\"body\",\"name\":\"queryShipIdParam\",\"description\":\"queryShipIdParam\",\"required\":true,\"schema\":{\"$ref\":\"#/definitions/查询参数\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/返回类«客户主档返回列表»\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/crm/shipId/get/{shipId}\":{\"get\":{\"tags\":[\"多地址改造\"],\"summary\":\"获取shipId详情\",\"operationId\":\"getShipIdUsingGET\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"token\",\"in\":\"header\",\"description\":\"令牌校验\",\"required\":true,\"type\":\"string\",\"default\":\"7IRRIl%2FdWT76pV2mLBipp8%2BHSO0gCdU8XSidi8vhtEdCk01o5PX67XKsfbtduWZC0WxUdTj%2Fkbz5zxYmKAYKpMDi4JmF9bqqGvg451EjVyJe1XC0RSIn5UoT8%2BpPF%2B45MYzLBvrd1tJGJUZFl625nQ%3D%3D\"},{\"name\":\"countryId\",\"in\":\"header\",\"description\":\"countryId\",\"required\":true,\"type\":\"string\",\"default\":\"1001\"},{\"name\":\"shipId\",\"in\":\"path\",\"description\":\"shipId\",\"required\":true,\"type\":\"string\"}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/返回类«shipId详情»\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/crm/shipId/list\":{\"post\":{\"tags\":[\"多地址改造\"],\"summary\":\"CRM主档列表查询\",\"operationId\":\"queryUserShipIdListUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"token\",\"in\":\"header\",\"description\":\"令牌校验\",\"required\":true,\"type\":\"string\",\"default\":\"7IRRIl%2FdWT76pV2mLBipp8%2BHSO0gCdU8XSidi8vhtEdCk01o5PX67XKsfbtduWZC0WxUdTj%2Fkbz5zxYmKAYKpMDi4JmF9bqqGvg451EjVyJe1XC0RSIn5UoT8%2BpPF%2B45MYzLBvrd1tJGJUZFl625nQ%3D%3D\"},{\"name\":\"countryId\",\"in\":\"header\",\"description\":\"countryId\",\"required\":true,\"type\":\"string\",\"default\":\"1001\"},{\"in\":\"body\",\"name\":\"queryShipIdParam\",\"description\":\"queryShipIdParam\",\"required\":true,\"schema\":{\"$ref\":\"#/definitions/查询参数\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/返回类«Page«客户主档返回列表»»\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/crm/shipId/list/other\":{\"post\":{\"tags\":[\"多地址改造\"],\"summary\":\"寻找其他人的但是没有被关联在当前用户身上的shipId\",\"operationId\":\"queryCrmSelectShipIdUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"token\",\"in\":\"header\",\"description\":\"令牌校验\",\"required\":true,\"type\":\"string\",\"default\":\"7IRRIl%2FdWT76pV2mLBipp8%2BHSO0gCdU8XSidi8vhtEdCk01o5PX67XKsfbtduWZC0WxUdTj%2Fkbz5zxYmKAYKpMDi4JmF9bqqGvg451EjVyJe1XC0RSIn5UoT8%2BpPF%2B45MYzLBvrd1tJGJUZFl625nQ%3D%3D\"},{\"name\":\"countryId\",\"in\":\"header\",\"description\":\"countryId\",\"required\":true,\"type\":\"string\",\"default\":\"1001\"},{\"in\":\"body\",\"name\":\"queryShipIdParam\",\"description\":\"queryShipIdParam\",\"required\":true,\"schema\":{\"$ref\":\"#/definitions/查询参数\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/返回类«Page«客户主档返回列表»»\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/crm/shipId/quotation/shipId/{userId}\":{\"post\":{\"tags\":[\"多地址改造\"],\"summary\":\"quotation可以选择的shipId列表\",\"operationId\":\"quotationShipIdListUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"token\",\"in\":\"header\",\"description\":\"令牌校验\",\"required\":true,\"type\":\"string\",\"default\":\"7IRRIl%2FdWT76pV2mLBipp8%2BHSO0gCdU8XSidi8vhtEdCk01o5PX67XKsfbtduWZC0WxUdTj%2Fkbz5zxYmKAYKpMDi4JmF9bqqGvg451EjVyJe1XC0RSIn5UoT8%2BpPF%2B45MYzLBvrd1tJGJUZFl625nQ%3D%3D\"},{\"name\":\"countryId\",\"in\":\"header\",\"description\":\"countryId\",\"required\":true,\"type\":\"string\",\"default\":\"1001\"},{\"name\":\"userId\",\"in\":\"path\",\"description\":\"userId\",\"required\":true,\"type\":\"integer\",\"format\":\"int64\"},{\"in\":\"body\",\"name\":\"queryShipIdParam\",\"description\":\"queryShipIdParam\",\"required\":true,\"schema\":{\"$ref\":\"#/definitions/查询参数\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/返回类«Page«客户主档返回列表»»\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/crm/shipId/remove/{receiveAddressId}/{userId}\":{\"post\":{\"tags\":[\"多地址改造\"],\"summary\":\"移除shipId和当前客户的关系\",\"operationId\":\"removeCrmShipIdUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"token\",\"in\":\"header\",\"description\":\"令牌校验\",\"required\":true,\"type\":\"string\",\"default\":\"7IRRIl%2FdWT76pV2mLBipp8%2BHSO0gCdU8XSidi8vhtEdCk01o5PX67XKsfbtduWZC0WxUdTj%2Fkbz5zxYmKAYKpMDi4JmF9bqqGvg451EjVyJe1XC0RSIn5UoT8%2BpPF%2B45MYzLBvrd1tJGJUZFl625nQ%3D%3D\"},{\"name\":\"countryId\",\"in\":\"header\",\"description\":\"countryId\",\"required\":true,\"type\":\"string\",\"default\":\"1001\"},{\"name\":\"receiveAddressId\",\"in\":\"path\",\"description\":\"receiveAddressId\",\"required\":true,\"type\":\"integer\",\"format\":\"int64\"},{\"name\":\"userId\",\"in\":\"path\",\"description\":\"userId\",\"required\":true,\"type\":\"integer\",\"format\":\"int64\"}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/返回类«boolean»\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/crm/shipId/update/shipId\":{\"post\":{\"tags\":[\"多地址改造\"],\"summary\":\"更新shipId\",\"operationId\":\"updateShipIdUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"token\",\"in\":\"header\",\"description\":\"令牌校验\",\"required\":true,\"type\":\"string\",\"default\":\"7IRRIl%2FdWT76pV2mLBipp8%2BHSO0gCdU8XSidi8vhtEdCk01o5PX67XKsfbtduWZC0WxUdTj%2Fkbz5zxYmKAYKpMDi4JmF9bqqGvg451EjVyJe1XC0RSIn5UoT8%2BpPF%2B45MYzLBvrd1tJGJUZFl625nQ%3D%3D\"},{\"name\":\"countryId\",\"in\":\"header\",\"description\":\"countryId\",\"required\":true,\"type\":\"string\",\"default\":\"1001\"},{\"in\":\"body\",\"name\":\"updateShipIdParam\",\"description\":\"updateShipIdParam\",\"required\":true,\"schema\":{\"$ref\":\"#/definitions/更新shipId的参数\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/返回类«boolean»\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}}},\"definitions\":{\"Page«客户主档返回列表»\":{\"type\":\"object\",\"properties\":{\"items\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/客户主档返回列表\"}},\"limit\":{\"type\":\"integer\",\"format\":\"int32\"},\"offset\":{\"type\":\"integer\",\"format\":\"int32\"},\"page\":{\"type\":\"integer\",\"format\":\"int32\"},\"sortField\":{\"type\":\"string\"},\"sortOrder\":{\"type\":\"string\"},\"startPage\":{\"type\":\"integer\",\"format\":\"int32\"},\"total\":{\"type\":\"integer\",\"format\":\"int32\"}}},\"shipId详情\":{\"type\":\"object\",\"properties\":{\"city\":{\"type\":\"string\",\"example\":\"杭州市\",\"description\":\"市\"},\"cityId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":4,\"description\":\"市Id\"},\"companyName\":{\"type\":\"string\",\"example\":\"名字叫Xxx\",\"description\":\"客户名称\"},\"contact\":{\"type\":\"string\",\"example\":\"人物的描述\",\"description\":\"联系方式\"},\"detailAddress\":{\"type\":\"string\",\"example\":\"西湖区天和华丰\",\"description\":\"详细地址\"},\"district\":{\"type\":\"string\",\"example\":\"余杭区\",\"description\":\"区\"},\"districtId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":5,\"description\":\"区Id\"},\"id\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":\"主键\",\"description\":\"主键\"},\"lat\":{\"type\":\"number\",\"format\":\"double\",\"example\":21.29612,\"description\":\"纬度\"},\"lng\":{\"type\":\"number\",\"format\":\"double\",\"example\":113.157337,\"description\":\"经度\"},\"mobile\":{\"type\":\"string\",\"example\":\"15700082377\",\"description\":\"手机号码\"},\"postCode\":{\"type\":\"string\",\"example\":\"311122\",\"description\":\"邮编\"},\"province\":{\"type\":\"string\",\"example\":\"浙江省\",\"description\":\"省的名称\"},\"provinceId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":22,\"description\":\"省Id\"},\"shipId\":{\"type\":\"string\",\"example\":\"SH1999_1\",\"description\":\"shipId\"},\"shipIdPhotoParams\":{\"type\":\"array\",\"description\":\"图片\",\"items\":{\"$ref\":\"#/definitions/图片\"}}}},\"创建shipId时的预参数\":{\"type\":\"object\",\"properties\":{\"companyName\":{\"type\":\"string\",\"example\":\"名字叫Xxx\",\"description\":\"客户名称\"},\"shipId\":{\"type\":\"string\",\"example\":\"SH10025_2\",\"description\":\"shipId\"}}},\"创建shipId的参数\":{\"type\":\"object\",\"properties\":{\"cityId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":4,\"description\":\"市Id\"},\"companyName\":{\"type\":\"string\",\"example\":\"名字叫Xxx\",\"description\":\"公司名称\"},\"contact\":{\"type\":\"string\",\"example\":\"人物的描述\",\"description\":\"人员的描述\"},\"countryId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":5,\"description\":\"国家Id\"},\"detailAddress\":{\"type\":\"string\",\"example\":\"西湖区天和华丰\",\"description\":\"详细地址\"},\"districtId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":5,\"description\":\"区Id\"},\"gps\":{\"type\":\"string\",\"example\":\"Lnt:113.157337 Lat:2222\",\"description\":\"经纬度，不用页面传递\"},\"lat\":{\"type\":\"number\",\"format\":\"double\",\"example\":21.29612,\"description\":\"纬度\"},\"lng\":{\"type\":\"number\",\"format\":\"double\",\"example\":113.157337,\"description\":\"经度\"},\"mobile\":{\"type\":\"string\",\"example\":\"15700082377\",\"description\":\"手机号码\"},\"postCode\":{\"type\":\"string\",\"example\":\"311122\",\"description\":\"邮编\"},\"prefix\":{\"type\":\"string\",\"example\":\"66\",\"description\":\"手机前缀\"},\"provinceId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":22,\"description\":\"省Id\"},\"shipIdPhotoParams\":{\"type\":\"array\",\"description\":\"图片\",\"items\":{\"$ref\":\"#/definitions/图片\"}},\"userId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":17965,\"description\":\"userId\"}}},\"图片\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"example\":\"Xxx的门店图片\",\"description\":\"图片名称\"},\"photoId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":1022,\"description\":\"照片的id\"},\"url\":{\"type\":\"string\",\"example\":\"/aa/bb\",\"description\":\"图片URl\"}}},\"客户主档返回列表\":{\"type\":\"object\",\"properties\":{\"canRemove\":{\"type\":\"boolean\",\"example\":false,\"description\":\"是否能显示remove按钮\"},\"city\":{\"type\":\"string\",\"example\":\"杭州市\",\"description\":\"市\"},\"cityId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":\"杭州市Id\",\"description\":\"市Id\"},\"companyName\":{\"type\":\"string\",\"example\":\"TCC td\",\"description\":\"公司名称\"},\"contact\":{\"type\":\"string\",\"example\":\"TCC 的公司\",\"description\":\"客户的相关描述\"},\"detailAddress\":{\"type\":\"string\",\"example\":\"天和华丰苑\",\"description\":\"详细地址\"},\"district\":{\"type\":\"string\",\"example\":\"余杭区\",\"description\":\"区\"},\"districtAddress\":{\"type\":\"string\",\"example\":\"浙江省-杭州市-余杭区\",\"description\":\"三级结构\"},\"districtId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":\"余杭区Id\",\"description\":\"区Id\"},\"mobile\":{\"type\":\"string\",\"example\":\"1570082377\",\"description\":\"客户手机号\"},\"postCode\":{\"type\":\"string\",\"example\":\"311122\",\"description\":\"邮编\"},\"province\":{\"type\":\"string\",\"example\":\"浙江省\",\"description\":\"省\"},\"provinceId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":\"浙江省Id\",\"description\":\"省Id\"},\"receiveAddressId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":21,\"description\":\"receiveAddressId\"},\"receiveFullAddress\":{\"type\":\"string\",\"example\":\"浙江省-杭州市-余杭区 天和华丰24幢\",\"description\":\"省-市-区\"},\"shipId\":{\"type\":\"string\",\"example\":\"123\",\"description\":\"shipId\"}}},\"更新shipId的参数\":{\"type\":\"object\",\"properties\":{\"cityId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":4,\"description\":\"市Id\"},\"companyName\":{\"type\":\"string\",\"example\":\"名字叫Xxx\",\"description\":\"客户名称\"},\"contact\":{\"type\":\"string\",\"example\":\"人物的描述\",\"description\":\"联系方式\"},\"countryId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":1001,\"description\":\"countryId\"},\"detailAddress\":{\"type\":\"string\",\"example\":\"西湖区天和华丰\",\"description\":\"详细地址\"},\"districtId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":5,\"description\":\"区Id\"},\"id\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":\"主键\",\"description\":\"主键\"},\"langCode\":{\"type\":\"string\",\"example\":\"1001 语言，页面不需要传递的\",\"description\":\"langCode\"},\"lat\":{\"type\":\"number\",\"format\":\"double\",\"example\":21.29612,\"description\":\"纬度\"},\"lng\":{\"type\":\"number\",\"format\":\"double\",\"example\":113.157337,\"description\":\"经度\"},\"mobile\":{\"type\":\"string\",\"example\":\"15700082377\",\"description\":\"手机号码\"},\"postCode\":{\"type\":\"string\",\"example\":\"311122\",\"description\":\"邮编\"},\"prefix\":{\"type\":\"string\",\"example\":\"66\",\"description\":\"手机号码前缀\"},\"provinceId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":22,\"description\":\"省Id\"},\"shipIdPhotoParams\":{\"type\":\"array\",\"description\":\"图片\",\"items\":{\"$ref\":\"#/definitions/图片\"}}}},\"查询参数\":{\"type\":\"object\",\"properties\":{\"cityId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":4,\"description\":\"市Id\"},\"companyName\":{\"type\":\"string\",\"example\":\"CustonmerAA\",\"description\":\"客户名称的模糊查询\"},\"contact\":{\"type\":\"string\",\"example\":\"西湖区\",\"description\":\"contact的模糊查询\"},\"countryId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":1001,\"description\":\"countryId\"},\"districtId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":5,\"description\":\"区Id\"},\"limit\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"一页大小限制\"},\"mobile\":{\"type\":\"string\",\"example\":\"66157000\",\"description\":\"mobile的模糊查询\"},\"offset\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"起点\"},\"page\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"页数\"},\"provinceId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":22,\"description\":\"省Id\"},\"shipId\":{\"type\":\"string\",\"example\":\"111\",\"description\":\"shipId 模糊查询\"},\"sortField\":{\"type\":\"string\"},\"sortOrder\":{\"type\":\"string\"},\"total\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"总数\"},\"totalAmount\":{\"type\":\"number\"},\"userId\":{\"type\":\"integer\",\"format\":\"int64\",\"example\":17965,\"description\":\"需要被查询的客户\"}}},\"返回类«List«创建shipId时的预参数»»\":{\"type\":\"object\",\"properties\":{\"cause\":{\"type\":\"array\",\"description\":\"异常原因\",\"items\":{\"type\":\"string\"}},\"code\":{\"type\":\"string\",\"description\":\"code\"},\"data\":{\"type\":\"array\",\"description\":\"返回结果值\",\"items\":{\"$ref\":\"#/definitions/创建shipId时的预参数\"}},\"location\":{\"type\":\"string\"},\"message\":{\"type\":\"string\",\"description\":\"描述\"},\"success\":{\"type\":\"boolean\"}}},\"返回类«Page«客户主档返回列表»»\":{\"type\":\"object\",\"properties\":{\"cause\":{\"type\":\"array\",\"description\":\"异常原因\",\"items\":{\"type\":\"string\"}},\"code\":{\"type\":\"string\",\"description\":\"code\"},\"data\":{\"description\":\"返回结果值\",\"$ref\":\"#/definitions/Page«客户主档返回列表»\"},\"location\":{\"type\":\"string\"},\"message\":{\"type\":\"string\",\"description\":\"描述\"},\"success\":{\"type\":\"boolean\"}}},\"返回类«boolean»\":{\"type\":\"object\",\"properties\":{\"cause\":{\"type\":\"array\",\"description\":\"异常原因\",\"items\":{\"type\":\"string\"}},\"code\":{\"type\":\"string\",\"description\":\"code\"},\"data\":{\"type\":\"boolean\",\"example\":false,\"description\":\"返回结果值\"},\"location\":{\"type\":\"string\"},\"message\":{\"type\":\"string\",\"description\":\"描述\"},\"success\":{\"type\":\"boolean\"}}},\"返回类«shipId详情»\":{\"type\":\"object\",\"properties\":{\"cause\":{\"type\":\"array\",\"description\":\"异常原因\",\"items\":{\"type\":\"string\"}},\"code\":{\"type\":\"string\",\"description\":\"code\"},\"data\":{\"description\":\"返回结果值\",\"$ref\":\"#/definitions/shipId详情\"},\"location\":{\"type\":\"string\"},\"message\":{\"type\":\"string\",\"description\":\"描述\"},\"success\":{\"type\":\"boolean\"}}},\"返回类«客户主档返回列表»\":{\"type\":\"object\",\"properties\":{\"cause\":{\"type\":\"array\",\"description\":\"异常原因\",\"items\":{\"type\":\"string\"}},\"code\":{\"type\":\"string\",\"description\":\"code\"},\"data\":{\"description\":\"返回结果值\",\"$ref\":\"#/definitions/客户主档返回列表\"},\"location\":{\"type\":\"string\"},\"message\":{\"type\":\"string\",\"description\":\"描述\"},\"success\":{\"type\":\"boolean\"}}}}}";

    @Override
    public OpenApi getOriginSwaggerData(String url) throws BizException {
        //获取json化数据
        String data = "{}";
        if (CommonUtil.isEmpty(url)) {
            data = SAMPLE_SWAGGER_DATA;
        } else {
            data = ApiResponseServiceImpl.data(url);
        }
        //格式化
        return ApiResponseServiceImpl.transform2Obj(data);
    }

    @Override
    public List<SwaggerApiListVo> getSwaggerData(SwaggerApiListParam swaggerApiListParam) throws BizException {
        //获取所有列表
        List<SwaggerApiListDto> swaggerApiListDtoList = this.queryAllList();

        //筛选
        List<SwaggerApiListVo> swaggerApiListVos = swaggerApiListDtoList.stream()
                .map(ApiResponseServiceImpl::convert2Vo)
                .filter(x -> {
                    return filter(x, swaggerApiListParam);
                })
                .collect(Collectors.toList());

        return swaggerApiListVos;
    }

    /**
     * 查询现在系统里面存在的所有记录
     */
    private List<SwaggerApiListDto> queryAllList() {
        OpenApi openApi = this.getOriginSwaggerData(null);


        //转换成列表
        if (CommonUtil.isEmpty(openApi)) {
            return new ArrayList<>();
        }
        //解析参数
        LinkedHashMap<String, ReturnResult> resultLinkedHashMap = openApi.getDefinitions();


        //详情请求
        LinkedHashMap<String, PathItem> map = openApi.getPaths();
        if (CommonUtil.isEmpty(map)) {
            return new ArrayList<>();
        }
        List<SwaggerApiListDto> swaggerApiListDtoList = new ArrayList<>(map.size());
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
        if (CommonUtil.isEmpty(swaggerApiListDtoList)) {
            return new ArrayList<>();
        }
        return swaggerApiListDtoList;
    }

    public Boolean filter(SwaggerApiListVo listVo, SwaggerApiListParam swaggerApiListParam) {
        if (CommonUtil.isEmpty(listVo) || CommonUtil.isEmpty(swaggerApiListParam)) {
            return true;
        }
        String tag = swaggerApiListParam.getTag();
        String key = swaggerApiListParam.getKey();
        String url = swaggerApiListParam.getUrl();
        String method = swaggerApiListParam.getMethod();
        String description = swaggerApiListParam.getDescription();
        //没有任何筛选条件
        if (CommonUtil.isEmpty(tag) &&
                CommonUtil.isEmpty(key)
                && CommonUtil.isEmpty(url)
                && CommonUtil.isEmpty(method)
                && CommonUtil.isEmpty(description)) {
            return true;
        }
        if (CommonUtil.isNotEmpty(tag) && !listVo.getTagStr().contains(tag)) {
            return false;
        }
        if (CommonUtil.isNotEmpty(key) && !Objects.equals(listVo.getKey(), key)) {
            return false;
        }
        if (CommonUtil.isNotEmpty(url) && !Objects.equals(listVo.getUrl(), url)) {
            return false;
        }
        if (CommonUtil.isNotEmpty(method) && !Objects.equals(listVo.getMethod(), method)) {
            return false;
        }
        if (CommonUtil.isNotEmpty(description) && !listVo.getDescription().contains(description)) {
            return false;
        }
        return true;
    }

    @Override
    public SwaggerApiListVo getSwaggerVo(String key) {
        List<SwaggerApiListDto> list = this.queryAllList();

        return getSwaggerData(null).stream().filter(x -> {
            return Objects.equals(x.getKey(), key);
        }).findFirst().orElse(null);
    }

    public static SwaggerApiListVo convert2Vo(SwaggerApiListDto dto) {
        SwaggerApiListVo swaggerApiListVo = CommonUtil.copyProperties(dto, SwaggerApiListVo.class);
        swaggerApiListVo.setMethod(dto.getMethod().name());
        List<String> tags = swaggerApiListVo.getTags();
        if (CommonUtil.isNotEmpty(tags)) {
            swaggerApiListVo.setTagStr(String.join("-", tags));
        }
        return swaggerApiListVo;
    }

    public List<SwaggerApiListDto> formApiInfo(PathItem pathItem, String url) throws Exception {
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
                SwaggerApiListDto apiListDto = this.resolvingPathItem(operation, url, method);
                if (CommonUtil.isEmpty(apiListDto)) {
                    continue;
                }
                System.out.println(JSON.toJSONString(apiListDto));
                apiListDtoList.add(apiListDto);
            }
        }
        return apiListDtoList;
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


    public SwaggerApiListDto resolvingPathItem(Operation operation, String url, HttpMethod httpMethod) {
        if (CommonUtil.isEmpty(operation) || CommonUtil.isEmpty(url) || CommonUtil.isEmpty(httpMethod)) {
            return null;
        }
        SwaggerApiListDto swaggerApiListDto = new SwaggerApiListDto();
        swaggerApiListDto.setMethod(httpMethod);
        swaggerApiListDto.setUrl(url);
        swaggerApiListDto.setDescription(operation.getSummary());
        swaggerApiListDto.setTags(operation.getTags());
        swaggerApiListDto.setParameters(operation.getParameters());
        swaggerApiListDto.setKey(swaggerApiListDto.getMethod().name() + SEP + swaggerApiListDto.getUrl());
        return swaggerApiListDto;
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
        data=data.replaceAll("\\$ref","ref");
        data=data.replaceAll("#/definitions","");
        JSONObject userJson = JSONObject.parseObject(data);
        OpenApi openApi = JSON.toJavaObject(userJson, OpenApi.class);
        return openApi;
    }

    /**
     * TODO
     * 缺少 url校验
     * 通过 url 获取数据
     * 1.文件
     * 2.url
     *
     * @return
     */
    public static String data(String url) {
        String data = "";
        try {
            data = ApiResponseServiceImpl.urlToString(url);
        } catch (Exception e) {
        }
        return data;
    }

    /**
     * TODO
     * 还需要携带认证信息
     * ConnectionConfigurator SSL 认证
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String urlToString(String url) throws Exception {
        InputStream is = null;
        BufferedReader br = null;

        try {
            URLConnection conn;
            do {
                final URL inUrl = new URL(cleanUrl(url));
                conn = inUrl.openConnection();
                conn.setRequestProperty("Accept", ACCEPT_HEADER_VALUE);
                conn.setRequestProperty("User-Agent", USER_AGENT_HEADER_VALUE);
                conn.connect();
                url = ((HttpURLConnection) conn).getHeaderField("Location");
            } while (301 == ((HttpURLConnection) conn).getResponseCode());
            InputStream in = conn.getInputStream();

            StringBuilder contents = new StringBuilder();

            BufferedReader input = new BufferedReader(new InputStreamReader(in, UTF_8));

            for (int i = 0; i != -1; i = input.read()) {
                char c = (char) i;
                if (!Character.isISOControl(c)) {
                    contents.append((char) i);
                }
                if (c == '\n') {
                    contents.append('\n');
                }
            }

            in.close();

            return contents.toString();
        } catch (javax.net.ssl.SSLProtocolException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static String cleanUrl(String url) {
        String result = null;
        try {
            result = url.replaceAll("\\{", "%7B").
                    replaceAll("\\}", "%7D").
                    replaceAll(" ", "%20");
        } catch (Exception t) {
            t.printStackTrace();
        }
        return result;
    }

}
