package com.swimmingsprite.xmlservice;

import com.swimmingsprite.xmlservice.entity.invoice.DocumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class XmlPropertySupplierImpl implements XmlPropertySupplier {
    private static final String CSV_XSD_PATHS_CLASSES_FILE_NAME = "XSDPathsAndClasses.csv" ;
    private static final String CSV_XSL_PATHS_VARIANTS_FILE_NAME = "XSLPathsAndVariants.csv" ;
    private final Environment env;


    public XmlPropertySupplierImpl(CsvFileParser csvFileParser, Environment env) {
        this.csvFileParser = csvFileParser;
        this.env = env;
    }

    private CsvFileParser csvFileParser;


    private static Map<String, String> namespaceXsdPaths;// = new ConcurrentHashMap<>();
    private static Map<String, Class<?>> namespaceClasses;// = new ConcurrentHashMap<>();
    private static Map<String, Map<String, String>> namespaceVariantsStylesheetsPaths;// = new ConcurrentHashMap<>();


    @EventListener(ApplicationReadyEvent.class)
    public void fetchAllProperties() {
        String csvAbsolutePath = getCsvAbsolutePath();
        if (csvAbsolutePath != null) {
            namespaceXsdPaths = csvFileParser.getXsdPaths(csvAbsolutePath);
            namespaceClasses = csvFileParser.getNamespaceClasses(csvAbsolutePath);
            namespaceVariantsStylesheetsPaths = csvFileParser.getXslVariantsPaths(csvAbsolutePath);
        }

    }

    private String getCsvAbsolutePath() {
        if ("true".equals(env.getProperty("xml.resources.csvFetchFromDefault"))) {
            String defaultCsvPath =
                    constructPathFromRoot(env.getProperty("xml.resources.csvDefaultFilesLocation"));
            if (defaultCsvPath != null)
                return !defaultCsvPath.endsWith("/") ? defaultCsvPath+"/" : defaultCsvPath;
            System.err.println("Can't get default csv path");
        }
        else if ("false".equals(env.getProperty("xml.resources.csvFetchFromDefault"))) {
            String customCsvPath = env.getProperty("xml.resources.csvFilesLocation");
            if (customCsvPath != null)
                return !customCsvPath.endsWith("/") ? customCsvPath+"/" : customCsvPath;
            System.err.println("Can't get default csv path");
        }
        return null;
    }

    private String constructPathFromRoot(String path) {
        if (path == null) return null;
        String root = System.getProperty("user.dir").replace("\\", "/");
        return !path.startsWith("/") ? root+"/"+path : root+path;
    }

    /*
    * namepace xsdPath
    * namespace className
    * namespace variant - stylesheetPath
    * */


    @Override
    public List<String> getAllXslVariants(String namespace) {
        return null;
    }

    @Override
    public String getXslVariantPath(String namespace, String variant) {
        return null;
    }

    @Override
    public String getXsdLocation(String namespace) {
        return null;
    }
}
