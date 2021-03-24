package com.swimmingsprite.xmlservice.service;

import com.swimmingsprite.xmlservice.ElementExtractor;
import com.swimmingsprite.xmlservice.repository.XMLRepository;
import com.swimmingsprite.xmlservice.validator.Validator;
import com.swimmingsprite.xmlservice.validator.ValidatorFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class XMLService {
    ValidatorFactory validatorFactory;
    XMLRepository repository;

    private Map<String, Class<?>>

    public XMLService(ValidatorFactory validatorFactory, XMLRepository repository) {
        this.validatorFactory = validatorFactory;
        this.repository = repository;
    }

    public void save(String xml) {
        String namespace = new ElementExtractor(xml).getNamespace();
        if (namespace == null) throw new NoSuchElementException("Namespace not present.");
        Validator validator = validatorFactory.getInstance(namespace);
        if (validator.validate(xml)) {
            //parse
            repository.save();
        }
    }

}
