package org.jtwig.xliff.parser;

import com.google.common.base.Optional;
import org.jtwig.xliff.exceptions.XliffParsingException;
import org.jtwig.xliff.model.TranslationFile;
import org.jtwig.xliff.model.TranslationUnit;
import org.jtwig.xliff.parser.xml.XmlAttribute;
import org.jtwig.xliff.parser.xml.XmlReader;
import org.jtwig.xliff.parser.xml.XmlStartElement;
import org.jtwig.xliff.parser.xml.exceptions.XmlReaderException;

import java.util.ArrayList;
import java.util.Collection;

public class TranslationFileParser implements XmlParser<TranslationFile> {
    public static final String TARGET_LANGUAGE_ATTRIBUTE = "target-language";
    public static final String TRANS_UNIT_TAG_NAME = "trans-unit";

    private final TranslationUnitParser translationUnitParser;

    public TranslationFileParser(TranslationUnitParser translationUnitParser) {
        this.translationUnitParser = translationUnitParser;
    }

    @Override
    public TranslationFile parse(XmlReader reader) throws XliffParsingException {
        try {
            Collection<TranslationUnit> units = new ArrayList<>();

            XmlStartElement startElement = reader.currentEvent().asStartElement();
            Optional<XmlAttribute> attribute = startElement.getAttribute(TARGET_LANGUAGE_ATTRIBUTE);
            if (!attribute.isPresent())
                throw new XliffParsingException(String.format("No '%s' attribute specified in the file element. This is a required attribute.", TARGET_LANGUAGE_ATTRIBUTE));

            Optional<XmlStartElement> event = reader.nextStartElement(TRANS_UNIT_TAG_NAME);
            while (event.isPresent()) {
                units.add(translationUnitParser.parse(reader));
                event = reader.nextStartElement(TRANS_UNIT_TAG_NAME);
            }

            return new TranslationFile(attribute.get().getValue(), units);
        } catch (XmlReaderException e) {
            throw new XliffParsingException(e);
        }
    }
}
