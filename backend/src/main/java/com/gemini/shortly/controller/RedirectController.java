// File: backend/src/main/java/com/gemini/shortly/controller/RedirectController.java
package com.gemini.shortly.controller;

import com.gemini.shortly.model.UrlMapping;
import com.gemini.shortly.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.Optional;

@RestController
public class RedirectController {

    private final UrlShortenerService urlShortenerService;

    public RedirectController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortCode) {
        Optional<UrlMapping> urlMappingOptional = urlShortenerService.getLongUrl(shortCode);

        if (urlMappingOptional.isPresent()) {
            String longUrl = urlMappingOptional.get().getLongUrl();
            // Return a 302 Found redirect response
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(longUrl))
                    .build();
        } else {
            // If the short code is not found, return a 404 Not Found response
            return ResponseEntity.notFound().build();
        }
    }
}