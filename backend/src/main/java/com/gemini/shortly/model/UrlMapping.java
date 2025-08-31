// File: backend/src/main/java/com/gemini/shortly/model/UrlMapping.java
package com.gemini.shortly.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "url_mappings")
@Data
@NoArgsConstructor
public class UrlMapping {

    @Id
    private String id;

    private String longUrl;
    private String shortCode;
    private LocalDateTime createdAt;
    private String userId;
    private long clickCount; // <-- ADD THIS LINE

    public UrlMapping(String longUrl, String shortCode, String userId) {
        this.longUrl = longUrl;
        this.shortCode = shortCode;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.clickCount = 0; // <-- INITIALIZE TO 0
    }
}