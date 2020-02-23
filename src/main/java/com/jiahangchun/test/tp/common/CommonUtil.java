package com.jiahangchun.test.tp.common;

import com.alibaba.fastjson.JSON;
import net.sf.cglib.beans.BeanGenerator;
import okhttp3.*;
import org.springframework.beans.BeanUtils;
//import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.HttpMethod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jiahangchun
 */
public class CommonUtil {

    private static final String ACCEPT_HEADER_VALUE = "application/json, application/yaml, */*";
    private static final String USER_AGENT_HEADER_VALUE = "Apache-HttpClient/Swagger";
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    public static Map<String, String> HTTP_REQUEST_HEADERS = new HashMap<>();
    private static OkHttpClient okHttpClient = null;

    static {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS).build();
        HTTP_REQUEST_HEADERS.put("Accept", ACCEPT_HEADER_VALUE);
        HTTP_REQUEST_HEADERS.put("User-Agent", USER_AGENT_HEADER_VALUE);
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");
    }

    private CommonUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return {@code true}: 为空<br>{@code false}: 不为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String && obj.toString().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断对象是否非空
     *
     * @param obj 对象
     * @return {@code true}: 非空<br>{@code false}: 空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /*********************加载**********************************************/

    /**
     * 得到当前ClassLoader，先找线程池的，找不到就找中间件所在的ClassLoader
     *
     * @return ClassLoader
     */
    public static ClassLoader getCurrentClassLoader() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = CommonUtil.class.getClassLoader();
        }
        return cl == null ? ClassLoader.getSystemClassLoader() : cl;
    }

    /**
     * 根据类名加载Class
     *
     * @param className  类名
     * @param initialize 是否初始化
     * @return Class
     */
    public static Class forName(String className, boolean initialize) {
        try {
            return Class.forName(className, initialize, getCurrentClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据类名加载Class
     *
     * @param className 类名
     * @return Class
     */
    public static Class forName(String className) {
        return forName(className, true);
    }


    public static HttpMethod getMethodByName(String name) {
        if (CommonUtil.isEmpty(name)) {
            return null;
        }
        return Arrays.asList(HttpMethod.class.getEnumConstants())
                .stream()
                .filter(e -> Objects.equals(e.name().toLowerCase(), name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    /*********************************** 拷贝 *************************************************/

    public static <T> T copyProperties(Object from, Class<T> toClass) throws RuntimeException {
        if (from == null) {
            return null;
        }
        T to;
        try {
            to = toClass.newInstance();
            copyProperties(from, to);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return to;
    }

    public static void copyProperties(Object fromObj, Object toObj) throws RuntimeException {
        BeanUtils.copyProperties(fromObj, toObj);
    }


    /*********************************** md5 *************************************************/

    /**
     * 依据string 转换成32位md5做key.
     *
     * @param plainText
     * @return
     */
    public static String md5s(String plainText) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return md5;
    }

    /*********************************** 正则表达式 *************************************************/
    /**
     * 找到最先匹配到的结果
     *
     * @param line
     * @param pattern
     * @return
     */
    public static String match(String line, String pattern) {
        String result = "";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);
        if (m.find()) {
            result = m.group(0);
        }
        return result;
    }


    /*********************************** http请求 *************************************************/


    public static String urlToString(String url) throws Exception {
        return CommonUtil.urlToString(url, HTTP_REQUEST_HEADERS, null);
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
    public static String urlToString(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {

        InputStream is = null;
        BufferedReader br = null;
        try {
            URLConnection conn;
            do {
                final URL inUrl = new URL(cleanUrl(url));
                conn = inUrl.openConnection();
                if (CommonUtil.isNotEmpty(headers)) {
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        if (CommonUtil.isNotEmpty(key) && CommonUtil.isNotEmpty(value)) {
                            conn.setRequestProperty(key, value);
                        }
                    }
                }
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


    /*********************************** 反射相关的操作 *************************************************/

    /**
     * 根据属性动态生成对象，并赋值
     * <p>
     * 注意：返回生成的对象属性，均在设置属性前加入前缀$cglib_prop_，例如$cglib_prop_userId
     *
     * @param propertyMap Map<生成的对象变量名称，生成的对象变量值>
     * @return Object
     */
    public static Object generateObjectByField(Map<String, Object> propertyMap) {
        try {
            BeanGenerator generator = new BeanGenerator();
            for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
                generator.addProperty(entry.getKey(), entry.getValue().getClass());
            }
            // 构建对象
            Object obj = generator.create();
            // 赋值
            for (Map.Entry<String, Object> en : propertyMap.entrySet()) {
                org.apache.commons.beanutils.BeanUtils.setProperty(obj, en.getKey(), en.getValue());
            }
            return obj;
        }catch (Exception e){
            return null;
        }
    }

    public static String generateObjectByFieldToStr(Map<String, Object> propertyMap) {
        try {
            Object object = CommonUtil.generateObjectByField(propertyMap);
            return JSON.toJSONString(object).replaceAll("$cglib_prop_", "");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取常用的类的默认返回值
     * @param type
     * @return
     */
    public static String getDefaultValue(String type){
        switch( type) {
            case "string":
                return "string";
            case "integer":
                return "1001";
            case "number":
                return "1";
            case "boolean":
                return "true";
            case "array":
                return "[]";
            case "object":
                return "{}";
            default:
                return type;
        }
    }


}
