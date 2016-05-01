package org.jtwig.xliff.parser.xml;

import com.google.common.base.Optional;
import org.jtwig.xliff.parser.xml.exceptions.XmlReaderException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.concurrent.atomic.AtomicReference;

public class XmlReader {
    private final XmlStartElementFactory xmlStartElementFactory;
    private final XmlReaderEventFactory xmlReaderEventFactory;
    private final XMLEventReader xmlEventReader;
    private final AtomicReference<XMLEvent> currentEvent = new AtomicReference<>();

    public XmlReader(XmlStartElementFactory xmlStartElementFactory, XmlReaderEventFactory xmlReaderEventFactory, XMLEventReader xmlEventReader) {
        this.xmlStartElementFactory = xmlStartElementFactory;
        this.xmlReaderEventFactory = xmlReaderEventFactory;
        this.xmlEventReader = xmlEventReader;
    }

    public Optional<XmlStartElement> nextStartElement(String name) throws XmlReaderException {
        try {
            while (xmlEventReader.hasNext()) {
                XMLEvent nextTag = xmlEventReader.nextEvent();
                currentEvent.set(nextTag);
                if (nextTag.isStartElement()) {
                    StartElement startElement = nextTag.asStartElement();
                    if (startElement.getName() != null && name.equals(startElement.getName().getLocalPart())) {
                        return Optional.of(xmlStartElementFactory.create(nextTag));
                    }
                }
            }

            return Optional.absent();
        } catch (XMLStreamException e) {
            throw new XmlReaderException(e);
        }
    }

    public XmlReaderEvent currentEvent() throws XmlReaderException {
        if (currentEvent.get() == null) throw new XmlReaderException("No current event");
        return xmlReaderEventFactory.create(currentEvent.get());
    }

    public String textInsideCurrentElement() throws XmlReaderException {
        try {
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                currentEvent.set(xmlEvent);

                if (xmlEvent.isEndElement() || xmlEvent.isEndDocument())
                    return "";

                if (xmlEvent.isCharacters()) {
                    return xmlEvent.asCharacters().getData();
                }
            }

            return "";
        } catch (XMLStreamException e) {
            throw new XmlReaderException(e);
        }
    }
}
