package com.jiahangchun.test.tp.convert.config;

import com.jiahangchun.test.tp.convert.doc.MarkupLanguage;
import com.jiahangchun.test.tp.convert.doc.interal.LineSeparator;
import lombok.Data;

@Data
public class ConfigBO {

    private Language outputLanguage;

    private MarkupLanguage markupLanguage;

    private LineSeparator lineSeparator;

    private int asciidocPegdownTimeoutMillis;

    private String anchorPrefix;

}
