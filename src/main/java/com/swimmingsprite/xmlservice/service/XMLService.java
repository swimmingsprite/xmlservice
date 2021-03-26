package com.swimmingsprite.xmlservice.service;

import com.swimmingsprite.xmlservice.ElementExtractor;
import com.swimmingsprite.xmlservice.Parser;
import com.swimmingsprite.xmlservice.XMLTransformer;
import com.swimmingsprite.xmlservice.XmlPropertySupplier;
import com.swimmingsprite.xmlservice.entity.invoice.DocumentType;
import com.swimmingsprite.xmlservice.repository.GeneralRepository;
import com.swimmingsprite.xmlservice.repository.InvoiceRepository;
import com.swimmingsprite.xmlservice.validator.Validator;
import com.swimmingsprite.xmlservice.validator.ValidatorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.NoSuchElementException;

@Service
public class XMLService {
    private final ValidatorFactory validatorFactory;
    private final Parser parser;
    private final ApplicationContext context;
    private final XMLTransformer xmlTransformer;
    private final XmlPropertySupplier xmlPropertySupplier;
    private final MailService mailService;


    public XMLService(ValidatorFactory validatorFactory, Parser parser, ApplicationContext context, XMLTransformer transformer, XmlPropertySupplier xmlPropertySupplier, MailService mailService) {
        this.validatorFactory = validatorFactory;
        this.parser = parser;
        this.context = context;
        this.xmlTransformer = transformer;
        this.xmlPropertySupplier = xmlPropertySupplier;
        this.mailService = mailService;
    }

    // TODO: 26. 3. 2021 Refactor repetitive code

    public void save(String xml) {
        ElementExtractor elementExtractor = new ElementExtractor(xml);
        String namespace = elementExtractor.getNamespace();
        if (namespace == null) throw new NoSuchElementException("Namespace not present.");
        Class<?> type = xmlPropertySupplier.getNamespaceClass(namespace);
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

    public String transformToHTML(String xml, String variant) {
        ElementExtractor elementExtractor = new ElementExtractor(xml);
        String namespace = elementExtractor.getNamespace();
        if (namespace == null) throw new NoSuchElementException("Namespace not present.");

        Validator validator = validatorFactory.getInstance(namespace);
        if (validator.validate(xml)) {
            return xmlTransformer.transform(xml, new File(xmlPropertySupplier.getXslVariantPath(namespace, variant)));
        }
        System.err.println("validation failed...");
        throw new RuntimeException(String.format("XML with namespace %s is not valid.", namespace));
    }

    private GeneralRepository getRepository(Class<?> clazz) {
        if (clazz.equals(DocumentType.class)) return context.getBean(InvoiceRepository.class);
        throw new RuntimeException("Failed to get proper repository.");
    }

    public void transformToHTMLAndSend(String xml, String variant, String email) {
        ElementExtractor elementExtractor = new ElementExtractor(xml);
        String namespace = elementExtractor.getNamespace();
        if (namespace == null) throw new NoSuchElementException("Namespace not present.");

        Validator validator = validatorFactory.getInstance(namespace);
        if (validator.validate(xml)) {
            String html = xmlTransformer.transform(xml, new File(xmlPropertySupplier.getXslVariantPath(namespace, variant)));
            mailService.sendHtml(email, html, "Your transformed HTML");
            return;
        }
        System.err.println("validation failed...");
        throw new RuntimeException(String.format("XML with namespace %s is not valid.", namespace));
    }

    public boolean validate(String xml) {
        ElementExtractor elementExtractor = new ElementExtractor(xml);
        String namespace = elementExtractor.getNamespace();
        if (namespace == null) throw new NoSuchElementException("Namespace not present.");
        Class<?> type = xmlPropertySupplier.getNamespaceClass(namespace);
        if (type == null) throw new RuntimeException("There's no proper pojo class to map for "+namespace);
        Validator validator = validatorFactory.getInstance(namespace);
        if (validator.validate(xml)) {
            return true;
        }
        System.err.println("validation failed...");
        return false;

    }

    public Object toJSON(String xml) {
        ElementExtractor elementExtractor = new ElementExtractor(xml);
        String namespace = elementExtractor.getNamespace();
        if (namespace == null) throw new NoSuchElementException("Namespace not present.");
        Class<?> type = xmlPropertySupplier.getNamespaceClass(namespace);
        if (type == null) throw new RuntimeException("There's no proper pojo class to map for "+namespace);
        Validator validator = validatorFactory.getInstance(namespace);
        if (validator.validate(xml)) {
            return parser.parse(elementExtractor.getDocument(), type);
        }
        System.err.println("validation failed...");
        throw new RuntimeException(String.format("XML with namespace %s is not valid.", namespace));
    }
}
