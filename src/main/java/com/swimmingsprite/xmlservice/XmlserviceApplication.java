package com.swimmingsprite.xmlservice;

import com.sun.tools.javac.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class XmlserviceApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(XmlserviceApplication.class, args);
    }

}
