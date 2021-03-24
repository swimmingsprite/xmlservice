package com.swimmingsprite.xmlservice.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("singleton")
public class ValidatorFactory {
    @Value("${xml.resources.path}")
    private String xmlStorage;

    private static final Map<String, Validator> validators = new ConcurrentHashMap<>();

    // TODO: 24. 3. 2021 fetch from file
    private static final Map<String, String> xsdPaths = new ConcurrentHashMap<>();


    @EventListener(ApplicationReadyEvent.class)
    public void putXSDPaths() {
        xsdPaths.put("http://www.example.com/Invoice", xmlStorage+"Invoice.xsd");
    }

    /**
     * Guarantee to not be null.
    * */
    public Validator getInstance(String docType) {
        Validator validator = getByType(docType);
        if (validator != null) return validator;
        throw new NoSuchElementException(String.format("Validator for type %s can't be found.", docType));
    }

    private Validator getByType(String docType) {
        Validator validator = validators.get(docType);
        //check if validator with this type is in cache map
        if (validator == null) {
            //if not, check if it's valid type (xsd for docType exist) and create new Validator
            System.out.println("doc: "+docType);
            System.out.println("docToLowercase: "+docType.toLowerCase(Locale.ROOT));
            String xsdPath = xsdPaths.get(docType);
            System.out.println("xsd path: "+xsdPath);
            if (xsdPath != null) {
                Validator newValidator = new BasicValidator(xsdPath);
                validators.put(docType, newValidator);
                return newValidator;
            }
            return null;
        }
        return validator;
    }
}
