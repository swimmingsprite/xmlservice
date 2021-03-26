package com.swimmingsprite.xmlservice;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class XmlPropertySupplierImpl implements XmlPropertySupplier {
    private static final String CSV_XSD_PATHS_CLASSES_FILE_NAME = "XSDPathsAndClasses.csv" ;
    private static final String CSV_XSL_PATHS_VARIANTS_FILE_NAME = "XSLPathsAndVariants.csv" ;
    private static final String CSV_NAMESPACE_CLASSES_FILE_NAME = "NamespaceClasses.csv" ;
    private final Environment env;


    public XmlPropertySupplierImpl(CsvFileParser csvFileParser, Environment env) {
        this.csvFileParser = csvFileParser;
        this.env = env;
    }

    private CsvFileParser csvFileParser;


    private static Map<String, String> namespaceXsdPaths;// = new ConcurrentHashMap<>();
    private static Map<String, Class<?>> namespaceClasses;// = new ConcurrentHashMap<>();
    private static Map<String, Map<String, String>> namespaceVariantsXslPaths;// = new ConcurrentHashMap<>();


    @EventListener(ApplicationReadyEvent.class)
    public void fetchAllProperties() {
        String csvAbsolutePath = getCsvAbsolutePath();
        if (csvAbsolutePath != null) {
            namespaceXsdPaths = csvFileParser.getXsdPaths(csvAbsolutePath+CSV_XSL_PATHS_VARIANTS_FILE_NAME);
            namespaceClasses = csvFileParser.getNamespaceClasses(csvAbsolutePath+CSV_NAMESPACE_CLASSES_FILE_NAME);
            namespaceVariantsXslPaths = csvFileParser.getXslVariantsPaths(csvAbsolutePath+CSV_XSL_PATHS_VARIANTS_FILE_NAME);
        }

    }

    private String getCsvAbsolutePath() {
        if ("true".equals(env.getProperty("xml.resources.csvFetchFromDefault"))) {
            String defaultCsvPath =
                    constructPathFromRoot(env.getProperty("xml.resources.csvDefaultFilesRelativePath"));
            if (defaultCsvPath != null)
                return !defaultCsvPath.endsWith("/") ? defaultCsvPath+"/" : defaultCsvPath;
            System.err.println("Can't get default csv path");
            return null;
        }
        else if ("false".equals(env.getProperty("xml.resources.csvFetchFromDefault"))) {
            String customCsvPath = env.getProperty("xml.resources.csvFilesLocation");
            if (customCsvPath != null)
                return !customCsvPath.endsWith("/") ? customCsvPath+"/" : customCsvPath;
            System.err.println("Can't get custom csv path");
            return null;
        }
        System.err.println("Can't get csv path. Check if xml.resources.csvFetchFromDefault property is present.");
        return null;
    }

    private String constructPathFromRoot(String path) {
        if (path == null) return null;
        String root = System.getProperty("user.dir").replace("\\", "/");
        return !path.startsWith("/") ? (root+"/"+path+(!path.endsWith("/") ? "/" : ""))
                : root+path+(!path.endsWith("/") ? "/" : "");
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
