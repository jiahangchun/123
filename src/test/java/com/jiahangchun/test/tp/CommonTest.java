package com.jiahangchun.test.tp;

import com.jiahangchun.test.tp.common.OkHttpRequestUtils;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CommonTest {

    public static String url = "http://localhost:8080/platform/crm/shipId/list",testUrl="/platform/crm/shipId/bind/{receiveAddressId}/{userId}";
    public static Map<String, Object> headers = new HashMap<>();
    public static String json = "{}";

    static {
        headers.put("token", "TwX6fxAF6%2Ftn%2BhQ029kkctjsVbG0HzGAmeeU0JkeIhkAFK2Te5Vb7vfTpCjSnsvJ7JXm3mTUB59x93Hk7SL3VJlTl%2BxeO5nf9mQEEjIzLRt1huotpmW88XeZhOkmyI1HY5c0T3sulP%2FJ2xTFng%2FW%2Bw%3D%3D");
        headers.put("countryId", "1001");
    }


    public static void main(String[] args) throws Exception {
//        testPost();
        String result=testUrl.replaceAll("\\{"+"receiveAddressId"+"}","1");
        System.out.println(result);
    }

    private static void testPost() throws IOException {
        String result = OkHttpRequestUtils.doPost(url, headers, json);
        System.out.println(result);
    }


}
