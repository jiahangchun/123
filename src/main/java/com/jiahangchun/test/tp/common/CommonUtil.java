package com.jiahangchun.test.tp.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpMethod;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author jiahangchun
 */
public class CommonUtil {

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
}
