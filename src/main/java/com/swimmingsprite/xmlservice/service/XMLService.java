package com.swimmingsprite.xmlservice.service;

import com.swimmingsprite.xmlservice.*;
import com.swimmingsprite.xmlservice.entity.invoice.DocumentType;
import com.swimmingsprite.xmlservice.repository.GeneralRepository;
import com.swimmingsprite.xmlservice.repository.InvoiceRepository;
import com.swimmingsprite.xmlservice.validator.Validator;
import com.swimmingsprite.xmlservice.validator.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class XMLService {
    private final ValidatorFactory validatorFactory;
    private final Parser parser;
    private final ApplicationContext context;
    private final XMLTransformer xmlTransformer;
    private final XmlPropertySupplier xmlPropertySupplier;
    private final MailService mailService;

    @Autowired
    PdfTransformer pdfTransformer;


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
        /*VOID 2E*/
        ElementExtractor elementExtractor = new ElementExtractor(xml);
        String namespace = elementExtractor.getNamespace();
        if (namespace == null) throw new NoSuchElementException("Namespace not present.");
        Class<?> type = xmlPropertySupplier.getNamespaceClass(namespace);
        if (type == null) throw new RuntimeException("There's no proper pojo class to map for "+namespace);
        Validator validator = validatorFactory.getInstance(namespace);
        if (validator.validate(xml)) {
            Object object = parser.parse(elementExtractor.getDocument(), type);
            getRepository(type).save(object);
            return;
        }
        System.err.println("validation failed...");
        throw new RuntimeException(String.format("XML with namespace %s is not valid.", namespace));
    }

    public String transformToHTML(String xml, String variant) {
        /*STRING 1*/
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
        /*VOID 1*/
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

    public void transformToPdfAndSend(String xml, String variant, String email) {
        /*VOID 1*/
        ElementExtractor elementExtractor = new ElementExtractor(xml);
        String namespace = elementExtractor.getNamespace();
        if (namespace == null) throw new NoSuchElementException("Namespace not present.");

        Validator validator = validatorFactory.getInstance(namespace);
        if (validator.validate(xml)) {
            String html = xmlTransformer.transform(xml, new File(xmlPropertySupplier.getXslVariantPath(namespace, variant)));
//            mailService.sendHtml(email, html, "Your transformed HTML");
            byte[] pdf = pdfTransformer.convert(html);
            mailService.sendPdf(email, pdf, "Your transformed HTML");
            return;
        }
        System.err.println("validation failed...");
        throw new RuntimeException(String.format("XML with namespace %s is not valid.", namespace));
    }

    public boolean validate(String xml) {
        /*BOOLEAN 2N*/
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
        return validateAndDoUsingClassObj(xml,
                (type, elementExtractor) -> parser.parse(elementExtractor.getDocument(), type) );
    }

    public <V> V validateAndDoUsingClassObj(String xml, BiFunction<Class<?>,ElementExtractor,V> biFunction) {
        ElementExtractor elementExtractor = new ElementExtractor(xml);
        String namespace = elementExtractor.getNamespace();
        if (namespace == null) throw new NoSuchElementException("Namespace not present.");
        Class<?> type = xmlPropertySupplier.getNamespaceClass(namespace);
        if (type == null) throw new RuntimeException("There's no proper pojo class to map for "+namespace);
        Validator validator = validatorFactory.getInstance(namespace);
        if (validator.validate(xml)) {
            return biFunction.apply(type, elementExtractor);
        }
        System.err.println("validation failed...");
        throw new RuntimeException(String.format("XML with namespace %s is not valid.", namespace));
    }

    public <V> V validateAndDo(String xml, String namespace, BiFunction<String, String, V> biFunction) {

    }
}
