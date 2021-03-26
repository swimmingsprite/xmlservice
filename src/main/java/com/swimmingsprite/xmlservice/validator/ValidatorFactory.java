package com.swimmingsprite.xmlservice.validator;

import com.swimmingsprite.xmlservice.XmlPropertySupplier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("singleton")
public class ValidatorFactory {
    private static final Map<String, Validator> validators = new ConcurrentHashMap<>();
    private XmlPropertySupplier xmlPropertySupplier;

    public ValidatorFactory(XmlPropertySupplier xmlPropertySupplier) {
        this.xmlPropertySupplier = xmlPropertySupplier;
    }

    /**
     * Returns proper Validator for given namespace or throws NoSuchElementException.
     * Guarantee to not be null.
     * @throws NoSuchElementException - if validator for given namespace can't be found
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
            String xsdPath = xmlPropertySupplier.getXsdPath(namespace);
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
