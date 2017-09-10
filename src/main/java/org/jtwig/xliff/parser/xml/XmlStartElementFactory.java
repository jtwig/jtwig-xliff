package org.jtwig.xliff.parser.xml;

import javax.xml.stream.events.XMLEvent;

public class XmlStartElementFactory {
    private final XmlAttributeFactory xmlAttributeFactory;

    public XmlStartElementFactory(XmlAttributeFactory xmlAttributeFactory) {
        this.xmlAttributeFactory = xmlAttributeFactory;
    }

    public XmlStartElement create (XMLEvent event) {
        return new XmlStartElement(xmlAttributeFactory, event.asStartElement());
    }
}
