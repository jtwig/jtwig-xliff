package org.jtwig.xliff.parser;

import com.google.common.base.Optional;
import org.jtwig.xliff.exceptions.XliffParsingException;
import org.jtwig.xliff.parser.xml.XmlReader;
import org.jtwig.xliff.parser.xml.XmlReaderFactory;
import org.jtwig.xliff.parser.xml.XmlStartElement;
import org.jtwig.xliff.parser.xml.exceptions.XmlReaderException;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class XliffParserTest {
    private final XmlReaderFactory xmlReaderFactory = mock(XmlReaderFactory.class);
    private final TranslationFileParser translationFileParser = mock(TranslationFileParser.class);
    private XliffParser underTest = new XliffParser(xmlReaderFactory, translationFileParser);

    @Test(expected = XliffParsingException.class)
    public void readerException() throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("test".getBytes());

        given(xmlReaderFactory.create(inputStream)).willThrow(new XmlReaderException("test"));

        underTest.parse(inputStream);
    }

    @Test(expected = XliffParsingException.class)
    public void noStart() throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("test".getBytes());
        XmlReader reader = mock(XmlReader.class);

        given(xmlReaderFactory.create(inputStream)).willReturn(reader);
        given(reader.nextStartElement("file")).willReturn(Optional.<XmlStartElement>absent());

        underTest.parse(inputStream);
    }
}