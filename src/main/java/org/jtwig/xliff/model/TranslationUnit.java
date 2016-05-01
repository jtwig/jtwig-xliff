package org.jtwig.xliff.model;

public class TranslationUnit {
    private final String source;
    private final String target;

    public TranslationUnit(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }
}
