package org.jtwig.xliff.parser;

import com.google.common.base.Optional;
import org.jtwig.xliff.exceptions.XliffParsingException;
import org.jtwig.xliff.parser.xml.XmlAttribute;
import org.jtwig.xliff.parser.xml.XmlReader;
import org.jtwig.xliff.parser.xml.XmlReaderEvent;
import org.jtwig.xliff.parser.xml.XmlStartElement;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TranslationFileParserTest {
    private final TranslationUnitParser translationUnitParser = mock(TranslationUnitParser.class);
    private TranslationFileParser underTest = new TranslationFileParser(translationUnitParser);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void parseNoTargetAttribute() throws Exception {
        XmlReader xmlReader = mock(XmlReader.class);
        XmlReaderEvent xmlReaderEvent = mock(XmlReaderEvent.class);
        XmlStartElement xmlStartElement = mock(XmlStartElement.class);

        when(xmlReader.currentEvent()).thenReturn(xmlReaderEvent);
        when(xmlReaderEvent.asStartElement()).thenReturn(xmlStartElement);
        when(xmlStartElement.getAttribute("target-language")).thenReturn(Optional.<XmlAttribute>absent());

        expectedException.expect(XliffParsingException.class);

        underTest.parse(xmlReader);
    }
}