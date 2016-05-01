package org.jtwig.xliff.i18n;

import java.io.File;
import java.io.FileFilter;

public class XmlFileFilter implements FileFilter {
    public static final String XML = ".xml";

    public static XmlFileFilter xmlFileFilter() {
        return new XmlFileFilter();
    }

    private XmlFileFilter() {}

    @Override
    public boolean accept(File pathname) {
        return pathname.getName().contains(XML);
    }
}
