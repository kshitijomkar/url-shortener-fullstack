// File: backend/src/main/java/com/gemini/shortly/dto/RecaptchaResponse.java
package com.gemini.shortly.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecaptchaResponse {
    private boolean success;
    @JsonProperty("error-codes")
    private String[] errorCodes;
}