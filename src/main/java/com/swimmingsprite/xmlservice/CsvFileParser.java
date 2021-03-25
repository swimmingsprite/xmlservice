package com.swimmingsprite.xmlservice;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface CsvFileParser {
    Map<String, Map<String, String>> getXslVariantsPaths(String csvAbsolutePath);
    Map<String, String> getXsdPaths(String csvAbsolutePath);
    Map<String, Class<?>> getNamespaceClasses(String csvAbsolutePath);
}
