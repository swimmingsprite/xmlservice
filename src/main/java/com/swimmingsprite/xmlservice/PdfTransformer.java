package com.swimmingsprite.xmlservice;

import org.springframework.stereotype.Component;
import com.itextpdf.html2pdf.HtmlConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
public class PdfTransformer {

    public byte[] convert(String html) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            HtmlConverter.convertToPdf(new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)),
                    output);
            return output.toByteArray();
        } catch (IOException e) {
            System.err.println("Error during html to pdf conversion.");
            e.printStackTrace();
            return null;
        }
    }

}
