// File: backend/src/main/java/com/gemini/shortly/dto/ShortenUrlRequest.java
package com.gemini.shortly.dto;
import lombok.Data;

@Data
public class ShortenUrlRequest {
    private String longUrl;
    private String recaptchaToken; // <-- ADD THIS
}