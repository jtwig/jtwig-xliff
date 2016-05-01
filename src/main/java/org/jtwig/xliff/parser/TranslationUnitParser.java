package org.jtwig.xliff.parser;

import com.google.common.base.Optional;
import org.jtwig.xliff.exceptions.XliffParsingException;
import org.jtwig.xliff.model.TranslationUnit;
import org.jtwig.xliff.parser.xml.XmlReader;
import org.jtwig.xliff.parser.xml.XmlStartElement;
import org.jtwig.xliff.parser.xml.exceptions.XmlReaderException;

public class TranslationUnitParser implements XmlParser<TranslationUnit> {
    public static final String SOURCE = "source";
    public static final String TARGET = "target";

    @Override
    public TranslationUnit parse(XmlReader reader) throws XliffParsingException {
        try {
            Optional<XmlStartElement> sourceElement = reader.nextStartElement(SOURCE);
            if (!sourceElement.isPresent())
                throw new XliffParsingException(String.format("Xliff is expected to have one '%s' element", SOURCE));
            String source = reader.textInsideCurrentElement();

            Optional<XmlStartElement> targetElement = reader.nextStartElement(TARGET);
            if (!targetElement.isPresent())
                throw new XliffParsingException(String.format("Jtwig xliff is expecting a '%s' element", TARGET));

            String target = reader.textInsideCurrentElement();

            return new TranslationUnit(source, target);
        } catch (XmlReaderException e) {
            throw new XliffParsingException(e);
        }
    }
}
