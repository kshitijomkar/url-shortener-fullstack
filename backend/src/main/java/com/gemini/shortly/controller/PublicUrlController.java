// File: backend/src/main/java/com/gemini/shortly/controller/PublicUrlController.java
package com.gemini.shortly.controller;

import com.gemini.shortly.dto.ShortenUrlRequest;
import com.gemini.shortly.exception.RateLimitException;
import com.gemini.shortly.model.UrlMapping;
import com.gemini.shortly.service.RecaptchaService;
import com.gemini.shortly.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class PublicUrlController {

    private final UrlShortenerService urlShortenerService;
    private final RecaptchaService recaptchaService;

    public PublicUrlController(UrlShortenerService urlShortenerService, RecaptchaService recaptchaService) {
        this.urlShortenerService = urlShortenerService;
        this.recaptchaService = recaptchaService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrlPublic(@RequestBody ShortenUrlRequest request, HttpServletRequest httpServletRequest) {
        boolean isRecaptchaValid = recaptchaService.validateRecaptcha(request.getRecaptchaToken());
        if (!isRecaptchaValid) {
            return new ResponseEntity<>("reCAPTCHA validation failed", HttpStatus.BAD_REQUEST);
        }

        String ipAddress = getClientIp(httpServletRequest);

        try {
            UrlMapping urlMapping = urlShortenerService.shortenUrlAnonymously(request.getLongUrl(), ipAddress);
            return new ResponseEntity<>(urlMapping, HttpStatus.CREATED);
        } catch (RateLimitException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
        }
    }

    // --- THIS IS THE NEW METHOD ---
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        // Just return a simple "200 OK" response with the text "UP"
        return ResponseEntity.ok("UP");
    }
    // ----------------------------

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(",")) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}