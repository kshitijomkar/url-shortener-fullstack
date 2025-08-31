// File: backend/src/main/java/com/gemini/shortly/dto/LoginRequest.java
package com.gemini.shortly.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}