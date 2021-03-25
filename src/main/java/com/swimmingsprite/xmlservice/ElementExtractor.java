package com.swimmingsprite.xmlservice;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ElementExtractor {
    private Element root;
    private Document doc;

    public ElementExtractor(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            root = doc.getDocumentElement();
        } catch (Exception e) {
            root = null;
        }
    }

    public Document getDocument() {
        return doc;
    }


    public String getNamespace() {
        return root != null ? root.getAttribute("xmlns") : null;
    }

}
