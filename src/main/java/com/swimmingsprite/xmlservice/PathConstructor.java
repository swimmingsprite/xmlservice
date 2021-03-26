package com.swimmingsprite.xmlservice;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PathConstructor {
    private static final String CSV_fetchFromDefaultEnvString = "xml.resources.csvFetchFromDefault";
    private static final String CSV_defaultFilesRelPathEnvString = "xml.resources.csvDefaultFilesRelativePath";
    private static final String CSV_customFilesLocationEnvString = "xml.resources.csvFilesLocation";

    private static final String XSL_fetchFromDefaultEnvString = "xml.resources.xslFetchFromDefault";
    private static final String XSL_defaultFilesRelPathEnvString = "xml.resources.xslDefaultRelativePath";
    private static final String XSL_customFilesLocationEnvString = "xml.resources.xslLocation";

    private static final String XSD_fetchFromDefaultEnvString = "xml.resources.xsdFetchFromDefault";
    private static final String XSD_defaultFilesRelPathEnvString = "xml.resources.xsdDefaultRelativePath";
    private static final String XSD_customFilesLocationEnvString = "xml.resources.xsdLocation";


    private final Environment env;

    public PathConstructor(Environment env) {
        this.env = env;
    }


    String getXsdPath(String fileName) {
        String xsdDir = getAbsolutePathByEnvProperties(
                XSD_fetchFromDefaultEnvString,
                XSD_defaultFilesRelPathEnvString,
                XSD_customFilesLocationEnvString
        );
        return xsdDir != null ? xsdDir+fileName : null;
    }

    String getXslPath(String fileName) {
        String xslDir = getAbsolutePathByEnvProperties(
                XSL_fetchFromDefaultEnvString,
                XSL_defaultFilesRelPathEnvString,
                XSL_customFilesLocationEnvString
        );
        return xslDir != null ? xslDir+fileName : null;
    }

    String getCsvPath(String fileName) {
        String csvDir = getAbsolutePathByEnvProperties(
                CSV_fetchFromDefaultEnvString,
                CSV_defaultFilesRelPathEnvString,
                CSV_customFilesLocationEnvString
        );
        return csvDir != null ? csvDir+fileName : null;
    }

    private String getAbsolutePathByEnvProperties(
            String fetchFromDefaultEnvString,
            String defaultFilesRelPathEnvString,
            String customFilesLocationEnvString
            ) {
        if ("true".equals(env.getProperty(fetchFromDefaultEnvString))) {
            String defaultCsvPath =
                    constructPathFromRoot(env.getProperty(defaultFilesRelPathEnvString));
            if (defaultCsvPath != null)
                return !defaultCsvPath.endsWith("/") ? defaultCsvPath+"/" : defaultCsvPath;
            System.err.println("Path Constructor: Can't get default path");
            return null;
        }
        else if ("false".equals(env.getProperty(fetchFromDefaultEnvString))) {
            String customCsvPath = env.getProperty(customFilesLocationEnvString);
            if (customCsvPath != null)
                return !customCsvPath.endsWith("/") ? customCsvPath+"/" : customCsvPath;
            System.err.println("Path Constructor: Can't get custom path");
            return null;
        }
        System.err.println("Path Constructor: Can't get path. Check if xml.resources.???Default??? property is present.");
        return null;
    }

    private String constructPathFromRoot(String relPath) {
        if (relPath == null) return null;
        String root = System.getProperty("user.dir").replace("\\", "/");
        return !relPath.startsWith("/") ? (root+"/"+relPath+(!relPath.endsWith("/") ? "/" : ""))
                : root+relPath+(!relPath.endsWith("/") ? "/" : "");
    }



}
