package com.convert.config;

import com.convert.doc.MarkupLanguage;
import com.convert.doc.interal.LineSeparator;


public class ConfigBO {

    private Language outputLanguage;

    private MarkupLanguage markupLanguage;

    private LineSeparator lineSeparator;

    private int asciidocPegdownTimeoutMillis;

    private String anchorPrefix;

    public Language getOutputLanguage() {
        return outputLanguage;
    }

    public void setOutputLanguage(Language outputLanguage) {
        this.outputLanguage = outputLanguage;
    }

    public MarkupLanguage getMarkupLanguage() {
        return markupLanguage;
    }

    public void setMarkupLanguage(MarkupLanguage markupLanguage) {
        this.markupLanguage = markupLanguage;
    }

    public LineSeparator getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(LineSeparator lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public int getAsciidocPegdownTimeoutMillis() {
        return asciidocPegdownTimeoutMillis;
    }

    public void setAsciidocPegdownTimeoutMillis(int asciidocPegdownTimeoutMillis) {
        this.asciidocPegdownTimeoutMillis = asciidocPegdownTimeoutMillis;
    }
}
