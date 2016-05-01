package org.jtwig.xliff.parser;

import org.jtwig.xliff.exceptions.XliffParsingException;
import org.jtwig.xliff.parser.xml.XmlReader;

public interface XmlParser<T> {
    T parse (XmlReader reader) throws XliffParsingException;
}
