// File: backend/src/main/java/com/gemini/urlshortenerapi/controller/HelloWorldController.java
package com.gemini.shortly.controller;

import org.springframework.web.bind.annotation.CrossOrigin; // Import this
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
//@CrossOrigin("http://localhost:5173") // Allow requests from our React app's origin
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from the Spring Boot Backend! 👋";
    }
}