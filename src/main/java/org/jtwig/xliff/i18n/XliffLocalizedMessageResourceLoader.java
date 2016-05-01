package org.jtwig.xliff.i18n;

import org.jtwig.environment.Environment;
import org.jtwig.resource.ResourceService;
import org.jtwig.resource.exceptions.ResourceException;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.translate.TranslateExtension;
import org.jtwig.translate.message.source.localized.loader.LocalizedMessageResourceLoader;
import org.jtwig.translate.message.source.localized.resource.InMemoryLocalizedMessageResource;
import org.jtwig.translate.message.source.localized.resource.LocalizedMessageResource;
import org.jtwig.xliff.exceptions.XliffParsingException;
import org.jtwig.xliff.model.TranslationFile;
import org.jtwig.xliff.model.TranslationUnit;
import org.jtwig.xliff.parser.XliffParser;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class XliffLocalizedMessageResourceLoader implements LocalizedMessageResourceLoader {
    private final XliffParser xliffParser;

    public XliffLocalizedMessageResourceLoader(XliffParser xliffParser) {
        this.xliffParser = xliffParser;
    }

    @Override
    public LocalizedMessageResource load(Environment environment, ResourceReference resourceReference) {
        ResourceService resourceService = environment.getResourceEnvironment().getResourceService();
        ResourceMetadata resourceMetadata = resourceService.loadMetadata(resourceReference);
        if (!resourceMetadata.exists()) {
            throw new ResourceNotFoundException(String.format("Resource '%s' not found", resourceReference));
        } else {
            try {
                TranslationFile parsedFile = xliffParser.parse(resourceMetadata.load());
                Locale locale = TranslateExtension.enviroment(environment).getLocaleResolver().resolve(parsedFile.getTargetLanguage());

                Map<String, String> definitions = new HashMap<>();
                for (TranslationUnit unit : parsedFile) {
                    definitions.put(unit.getSource().trim(), unit.getTarget().trim());
                }
                return new InMemoryLocalizedMessageResource(locale, definitions);
            } catch (XliffParsingException e) {
                throw new ResourceException(String.format("Cannot load xliff file '%s'", resourceReference), e);
            }
        }
    }
}
