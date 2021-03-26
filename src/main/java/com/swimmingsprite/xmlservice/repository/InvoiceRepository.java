package com.swimmingsprite.xmlservice.repository;

import com.swimmingsprite.xmlservice.entity.invoice.DocumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;

@Repository
public class InvoiceRepository implements GeneralRepository {
    final JdbcTemplate template;

    public InvoiceRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void save(Object obj) {
        DocumentType doc = (DocumentType) obj;
        System.out.println(doc.getBank().getBankName());
        System.out.println(doc.getCustomer().getCompanyName());
        // TODO: 26. 3. 2021 IMPLEMENTATION

    }
}
