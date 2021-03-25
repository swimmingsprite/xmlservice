package com.swimmingsprite.xmlservice;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CsvFileParserImpl implements CsvFileParser {


    @Override
    public Map<String, Map<String, String>> getXslVariantsPaths(String csvAbsolutePath) {
        return null;
    }

    @Override
    public Map<String, String> getXsdPaths(String csvAbsolutePath) {
        return null;
    }

    @Override
    public Map<String, Class<?>> getNamespaceClasses(String csvAbsolutePath) {
        return null;
    }
}
