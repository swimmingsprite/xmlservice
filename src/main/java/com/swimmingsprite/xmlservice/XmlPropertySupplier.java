package com.swimmingsprite.xmlservice;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface XmlPropertySupplier {
    List<String> getAllXslVariants(String namespace);
    String getXslVariantPath(String namespace, String variant);
    String getXsdPath(String namespace);
    Class<?> getNamespaceClass(String namespace);
}
