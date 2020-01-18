package com.jiahangchun.test.tp.swagger.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiahangchun.test.tp.common.BizException;
import com.jiahangchun.test.tp.common.CommonUtil;
import com.jiahangchun.test.tp.swagger.ApiResponseService;
import com.jiahangchun.test.tp.swagger.OpenApi;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service("apiResponseService")
public class ApiResponseServiceImpl implements ApiResponseService {

    /**
     * 默认的swagger地址
     */
    private static final String CONVENIENT_SWAGGER_URL = "http://erp.test.jimuitech.com//platform/v2/api-docs";
    private static final String ACCEPT_HEADER_VALUE = "application/json, application/yaml, */*";
    private static final String USER_AGENT_HEADER_VALUE = "Apache-HttpClient/Swagger";
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    @Override
    public OpenApi getOriginSwaggerData(String url) throws BizException {
        if(CommonUtil.isEmpty(url)){
            url=CONVENIENT_SWAGGER_URL;
        }
        //获取json化数据
        String data = ApiResponseServiceImpl.data(url);
        //格式化
        return ApiResponseServiceImpl.transform2Obj(data);
    }

    /**
     * TODO
     * jackson转换出具体的java对象
     * 缺少：
     * 1/yml结构解析：之后swagger是yml结构？
     * 2/
     * @param data
     */
    public static OpenApi transform2Obj(String data) throws RuntimeException {
        if(CommonUtil.isEmpty(data)){
            throw new RuntimeException("data empty.");
        }
        JSONObject userJson = JSONObject.parseObject(data);
        OpenApi openApi = JSON.toJavaObject(userJson,OpenApi.class);

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
