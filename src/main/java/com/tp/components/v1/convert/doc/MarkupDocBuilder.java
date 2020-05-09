/*
 *
 *  Copyright 2015 Robert Winkler
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package com.tp.components.v1.convert.doc;

import com.tp.components.v1.convert.doc.interal.markdown.MarkupBlockStyle;

import java.nio.charset.Charset;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Robert Winkler
 */
public interface MarkupDocBuilder {

    /**
     * Configure this builder to prefix all generated anchors with {@code prefix}.
     *
     * @param prefix anchor prefix
     * @return this builder
     */
    MarkupDocBuilder withAnchorPrefix(String prefix);

    /**
     * 添加文本描述
     *
     * @param description
     * @return
     */
    MarkupDocBuilder documentDescription(String description);

    /**
     * 添加请求的url
     *
     * @param url
     * @return
     */
    MarkupDocBuilder documentUrl(String url);

    /**
     * 请求方法
     *
     * @param method
     * @return
     */
    MarkupDocBuilder documentMethod(String method);

    /**
     * 请求参数表格
     *
     * @param requestParam
     * @return
     */
    MarkupDocBuilder documentRequestParam(String requestParam);

    /**
     * 请求参数例子
     *
     * @param
     * @return
     */
    MarkupDocBuilder documentRequestBody(String requestBody);


    /**
     * 返回值表格
     *
     * @param requestParam
     * @return
     */
    MarkupDocBuilder documentResponseParam(String requestParam);


    /**
     * 返回值例子
     *
     * @param responseBody
     * @return
     */
    MarkupDocBuilder documentResponseBody(String responseBody);

    /**
     * 生成表格
     *
     * @param columns
     * @param cells
     * @return
     */
    MarkupDocBuilder table(List<String> columns, List<List<String>> cells);

    /**
     * Builds a block of {@code text} with specified {@code style}.
     *
     * @param text               text
     * @param style              block style
     * @return this builder
     */
    MarkupDocBuilder block(String text, MarkupBlockStyle style);

    /**
     * Writes the content of the builder to a file.<br>
     * An extension will be dynamically added to fileName depending on the markup language.
     *
     * @param file    the generated file without extension
     * @param charset the the charset to use for encoding
     * @param options the file open options
     */
    void writeToFile(Path file, Charset charset, OpenOption... options);

    /**
     * 直接将文本计算出来
     * @return
     */
    String writeToConsole();

    /**
     * Add an extension to fileName depending on markup language.
     *
     * @param fileName without extension
     * @return fileName with an extension
     */
    String addFileExtension(String fileName);

    /**
     * Writes the content of the builder to a file.
     *
     * @param file    the generated file
     * @param charset the the charset to use for encoding
     * @param options the file open options
     */
    void writeToFileWithoutExtension(Path file, Charset charset, OpenOption... options);

    /**
     * 增加一行
     * @param
     * @return
     */
    MarkupDocBuilder documentNewLine();
}
