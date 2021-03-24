package com.swimmingsprite.xmlservice.validator;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;

public final class BasicValidator implements Validator {
    private final String xsdPath;

    public BasicValidator(String xsdPath) {
        this.xsdPath = xsdPath;
    }

    @Override
    public boolean validate(String xml) {
        try {
            System.out.println("xsd path is: "+xsdPath);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            System.out.println(1);
            Schema schema = schemaFactory.newSchema(new File(xsdPath));
            System.out.println();
            javax.xml.validation.Validator validator = schema.newValidator();
            StreamResult result = new StreamResult(System.out);
            validator.validate(new StreamSource(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))), result);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
