package com.swimmingsprite.xmlservice;

import com.swimmingsprite.xmlservice.entity.invoice.DocumentType;
import com.swimmingsprite.xmlservice.entity.invoice.ObjectFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

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
//            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            T object = (T) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return object;
        }
        catch (JAXBException e) {
            throw new RuntimeException("Error during xml parsing.", e);
        }
    }

    public <T> T parse(Node node, Class<?> clazz) {

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(clazz);
//            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            T object = (T) jaxbUnmarshaller.unmarshal(node);
            return object;
        }
        catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during xml parsing.", e);
        }
    }
}
