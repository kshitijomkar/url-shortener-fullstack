// File: backend/src/main/java/com/gemini/shortly/repository/AnonymousUsageRepository.java
package com.gemini.shortly.repository;

import com.gemini.shortly.model.AnonymousUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface AnonymousUsageRepository extends MongoRepository<AnonymousUsage, String> {
    Optional<AnonymousUsage> findByIpAddress(String ipAddress);
}