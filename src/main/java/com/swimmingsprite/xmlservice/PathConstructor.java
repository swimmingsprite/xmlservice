package com.swimmingsprite.xmlservice;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PathConstructor {
    private final Environment env;

    public PathConstructor(Environment env) {
        this.env = env;
    }


    String getXsdPath(String fileName) {

    }

    String getXslPath(String fileName) {
        if ("true".equals(env.getProperty("xml.resources.csvFetchFromDefault"))) {
            String defaultCsvPath =
                    constructPathFromRoot(env.getProperty("xml.resources.csvDefaultFilesRelativePath"));
            if (defaultCsvPath != null)
                return !defaultCsvPath.endsWith("/") ? defaultCsvPath+"/" : defaultCsvPath;
            System.err.println("Path Constructor: Can't get default csv path");
            return null;
        }
        else if ("false".equals(env.getProperty("xml.resources.csvFetchFromDefault"))) {
            String customCsvPath = env.getProperty("xml.resources.csvFilesLocation");
            if (customCsvPath != null)
                return !customCsvPath.endsWith("/") ? customCsvPath+"/" : customCsvPath;
            System.err.println("Path Constructor: Can't get custom csv path");
            return null;
        }
        System.err.println("Path Constructor: Can't get csv path. Check if xml.resources.csvFetchFromDefault property is present.");
        return null;

    }

    String getCsvPath(String fileName) {
        String csvDir = getCsvAbsolutePath();
        return csvDir != null ? csvDir+fileName : null;
    }

    private String getCsvAbsolutePath() {
        if ("true".equals(env.getProperty("xml.resources.csvFetchFromDefault"))) {
            String defaultCsvPath =
                    constructPathFromRoot(env.getProperty("xml.resources.csvDefaultFilesRelativePath"));
            if (defaultCsvPath != null)
                return !defaultCsvPath.endsWith("/") ? defaultCsvPath+"/" : defaultCsvPath;
            System.err.println("Path Constructor: Can't get default csv path");
            return null;
        }
        else if ("false".equals(env.getProperty("xml.resources.csvFetchFromDefault"))) {
            String customCsvPath = env.getProperty("xml.resources.csvFilesLocation");
            if (customCsvPath != null)
                return !customCsvPath.endsWith("/") ? customCsvPath+"/" : customCsvPath;
            System.err.println("Path Constructor: Can't get custom csv path");
            return null;
        }
        System.err.println("Path Constructor: Can't get csv path. Check if xml.resources.csvFetchFromDefault property is present.");
        return null;
    }

    private String constructPathFromRoot(String relPath) {
        if (relPath == null) return null;
        String root = System.getProperty("user.dir").replace("\\", "/");
        return !relPath.startsWith("/") ? (root+"/"+relPath+(!relPath.endsWith("/") ? "/" : ""))
                : root+relPath+(!relPath.endsWith("/") ? "/" : "");
    }



}
