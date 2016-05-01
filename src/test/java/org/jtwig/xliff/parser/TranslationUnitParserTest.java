package org.jtwig.xliff.parser;

import com.google.common.base.Optional;
import org.jtwig.xliff.exceptions.XliffParsingException;
import org.jtwig.xliff.model.TranslationUnit;
import org.jtwig.xliff.parser.xml.XmlReader;
import org.jtwig.xliff.parser.xml.XmlStartElement;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TranslationUnitParserTest {
    private TranslationUnitParser underTest = new TranslationUnitParser();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void parseNoSource() throws Exception {
        XmlReader reader = mock(XmlReader.class);

        when(reader.nextStartElement("source")).thenReturn(Optional.<XmlStartElement>absent());

        expectedException.expect(XliffParsingException.class);

        underTest.parse(reader);
    }
    @Test
    public void parseNoTarget() throws Exception {
        XmlReader reader = mock(XmlReader.class);

        when(reader.nextStartElement("source")).thenReturn(Optional.of(mock(XmlStartElement.class)));
        when(reader.nextStartElement("target")).thenReturn(Optional.<XmlStartElement>absent());

        expectedException.expect(XliffParsingException.class);

        underTest.parse(reader);
    }
    @Test
    public void parseBothExist() throws Exception {
        XmlReader reader = mock(XmlReader.class);

        when(reader.nextStartElement("source")).thenReturn(Optional.of(mock(XmlStartElement.class)));
        when(reader.nextStartElement("target")).thenReturn(Optional.of(mock(XmlStartElement.class)));
        when(reader.textInsideCurrentElement()).thenReturn("one", "two");

        TranslationUnit result = underTest.parse(reader);

        assertThat(result.getSource(), is("one"));
        assertThat(result.getTarget(), is("two"));
    }
}