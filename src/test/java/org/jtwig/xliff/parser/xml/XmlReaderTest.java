package org.jtwig.xliff.parser.xml;

import org.jtwig.xliff.parser.xml.exceptions.XmlReaderException;
import org.junit.Test;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class XmlReaderTest {
    private final XmlStartElementFactory xmlStartElementFactory = mock(XmlStartElementFactory.class);
    private final XmlReaderEventFactory xmlReaderEventFactory = mock(XmlReaderEventFactory.class);
    private final XMLEventReader xmlEventReader = mock(XMLEventReader.class);

    private XmlReader underTest = new XmlReader(
            xmlStartElementFactory,
            xmlReaderEventFactory,
            xmlEventReader
    );

    @Test(expected = XmlReaderException.class)
    public void next() throws Exception {
        given(xmlEventReader.hasNext()).willReturn(true);
        given(xmlEventReader.nextEvent()).willThrow(new XMLStreamException());

        underTest.nextStartElement("test");
    }

    @Test(expected = XmlReaderException.class)
    public void text() throws Exception {
        given(xmlEventReader.hasNext()).willReturn(true);
        given(xmlEventReader.nextEvent()).willThrow(new XMLStreamException());

        underTest.textInsideCurrentElement();
    }

    @Test(expected = XmlReaderException.class)
    public void current() throws Exception {
        XmlReaderEvent result = underTest.currentEvent();
    }
}