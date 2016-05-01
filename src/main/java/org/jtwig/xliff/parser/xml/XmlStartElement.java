package org.jtwig.xliff.parser.xml;

import com.google.common.base.Optional;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.Iterator;

public class XmlStartElement {
    private final XmlAttributeFactory xmlAttributeFactory;
    private final StartElement event;

    public XmlStartElement(XmlAttributeFactory xmlAttributeFactory, StartElement event) {
        this.xmlAttributeFactory = xmlAttributeFactory;
        this.event = event;
    }

    public boolean hasName(String name) {
        return this.event.getName() != null && name.equals(this.event.getName().getLocalPart());
    }

    public Optional<XmlAttribute> getAttribute(String name) {
        Iterator<Attribute> attributeIterator = this.event.getAttributes();
        while (attributeIterator.hasNext()) {
            Attribute next = attributeIterator.next();
            if (next.getName() != null && name.equals(next.getName().getLocalPart())) {
                return Optional.of(xmlAttributeFactory.create(next));
            }
        }

        return Optional.absent();
    }
}
