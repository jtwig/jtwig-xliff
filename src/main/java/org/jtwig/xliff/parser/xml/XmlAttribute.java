package org.jtwig.xliff.parser.xml;

import javax.xml.stream.events.Attribute;

public class XmlAttribute {
    private final Attribute attribute;

    public XmlAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return attribute.getValue();
    }
}
