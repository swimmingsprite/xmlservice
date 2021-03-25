package com.swimmingsprite.xmlservice.controller;

import com.swimmingsprite.xmlservice.service.XMLService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class Controller {
    XMLService service;

    public Controller(XMLService service) {
        this.service = service;
    }

    @PostMapping(value = "/save", consumes = "application/xml")
    public ResponseEntity<?> save(@RequestBody String xml) {
        try {
            service.save(xml);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            printException(e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping(value = "/transformToHtml", consumes = "application/xml", produces = "text/html")
    public ResponseEntity<String> transformToHtml(@RequestBody String xml) {
        try {
            String html = service.transformToHTML(xml);
            return new ResponseEntity<>(html, HttpStatus.OK);
        }
        catch (Exception e) {
            printException(e);
            return ResponseEntity.status(500).build();
        }
    }

    private void printException(Exception e) {
        System.err.println("Message: "+e.getMessage()+"\n"+"Cause: "+e.getCause());
    }


}
