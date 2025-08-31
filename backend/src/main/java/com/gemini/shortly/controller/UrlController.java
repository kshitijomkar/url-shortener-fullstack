// File: backend/src/main/java/com/gemini/shortly/controller/UrlController.java
package com.gemini.shortly.controller;

import com.gemini.shortly.dto.ShortenUrlRequest;
import com.gemini.shortly.model.UrlMapping;
import com.gemini.shortly.model.User;
import com.gemini.shortly.repository.UrlMappingRepository;
import com.gemini.shortly.repository.UserRepository;
import com.gemini.shortly.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final UrlShortenerService urlShortenerService;
    private final UrlMappingRepository urlMappingRepository;
    private final UserRepository userRepository;

    public UrlController(UrlShortenerService urlShortenerService, UrlMappingRepository urlMappingRepository, UserRepository userRepository) {
        this.urlShortenerService = urlShortenerService;
        this.urlMappingRepository = urlMappingRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlMapping> shortenUrl(@RequestBody ShortenUrlRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UrlMapping urlMapping = urlShortenerService.shortenUrl(request.getLongUrl(), currentUser.getId());
        return new ResponseEntity<>(urlMapping, HttpStatus.CREATED);
    }

    @GetMapping("/my-urls")
    public ResponseEntity<List<UrlMapping>> getMyUrls(@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UrlMapping> userUrls = urlMappingRepository.findByUserId(currentUser.getId());
        return ResponseEntity.ok(userUrls);
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortCode, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            urlShortenerService.deleteUrl(shortCode, currentUser.getId());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) { // <-- THIS IS THE FIX
            // This single catch block now handles both "not found" and "not authorized" exceptions.
            // Log the error for debugging purposes on the server
            System.err.println("Delete operation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}