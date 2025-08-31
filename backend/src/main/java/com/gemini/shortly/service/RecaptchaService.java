// File: backend/src/main/java/com/gemini/shortly/service/RecaptchaService.java
package com.gemini.shortly.service;

import com.gemini.shortly.dto.RecaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    
    @Value("${recaptcha.secret.key}")
    private String recaptchaSecretKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean validateRecaptcha(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("secret", recaptchaSecretKey);
        body.add("response", token);

        try {
            RecaptchaResponse response = restTemplate.postForObject(RECAPTCHA_VERIFY_URL, body, RecaptchaResponse.class);
            return response != null && response.isSuccess();
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error while validating reCAPTCHA: " + e.getMessage());
            return false;
        }
    }
}