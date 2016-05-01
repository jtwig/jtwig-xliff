package org.jtwig.xliff.i18n;

import org.jtwig.environment.Environment;
import org.jtwig.resource.exceptions.ResourceException;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.xliff.exceptions.XliffParsingException;
import org.jtwig.xliff.parser.XliffParser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.InputStream;

import static org.mockito.Mockito.*;

public class XliffLocalizedMessageResourceLoaderTest {
    private final XliffParser xliffParser = mock(XliffParser.class);
    private XliffLocalizedMessageResourceLoader underTest = new XliffLocalizedMessageResourceLoader(xliffParser);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void loadResourceNotFound() throws Exception {
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        ResourceReference resourceReference = mock(ResourceReference.class);
        ResourceMetadata resourceMetadata = mock(ResourceMetadata.class);

        when(environment.getResourceEnvironment().getResourceService().loadMetadata(resourceReference))
                .thenReturn(resourceMetadata);
        when(resourceMetadata.exists()).thenReturn(false);

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(String.format("Resource '%s' not found", resourceReference));

        underTest.load(environment, resourceReference);
    }

    @Test
    public void loadResourceInvalid() throws Exception {
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        ResourceReference resourceReference = mock(ResourceReference.class);
        ResourceMetadata resourceMetadata = mock(ResourceMetadata.class);
        InputStream inputStream = mock(InputStream.class);

        when(environment.getResourceEnvironment().getResourceService().loadMetadata(resourceReference))
                .thenReturn(resourceMetadata);
        when(resourceMetadata.exists()).thenReturn(true);
        when(resourceMetadata.load()).thenReturn(inputStream);
        when(xliffParser.parse(inputStream)).thenThrow(XliffParsingException.class);

        expectedException.expect(ResourceException.class);
        expectedException.expectMessage(String.format("Cannot load xliff file '%s'", resourceReference));

        underTest.load(environment, resourceReference);
    }
}