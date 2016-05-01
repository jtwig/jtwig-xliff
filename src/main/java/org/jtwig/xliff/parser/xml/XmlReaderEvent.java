package org.jtwig.xliff.parser.xml;

import javax.xml.stream.events.XMLEvent;

public class XmlReaderEvent {
    private final XmlStartElementFactory xmlStartElementFactory;
    private final XMLEvent event;

    public XmlReaderEvent(XmlStartElementFactory xmlStartElementFactory, XMLEvent event) {
        this.xmlStartElementFactory = xmlStartElementFactory;
        this.event = event;
    }

    public boolean isStartElement() {
        return event.isStartElement();
    }

    public XmlStartElement asStartElement() {
        return xmlStartElementFactory.create(event);
    }
}
