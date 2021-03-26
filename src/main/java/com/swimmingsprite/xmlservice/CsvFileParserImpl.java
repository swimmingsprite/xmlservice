package com.swimmingsprite.xmlservice;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class CsvFileParserImpl implements CsvFileParser {
    private PathConstructor pathConstructor;

    public CsvFileParserImpl(PathConstructor pathConstructor) {
        this.pathConstructor = pathConstructor;
    }

    @Override
    public Map<String, Map<String, String>> getXslVariantsPaths(String csvAbsolutePath) {
        if (csvAbsolutePath == null) {
            System.err.println("Csv path can't be null. Returning empty map.");
            return Collections.emptyMap();}
        Map<String, Map<String, String>> map = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvAbsolutePath))) {
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                String namespace = lineInArray[0];
                String variant = lineInArray[1];
                String fileName = lineInArray[2];
                if (namespace == null || variant == null || fileName == null) continue;
                Map<String, String> innerMap = map.get(namespace);
                if (innerMap == null) {
                    map.put(namespace, new HashMap<>(Map.of(variant, pathConstructor.getXslPath(fileName))));
                } else {
                    innerMap.putIfAbsent(variant, pathConstructor.getXslPath(fileName));
                }
            }
            return map;
        } catch (FileNotFoundException e) {
            System.err.printf("File with path %s not found", csvAbsolutePath);
            e.printStackTrace();
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error while reading CSV file with absolute path: " + csvAbsolutePath);
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, String> getXsdPaths(String csvAbsolutePath) {
        Map<String, String> map = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvAbsolutePath))) {
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                String namespace = lineInArray[0];
                String fileName = lineInArray[1];
                if (namespace == null || fileName == null) continue;
                map.putIfAbsent(namespace, pathConstructor.getXsdPath(fileName));
            }
            return map;
        } catch (FileNotFoundException e) {
            System.err.printf("File with path %s not found", csvAbsolutePath);
            e.printStackTrace();
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error while reading CSV file with absolute path: " + csvAbsolutePath);
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Class<?>> getNamespaceClasses(String csvAbsolutePath) {
        Map<String, Class<?>> map = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvAbsolutePath))) {
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                String namespace = lineInArray[0];
                String className = lineInArray[1];
                if (namespace == null || className == null) continue;
                Class<?> clazz;
                if (map.get(namespace) == null) {
                    try {
                        clazz = Class.forName(className);
                        map.put(namespace, clazz);

                    } catch (ClassNotFoundException e) {
                        System.err.println("Can't load class with name: " + className);
                    }
                }
            }
            return map;
        } catch (FileNotFoundException e) {
            System.err.printf("File with path %s not found", csvAbsolutePath);
            e.printStackTrace();
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error while reading CSV file with absolute path: " + csvAbsolutePath);
            e.printStackTrace();
        }
        return map;
    }
}
