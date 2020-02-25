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
package com.convert.doc;


import com.convert.doc.interal.markdown.MarkdownBuilder;

/**
 * @author Robert Winkler
 */
public final class MarkupDocBuilders {

    private MarkupDocBuilders() {
    }


    /**
     * Creates a MarkupDocBuilder which uses a custom line separator.
     * If the custom line separator is null, it uses the system line separator.
     * There is a possibility asciidoc generator pegdown (<a href="https://github.com/sirthias/pegdown#parsing-timeouts">optional</a>) can
     * take more time. The default is set to two seconds. To override pass value greater than two seconds.
     *
     * @param markupLanguage               the markup language which is used to generate the files
     * @return a MarkupDocBuilder
     */
    public static MarkupDocBuilder documentBuilder(MarkupLanguage markupLanguage) {
        switch (markupLanguage) {
            case MARKDOWN:
                    return new MarkdownBuilder();
//            case ASCIIDOC:
//                if (lineSeparator == null)
//                    return new AsciiDocBuilder(asciidocPegdownTimeoutMillis);
//                else
//                    return new AsciiDocBuilder(lineSeparator.toString(), asciidocPegdownTimeoutMillis);
//            case CONFLUENCE_MARKUP:
//                if (lineSeparator == null)
//                    return new ConfluenceMarkupBuilder();
//                else
//                    return new ConfluenceMarkupBuilder(lineSeparator.toString());
            default:
                throw new IllegalArgumentException(String.format("Unsupported markup language %s", markupLanguage));
        }
    }
}
