package org.jtwig.xliff.parser.xml;

import org.jtwig.xliff.parser.xml.exceptions.XmlReaderException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.InputStream;

public class XmlReaderFactory {
    private final XMLInputFactory factory;

    public XmlReaderFactory(XMLInputFactory factory) {
        this.factory = factory;
    }

    public XmlReader create (InputStream inputStream) throws XmlReaderException {
        XmlAttributeFactory xmlAttributeFactory = new XmlAttributeFactory();
        XmlStartElementFactory xmlStartElementFactory = new XmlStartElementFactory(xmlAttributeFactory);
        XmlReaderEventFactory xmlReaderEventFactory = new XmlReaderEventFactory(xmlStartElementFactory);
        try {
            XMLEventReader xmlEventReader = factory.createXMLEventReader(inputStream);
            return new XmlReader(xmlStartElementFactory, xmlReaderEventFactory, xmlEventReader);
        } catch (XMLStreamException e) {
            throw new XmlReaderException(e);
        }
    }
}
