package com.swimmingsprite.xmlservice.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ValidatorFactory {
    @Value("${xml.resources.path}")
    private static String xmlStorage;

    private static Map<String, Validator> validators = new ConcurrentHashMap<>();
    private static Map<String, String> xsdPaths = new ConcurrentHashMap<>(
            Map.of("invoice", xmlStorage+"Invoice.xsd"));


    public static Validator newInstance(String docType) {
        Validator validator = getByType(docType);
        if (validator != null) return validator;
        throw new NoSuchElementException(String.format("Validator for type %s can't be found.", docType));
    }

    private static Validator getByType(String docType) {
        Validator validator = validators.get(docType);
        //check if validator with this type is in cache map
        if (validator == null) {
            //if not, check if is valid type (xsd for this type exist) and create new Validator
            String xsdPath = xsdPaths.get(docType.toLowerCase(Locale.ROOT));
            if (xsdPath != null) {
                Validator newValidator = new BasicValidator(xsdPath);
                validators.put(docType.toLowerCase(Locale.ROOT), newValidator);
                return newValidator;
            }
            return null;
        }
        return validator;
    }
}
