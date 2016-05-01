package org.jtwig.xliff.parser;

import com.google.common.base.Optional;
import org.jtwig.xliff.exceptions.XliffParsingException;
import org.jtwig.xliff.model.TranslationFile;
import org.jtwig.xliff.parser.xml.XmlReader;
import org.jtwig.xliff.parser.xml.XmlReaderFactory;
import org.jtwig.xliff.parser.xml.XmlStartElement;
import org.jtwig.xliff.parser.xml.exceptions.XmlReaderException;

import java.io.InputStream;

public class XliffParser {
    private static final String FILE_TAG = "file";
    private final XmlReaderFactory xmlReaderFactory;
    private final TranslationFileParser translationFileParser;

    public XliffParser(XmlReaderFactory xmlReaderFactory, TranslationFileParser translationFileParser) {
        this.xmlReaderFactory = xmlReaderFactory;
        this.translationFileParser = translationFileParser;
    }

    public TranslationFile parse(InputStream inputStream) throws XliffParsingException {
        try {
            XmlReader reader = xmlReaderFactory.create(inputStream);
            Optional<XmlStartElement> xmlEvent = reader.nextStartElement(FILE_TAG);
            if (!xmlEvent.isPresent()) throw new XliffParsingException("xliff files are expected to have at least one 'file' element");
            return translationFileParser.parse(reader);
        } catch (XmlReaderException e) {
            throw new XliffParsingException(e);
        }
    }
}
