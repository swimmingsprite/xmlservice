package com.swimmingsprite.xmlservice.repository;

import com.swimmingsprite.xmlservice.entity.invoice.DocumentType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InvoiceRepository implements GeneralRepository {
    final JdbcTemplate template;

    public InvoiceRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void save(Object obj) {

    }
}
