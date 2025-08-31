// File: backend/src/main/java/com/gemini/shortly/repository/UserRepository.java
package com.gemini.shortly.repository;

import com.gemini.shortly.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}