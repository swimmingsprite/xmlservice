package com.swimmingsprite.xmlservice.controller;

import com.swimmingsprite.xmlservice.service.XMLService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

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
        } catch (Exception e) {
            printException(e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping(value = "/validate", consumes = "application/xml")
    public ResponseEntity<?> validate(@RequestBody String xml) {
        try {
            if (service.validate(xml)) return ResponseEntity.ok().build();
            else {return ResponseEntity.badRequest().build();}
        } catch (Exception e) {
            printException(e);
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping(value = "/toJSON", consumes = "application/xml", produces = "application/json")
    public ResponseEntity<?> toJSON(@RequestBody String xml) {
        try {
            Object object = service.toJSON(xml);
            return new ResponseEntity<>(object, HttpStatus.OK);
        } catch (Exception e) {
            printException(e);
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }



    @PostMapping(value = "/transformToHtml", consumes = "application/xml", produces = "text/html")
    public ResponseEntity<String> transformToHtml(@RequestBody String xml,
                                                  @RequestParam("variant") String variant) {
        try {
            String html = service.transformToHtml(xml, variant);
            return new ResponseEntity<>(html, HttpStatus.OK);
        } catch (Exception e) {
            printException(e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping(value = "/transformToHtmlAndSend", consumes = "application/xml", produces = "text/html")
    public ResponseEntity<String> transformToHtmlAndSend(
            @RequestBody String xml,
            @RequestParam("variant") String variant,
            @Email @RequestParam("email") String email) {
        try {
            service.transformToHtmlAndSend(xml, variant, email);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            printException(e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping(value = "/transformToPdfAndSend", consumes = "application/xml", produces = "text/html")
    public ResponseEntity<String> transformToPdfAndSend(
            @RequestBody String xml,
            @RequestParam("variant") String variant,
            @Email @RequestParam("email") String email) {
        try {
            service.transformToPdfAndSend(xml, variant, email);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            printException(e);
            return ResponseEntity.status(500).build();
        }
    }

    private void printException(Exception e) {
        System.err.println("Message: " + e.getMessage() + "\n" + "Cause: " + e.getCause());
    }


}
