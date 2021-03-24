package com.swimmingsprite.xmlservice.validator;

import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ValidatorFactory {

    public static Validator newInstance(String docType) {
        Validator validator = getByType(docType);
        if (validator != null) return validator;
        throw new NoSuchElementException(String.format("Validator for type %s does not exist.", docType));
    }

    private static Validator getByType(String docType) {
        // TODO: 24. 3. 2021 impl
        return null;
    }
}
