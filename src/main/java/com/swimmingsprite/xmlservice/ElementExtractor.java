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
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.doc = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            this.root = this.doc.getDocumentElement();
        } catch (Exception e) {
            this.root = null;
        }
    }

    public Element getRootElement() {
        return root;
    }

    public String getNamespace() {
        return root.getAttribute("xmlns");
    }

}
