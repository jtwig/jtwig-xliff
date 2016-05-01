package org.jtwig.xliff.parser.xml;

import javax.xml.stream.events.XMLEvent;

public class XmlReaderEventFactory {
    private final XmlStartElementFactory xmlStartElementFactory;

    public XmlReaderEventFactory(XmlStartElementFactory xmlStartElementFactory) {
        this.xmlStartElementFactory = xmlStartElementFactory;
    }

    public XmlReaderEvent create (XMLEvent event) {
        return new XmlReaderEvent(xmlStartElementFactory, event);
    }
}
