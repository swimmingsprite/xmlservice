package com.swimmingsprite.xmlservice.validator;

import org.springframework.stereotype.Component;

public interface Validator {
    boolean validate(String xml);
}
