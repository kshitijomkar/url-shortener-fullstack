// File: backend/src/main/java/com/gemini/shortly/repository/UrlMappingRepository.java
package com.gemini.shortly.repository;

import com.gemini.shortly.model.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List; // <-- Import List
import java.util.Optional;

public interface UrlMappingRepository extends MongoRepository<UrlMapping, String> {

    Optional<UrlMapping> findByShortCode(String shortCode);

    List<UrlMapping> findByUserId(String userId); // <-- ADD THIS METHOD
}