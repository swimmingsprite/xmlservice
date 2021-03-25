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


    @EventListener(ApplicationReadyEvent.class) // TODO: 25. 3. 2021 fetch paths from file
    public void putXSDPaths() {
        xsdPaths.put("http://www.example.com/Invoice", xmlStorage+"Invoice.xsd");
    }

    /**
     * Returns proper Validator for given namespace or throws NoSuchElementException.
     * Guarantee to not be null.
     * @throws NoSuchElementException
     * @param namespace - unique xmlns namespace
    * */
    public Validator getInstance(String namespace) {
        Validator validator = getByType(namespace);
        if (validator != null) return validator;
        throw new NoSuchElementException(String.format("Validator for type %s can't be found.", namespace));
    }

    private Validator getByType(String namespace) {
        Validator validator = validators.get(namespace);
        //check if validator with this type is in cache map
        if (validator == null) {
            //if not, check if it's valid type (xsd for namespace exist) and create new Validator
            String xsdPath = xsdPaths.get(namespace); // TODO: 25. 3. 2021 fetch from other bean
            if (xsdPath != null) {
                Validator newValidator = new BasicValidator(xsdPath);
                validators.put(namespace, newValidator);
                return newValidator;
            }
            return null;
        }
        return validator;
    }
}
