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
package com.jiahangchun.test.tp.convert.doc.interal.markdown;

import com.jiahangchun.test.tp.common.CommonUtil;
import com.jiahangchun.test.tp.convert.doc.MarkupDocBuilder;
import com.jiahangchun.test.tp.convert.doc.MarkupLanguage;
import com.jiahangchun.test.tp.convert.doc.interal.AbstractMarkupDocBuilder;
import com.jiahangchun.test.tp.convert.doc.interal.Markup;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * @author Robert Winkler
 */
public class MarkdownBuilder extends AbstractMarkupDocBuilder {

    private static final Map<MarkupBlockStyle, String> BLOCK_STYLE = new HashMap<MarkupBlockStyle, String>() {{
        put(MarkupBlockStyle.LISTING, Markdown.LISTING.toString());
    }};


    public MarkdownBuilder() {
        super();
    }

    public MarkdownBuilder(String newLine) {
        super(newLine);
    }

    @Override
    public MarkupDocBuilder documentDescription(String description) {
        documentDescription(Markdown.DOCUMENT_TITLE, description);
        return this;
    }

    @Override
    public MarkupDocBuilder documentUrl(String url) {
        documentUrl(Markdown.DOCUMENT_TITLE, url);
        return this;
    }

    @Override
    public MarkupDocBuilder documentMethod(String method) {
        documentMethod(Markdown.DOCUMENT_TITLE, method);
        return this;
    }

    @Override
    public MarkupDocBuilder documentRequestParam(String requestParam) {
        documentRequestParam(Markdown.DOCUMENT_TITLE, requestParam);
        return this;
    }

    @Override
    public MarkupDocBuilder documentRequestBody(String requestBody) {
        documentRequestBody(Markdown.DOCUMENT_TITLE, "");
        block(requestBody, MarkupBlockStyle.LISTING);
        return this;
    }

    @Override
    public MarkupDocBuilder documentResponseParam(String requestParam) {
        documentResponseParam(Markdown.DOCUMENT_TITLE, requestParam);
        return this;
    }

    @Override
    public MarkupDocBuilder documentResponseBody(String responseBody) {
        documentResponseBody(Markdown.DOCUMENT_TITLE, responseBody);
        return this;
    }

    @Override
    public MarkupDocBuilder table(List<String> columns, List<List<String>> cells) {
        Validate.notEmpty(cells, "cells must not be null");
        Validate.notEmpty(cells, "columns must not be null");
        if (CommonUtil.isNotEmpty(columns)) {
            List<String> headerList = columns.stream()
                    .map(x -> formatTableCell(defaultString(x)))
                    .collect(Collectors.toList());
            documentBuilder.append(Markdown.TABLE_COLUMN_DELIMITER).append(join(headerList, Markdown.TABLE_COLUMN_DELIMITER.toString())).append(Markdown.TABLE_COLUMN_DELIMITER).append(newLine);
            //header和columns之间的隔离
            documentBuilder.append(Markdown.TABLE_COLUMN_DELIMITER);
            headerList.forEach(col -> {
                documentBuilder.append(StringUtils.repeat(Markdown.TABLE_ROW.toString(), 3));
                documentBuilder.append(Markdown.TABLE_COLUMN_DELIMITER);
            });
            documentBuilder.append(newLine);
        }
        for (List<String> row : cells) {
            Collection<String> cellList = row.stream().map(cell -> formatTableCell(defaultString(cell))).collect(Collectors.toList());
            documentBuilder.append(Markdown.TABLE_COLUMN_DELIMITER).append(join(cellList, Markdown.TABLE_COLUMN_DELIMITER.toString())).append(Markdown.TABLE_COLUMN_DELIMITER).append(newLine);
        }
        return this;
    }


    private String formatTableCell(String cell) {
        cell = replaceNewLines(cell.trim(), "<br>");
        return cell.replace(Markdown.TABLE_COLUMN_DELIMITER.toString(), "\\" + Markdown.TABLE_COLUMN_DELIMITER.toString());
    }

    @Override
    public MarkupDocBuilder block(String text, MarkupBlockStyle style) {
        Markup m = new Markup() {
            public String toString() {
                return BLOCK_STYLE.get(style);
            }
        };
        delimitedBlockText(m, text, m);
        return this;
    }

    @Override
    public String addFileExtension(String fileName) {
        return fileName + MarkupLanguage.MARKDOWN.getFileNameExtensions().get(0);
    }
}
