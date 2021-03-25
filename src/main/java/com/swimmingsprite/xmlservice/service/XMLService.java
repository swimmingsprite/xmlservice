package com.swimmingsprite.xmlservice.service;

import com.swimmingsprite.xmlservice.ElementExtractor;
import com.swimmingsprite.xmlservice.Parser;
import com.swimmingsprite.xmlservice.XMLTransformer;
import com.swimmingsprite.xmlservice.entity.invoice.DocumentType;
import com.swimmingsprite.xmlservice.repository.GeneralRepository;
import com.swimmingsprite.xmlservice.repository.InvoiceRepository;
import com.swimmingsprite.xmlservice.validator.Validator;
import com.swimmingsprite.xmlservice.validator.ValidatorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class XMLService {
    private final ValidatorFactory validatorFactory;
    private final Parser parser;
    private final ApplicationContext context;
    private final XMLTransformer xmlTransformer;


    // TODO: 24. 3. 2021 fetch from file
    private final Map<String, Class<?>> namespaceClasses = new ConcurrentHashMap<>(
            Map.of("http://www.example.com/Invoice", DocumentType.class));

    public XMLService(ValidatorFactory validatorFactory, Parser parser, ApplicationContext context, XMLTransformer transformer) {
        this.validatorFactory = validatorFactory;
        this.parser = parser;
        this.context = context;
        this.xmlTransformer = transformer;
    }

    public void save(String xml) {
        ElementExtractor elementExtractor = new ElementExtractor(xml);
        String namespace = elementExtractor.getNamespace();
        if (namespace == null) throw new NoSuchElementException("Namespace not present.");
        Class<?> type = namespaceClasses.get(namespace);
        if (type == null) throw new RuntimeException("There's no proper pojo class to map for "+namespace);
        Validator validator = validatorFactory.getInstance(namespace);
        if (validator.validate(xml)) {
//            Object object = parser.parse(xml, type);
            Object object = parser.parse(elementExtractor.getDocument(), type);
            getRepository(type).save(object);
            return;
        }
        System.err.println("validation failed...");
        throw new RuntimeException(String.format("XML with namespace %s is not valid.", namespace));
    }

    public String transformToHTML(String xml) {
        ElementExtractor elementExtractor = new ElementExtractor(xml);
        String namespace = elementExtractor.getNamespace();
        if (namespace == null) throw new NoSuchElementException("Namespace not present.");
        Class<?> type = namespaceClasses.get(namespace);
        if (type == null) throw new RuntimeException("There's no proper pojo class to map for "+namespace);
        Validator validator = validatorFactory.getInstance(namespace);
        if (validator.validate(xml)) {
            return xmlTransformer.transform(xml, stylesheet);
        }
        System.err.println("validation failed...");
        throw new RuntimeException(String.format("XML with namespace %s is not valid.", namespace));
    }

    private GeneralRepository getRepository(Class<?> clazz) {
        if (clazz.equals(DocumentType.class)) return context.getBean(InvoiceRepository.class);
        throw new RuntimeException("Failed to get proper repository.");
    }

}
