package org.jtwig.xliff.model;

import java.util.Collection;
import java.util.Iterator;

public class TranslationFile implements Iterable<TranslationUnit> {
    private final String targetLanguage;
    private final Collection<TranslationUnit> translationUnits;

    public TranslationFile(String targetLanguage, Collection<TranslationUnit> translationUnits) {
        this.targetLanguage = targetLanguage;
        this.translationUnits = translationUnits;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public Collection<TranslationUnit> getTranslationUnits() {
        return translationUnits;
    }

    @Override
    public Iterator<TranslationUnit> iterator() {
        return translationUnits.iterator();
    }
}
