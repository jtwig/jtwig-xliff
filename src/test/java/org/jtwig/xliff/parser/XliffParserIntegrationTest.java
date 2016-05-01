package org.jtwig.xliff.parser;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.translate.TranslateExtension;
import org.jtwig.translate.configuration.TranslateConfigurationBuilder;
import org.jtwig.xliff.i18n.XliffMessageSourceFactoryBuilder;
import org.jtwig.xliff.model.TranslationFile;
import org.jtwig.xliff.parser.xml.XmlReaderFactory;
import org.junit.Test;

import javax.xml.stream.XMLInputFactory;
import java.io.InputStream;

import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;
import static org.junit.Assert.assertThat;

public class XliffParserIntegrationTest {
    private final XliffParser underTest = new XliffParser(new XmlReaderFactory(XMLInputFactory.newFactory()), new TranslationFileParser(new TranslationUnitParser()));

    @Test
    public void parse() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("xliff-example.xml");

        TranslationFile result = underTest.parse(inputStream);

        assertThat(result.getTranslationUnits().size(), is(2));
    }

    @Test
    public void template() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 'Hello world' | translate('pt') }}", configuration()
                .extensions()
                .add(new TranslateExtension(TranslateConfigurationBuilder.translateConfiguration()
                        .withMessageSourceFactory(XliffMessageSourceFactoryBuilder.xliffMessageSource()
                                .withLookupClasspath("")
                                .build())
                        .build()))
                .and()
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("Ola Mundo"));
    }
}