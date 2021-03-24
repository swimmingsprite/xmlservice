package com.swimmingsprite.xmlservice.validator;

import org.springframework.stereotype.Component;

@Component
public interface Validator {
    boolean validate(String xml);
}
