package org.jtwig.xliff.i18n;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.translate.message.source.factory.MessageSourceFactory;
import org.jtwig.translate.message.source.localized.AggregatedLocalizedMessageSourceFactory;
import org.jtwig.translate.message.source.localized.provider.ClasspathLocalizedResourceProvider;
import org.jtwig.translate.message.source.localized.provider.CompositeLocalizedResourceProvider;
import org.jtwig.translate.message.source.localized.provider.FileLocalizedResourceProvider;
import org.jtwig.translate.message.source.localized.provider.LocalizedResourceProvider;
import org.jtwig.xliff.parser.TranslationFileParser;
import org.jtwig.xliff.parser.TranslationUnitParser;
import org.jtwig.xliff.parser.XliffParser;
import org.jtwig.xliff.parser.xml.XmlReaderFactory;

import javax.xml.stream.XMLInputFactory;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;

import static org.jtwig.translate.message.source.localized.provider.file.DirectoryFileFinder.directory;
import static org.jtwig.translate.message.source.localized.provider.file.RecursiveFileFinder.recursiveDirectory;
import static org.jtwig.xliff.i18n.XmlFileFilter.xmlFileFilter;

public class XliffMessageSourceFactoryBuilder implements Builder<MessageSourceFactory> {
    public static XliffMessageSourceFactoryBuilder xliffMessageSource () {
        return new XliffMessageSourceFactoryBuilder(xmlFileFilter());
    }

    private final FileFilter fileFilter;
    private final Collection<LocalizedResourceProvider> localizedResourceProviders = new ArrayList<>();

    public XliffMessageSourceFactoryBuilder(FileFilter fileFilter) {
        this.fileFilter = fileFilter;
    }

    public XliffMessageSourceFactoryBuilder withLookupDirectory (String directory) {
        return withLookupDirectory(new File(directory));
    }

    public XliffMessageSourceFactoryBuilder withLookupDirectory (File directory) {
        localizedResourceProviders.add(new FileLocalizedResourceProvider(directory, fileFilter, directory()));
        return this;
    }
    public XliffMessageSourceFactoryBuilder withLookupDirectoryRecursively (String directory) {
        return withLookupDirectoryRecursively(new File(directory));
    }

    public XliffMessageSourceFactoryBuilder withLookupDirectoryRecursively (File directory) {
        localizedResourceProviders.add(new FileLocalizedResourceProvider(directory, fileFilter, recursiveDirectory()));
        return this;
    }

    public XliffMessageSourceFactoryBuilder withLookupClasspath (String basePackage) {
        localizedResourceProviders.add(new ClasspathLocalizedResourceProvider(getClass().getClassLoader(), basePackage, fileFilter, directory()));
        return this;
    }

    public XliffMessageSourceFactoryBuilder withLookupClasspathRecursively (String basePackage) {
        localizedResourceProviders.add(new ClasspathLocalizedResourceProvider(getClass().getClassLoader(), basePackage, fileFilter, recursiveDirectory()));
        return this;
    }

    @Override
    public MessageSourceFactory build() {
        return new AggregatedLocalizedMessageSourceFactory(
                new CompositeLocalizedResourceProvider(localizedResourceProviders),
                new XliffLocalizedMessageResourceLoader(new XliffParser(new XmlReaderFactory(XMLInputFactory.newFactory()), new TranslationFileParser(new TranslationUnitParser())))
        );
    }
}
