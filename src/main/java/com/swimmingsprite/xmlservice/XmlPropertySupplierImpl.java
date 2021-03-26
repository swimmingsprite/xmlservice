package com.swimmingsprite.xmlservice;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class XmlPropertySupplierImpl implements XmlPropertySupplier {
    private static final String CSV_XSD_PATHS_CLASSES_FILE_NAME = "XSDPathsAndClasses.csv";
    private static final String CSV_XSL_PATHS_VARIANTS_FILE_NAME = "XSLPathsAndVariants.csv";
    private static final String CSV_NAMESPACE_CLASSES_FILE_NAME = "NamespaceClasses.csv";
    private final PathConstructor pathConstructor;


    public XmlPropertySupplierImpl(CsvFileParser csvFileParser, PathConstructor pathConstructor) {
        this.csvFileParser = csvFileParser;
        this.pathConstructor = pathConstructor;
    }

    private CsvFileParser csvFileParser;


    private static Map<String, String> namespaceXsdPaths;// = new ConcurrentHashMap<>();
    private static Map<String, Class<?>> namespaceClasses;// = new ConcurrentHashMap<>();
    private static Map<String, Map<String, String>> namespaceVariantsXslPaths;// = new ConcurrentHashMap<>();


    @EventListener(ApplicationReadyEvent.class)
    public void fetchAllProperties() {
        namespaceXsdPaths = csvFileParser.getXsdPaths(
                pathConstructor.getCsvPath(CSV_XSL_PATHS_VARIANTS_FILE_NAME));
        namespaceClasses = csvFileParser.getNamespaceClasses(
                pathConstructor.getCsvPath(CSV_NAMESPACE_CLASSES_FILE_NAME));
        namespaceVariantsXslPaths = csvFileParser.getXslVariantsPaths(
                pathConstructor.getCsvPath(CSV_XSL_PATHS_VARIANTS_FILE_NAME));
    }


    /*
     * namepace xsdPath
     * namespace className
     * namespace variant - stylesheetPath
     * */


    @Override
    public List<String> getAllXslVariants(String namespace) {
        if (namespaceVariantsXslPaths.get(namespace) != null)
            return new ArrayList<>(namespaceVariantsXslPaths.get(namespace).values());
        return Collections.emptyList();
    }

    @Override
    public String getXslVariantPath(String namespace, String variant) {
        if (namespace == null || variant == null) return null;
        Map<String, String> innerMap = namespaceVariantsXslPaths.get(namespace);
        if (innerMap != null) return innerMap.get(variant);
        return null;
    }

    @Override
    public String getXsdLocation(String namespace) {
        return namespaceXsdPaths.getOrDefault(namespace, null);
    }

    @Override
    public Class<?> getClass(String namespace) {
        return namespaceClasses.getOrDefault(namespace, null);
    }
}
