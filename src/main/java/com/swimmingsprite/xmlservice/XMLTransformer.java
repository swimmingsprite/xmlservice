package com.swimmingsprite.xmlservice;

import org.springframework.stereotype.Component;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

@Component
public class XMLTransformer {

    public String transform(String xml, File stylesheet) {
        StreamSource stylesource = new StreamSource(stylesheet);
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer(stylesource);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(baos);
            transformer.transform(new StreamSource(
                    new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))),
                    result);
            return baos.toString(StandardCharsets.UTF_8);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create transformer.", e);
        } catch (TransformerException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during transformation.", e);
        }


    }
}
