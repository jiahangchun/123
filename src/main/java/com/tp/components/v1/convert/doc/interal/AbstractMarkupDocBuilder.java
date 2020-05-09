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
package com.tp.components.v1.convert.doc.interal;

import com.tp.components.v1.common.JMCommonUtil;
import com.tp.components.v1.convert.doc.MarkupDocBuilder;
import com.tp.components.v1.convert.doc.interal.markdown.MarkupBlockStyle;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.regex.Matcher;

/**
 * @author Robert Winkler
 */
public abstract class AbstractMarkupDocBuilder implements MarkupDocBuilder {

    protected String anchorPrefix = null;
    /**
     * 转换的string
     */
    protected StringBuilder documentBuilder = new StringBuilder();
    protected String newLine;
    protected int asciidocPegdownTimeoutMillis;
    protected static final int ASCIIDOC_PEGDOWN_DEFUALT_MILLIS = 2000;
    protected static final String WHITESPACE = " ";
    protected static final String NEW_LINES = "\\r\\n|\\r|\\n";
    protected static final boolean LINE_BREAK_DEFAULT = false;

    /**
     * 构造器
     */
    public AbstractMarkupDocBuilder() {
        this(System.getProperty("line.separator"), ASCIIDOC_PEGDOWN_DEFUALT_MILLIS);
    }

    public AbstractMarkupDocBuilder(String newLine) {
        this(newLine, ASCIIDOC_PEGDOWN_DEFUALT_MILLIS);
    }

    public AbstractMarkupDocBuilder(String newLine, int asciidocPegdownTimeoutMillis) {
        this.newLine = newLine;
        this.asciidocPegdownTimeoutMillis = asciidocPegdownTimeoutMillis;
    }

    @Override
    public MarkupDocBuilder withAnchorPrefix(String prefix) {
        this.anchorPrefix = prefix;
        return this;
    }

    protected void documentDescription(Markup markup, String description) {
        documentBuilder.append(markup).append("简要描述").append(newLine).append(replaceNewLinesWithWhiteSpace(description)).append(newLine).append(newLine);
    }

    protected void documentUrl(Markup markup, String url) {
        documentBuilder.append(markup).append("请求URL").append(newLine).append(replaceNewLinesWithWhiteSpace(url)).append(newLine).append(newLine);
    }

    protected void documentMethod(Markup markup, String method) {
        documentBuilder.append(markup).append("请求方式").append(newLine).append(replaceNewLinesWithWhiteSpace(method)).append(newLine).append(newLine);
    }

    protected void documentRequestParam(Markup markup, String requestParam) {
        documentBuilder.append(markup).append("请求参数描述").append(newLine).append(replaceNewLinesWithWhiteSpace(requestParam)).append(newLine).append(newLine);
    }

    protected void documentRequestBody(Markup markup, String requestBody) {
        documentBuilder.append(markup).append("请求参数示例").append(newLine).append(replaceNewLinesWithWhiteSpace(requestBody)).append(newLine).append(newLine);
    }

    protected void documentResponseParam(Markup markup, String responseParam) {
        documentBuilder.append(markup).append("返回结果描述").append(newLine).append(replaceNewLinesWithWhiteSpace(responseParam)).append(newLine).append(newLine);
    }

    protected void documentResponseBody(Markup markup, String responseBody) {
        documentBuilder.append(markup).append("返回请求示例").append(newLine).append(replaceNewLinesWithWhiteSpace(responseBody)).append(newLine).append(newLine);
    }

    protected void documentNewLine(Markup markup, String line) {
        documentBuilder.append(markup).append(newLine).append(replaceNewLinesWithWhiteSpace(line)).append(newLine).append(newLine);
    }

    public String replaceNewLinesWithWhiteSpace(String content) {
        return replaceNewLines(content, WHITESPACE);
    }

    public String replaceNewLines(String content, String replacement) {
        if(JMCommonUtil.isEmpty(content)){
            return "";
        }
        return content.replaceAll(NEW_LINES, Matcher.quoteReplacement(replacement));
    }

    protected void delimitedBlockText(Markup begin, String text, Markup end) {
        Validate.notBlank(text, "text must not be blank");
        if (!StringUtils.isBlank(begin.toString()))
            documentBuilder.append(begin).append(newLine);

        documentBuilder.append(replaceNewLines(text, newLine)).append(newLine);
        if (!StringUtils.isBlank(end.toString()))
            documentBuilder.append(end).append(newLine);

        documentBuilder.append(newLine);
    }

    @Override
    public MarkupDocBuilder block(String text, MarkupBlockStyle style) {
        Validate.notBlank(text, "text must not be blank");
        return block(replaceNewLines(text, newLine), style);
    }

    @Override
    public void writeToFile(Path file, Charset charset, OpenOption... options) {
        writeToFileWithoutExtension(file.resolveSibling(addFileExtension(file.getFileName().toString())), charset, options);
    }

    @Override
    public String writeToConsole() {
        return toString();
    }

    /**
     * 2 newLines are needed at the end of file for file to be included without protection.
     */
    @Override
    public void writeToFileWithoutExtension(Path file, Charset charset, OpenOption... options) {
        // Support relative file names both of "filename" and "./filename"
        if (file.getParent() != null) {
            try {
                Files.createDirectories(file.getParent());
            } catch (IOException e) {
                throw new RuntimeException("Failed create directory", e);
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset, options)) {
            writer.write(toString());
            writer.write(newLine);
            writer.write(newLine);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file", e);
        }
    }

    @Override
    public String toString() {
        return documentBuilder.toString();
    }

}
