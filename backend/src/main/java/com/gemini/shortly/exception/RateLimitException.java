// File: backend/src/main/java/com/gemini/shortly/exception/RateLimitException.java
package com.gemini.shortly.exception;

public class RateLimitException extends RuntimeException {
    public RateLimitException(String message) {
        super(message);
    }
}