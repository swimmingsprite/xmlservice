package com.swimmingsprite.xmlservice;

import com.swimmingsprite.xmlservice.entity.invoice.DocumentType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class XmlPropertySupplierImpl implements XmlPropertySupplier {
    private CsvFileParser csvFileParser;

    private static Map<String, String> namespaceXsdPaths = new ConcurrentHashMap<>();
    private static Map<String, Class<?>> namespaceClasses = new ConcurrentHashMap<>();
    private static Map<String, Map<String, String>> namespaceVariantsStylesheetsPaths = new ConcurrentHashMap<>();

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
