package org.jtwig.xliff.parser.xml;

import javax.xml.stream.events.Attribute;

public class XmlAttributeFactory {
    public XmlAttribute create(Attribute attribute) {
        return new XmlAttribute(attribute);
    }
}
