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

import com.jiahangchun.test.tp.convert.doc.interal.Markup;

/**
 * @author Robert Winkler
 */
public enum Markdown implements Markup {
    TABLE_COLUMN_DELIMITER("|"),
    TABLE_ROW("-"),
    LISTING("```"),
    LISTING_JSON("```json"),
    TITLE("#"),
    DOCUMENT_TITLE("# "),
    LITERAL("`"),
    BOLD("**"),
    ITALIC("*"),
    LIST_ENTRY("* "),
    SPACE_ESCAPE("-"),
    LINE_BREAK("  ");

    private final String markup;

    /**
     * @param markup AsciiDoc markup
     */
    private Markdown(final String markup) {
        this.markup = markup;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return markup;
    }
}
