package com.swimmingsprite.xmlservice;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Component
public class Parser {
    public <T> T parse(String xml, Class<T> clazz) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            T object = (T) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return object;
        }
        catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during xml parsing.");
        }

    }
}
