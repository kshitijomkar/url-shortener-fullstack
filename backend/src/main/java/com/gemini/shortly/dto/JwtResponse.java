// File: backend/src/main/java/com/gemini/shortly/dto/JwtResponse.java
package com.gemini.shortly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private final String jwt;
}