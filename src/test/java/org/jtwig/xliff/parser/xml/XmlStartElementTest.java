package org.jtwig.xliff.parser.xml;

import com.google.common.base.Optional;
import org.junit.Test;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class XmlStartElementTest {
    XmlAttributeFactory xmlAttributeFactory = mock(XmlAttributeFactory.class);
    StartElement startElement = mock(StartElement.class);

    XmlStartElement underTest = new XmlStartElement(xmlAttributeFactory, startElement);

    @Test
    public void xmlStartNoAttributes() throws Exception {
        given(startElement.getAttributes()).willReturn(asList().iterator());

        Optional<XmlAttribute> result = underTest.getAttribute("test");

        assertThat(result.isPresent(), is(false));
    }
    @Test
    public void xmlStartNullNameAttribute() throws Exception {
        Attribute attribute = mock(Attribute.class);
        given(startElement.getAttributes()).willReturn(asList(attribute).iterator());
        given(attribute.getName()).willReturn(null);

        Optional<XmlAttribute> result = underTest.getAttribute("test");

        assertThat(result.isPresent(), is(false));
    }
}