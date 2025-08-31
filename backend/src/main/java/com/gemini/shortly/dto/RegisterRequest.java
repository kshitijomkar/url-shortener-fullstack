// File: backend/src/main/java/com/gemini/shortly/dto/RegisterRequest.java
package com.gemini.shortly.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
}