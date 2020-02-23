package com.jiahangchun.test.tp.convert.data.format;
import io.vavr.Function1;

/**
 * 拿到原始数据后转换方法定义
 * @param <T>
 */
public abstract class DataFormat<T> implements Function1<SwaggerDataFormat.Parameters, T> {
}
