// File: backend/src/main/java/com/gemini/shortly/model/AnonymousUsage.java
package com.gemini.shortly.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "anonymous_usage")
@Data
public class AnonymousUsage {
    @Id
    private String id;

    @Indexed(unique = true)
    private String ipAddress;
    private int urlCount;

    public AnonymousUsage(String ipAddress) {
        this.ipAddress = ipAddress;
        this.urlCount = 1;
    }
}