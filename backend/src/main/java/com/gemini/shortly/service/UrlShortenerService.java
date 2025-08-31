// File: backend/src/main/java/com/gemini/shortly/service/UrlShortenerService.java
package com.gemini.shortly.service;

import com.gemini.shortly.exception.RateLimitException;
import com.gemini.shortly.model.AnonymousUsage;
import com.gemini.shortly.model.UrlMapping;
import com.gemini.shortly.repository.AnonymousUsageRepository; // <-- THE MISSING IMPORT
import com.gemini.shortly.repository.UrlMappingRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShortenerService {

    private final UrlMappingRepository urlMappingRepository;
    private final AnonymousUsageRepository anonymousUsageRepository;
    private static final int ANONYMOUS_URL_LIMIT = 3;

    public UrlShortenerService(UrlMappingRepository urlMappingRepository, AnonymousUsageRepository anonymousUsageRepository) {
        this.urlMappingRepository = urlMappingRepository;
        this.anonymousUsageRepository = anonymousUsageRepository;
    }

    public UrlMapping shortenUrl(String longUrl, String userId) {
        // (This is the method for logged-in users, no changes needed here for now)
        String sanitizedUrl = longUrl.trim();
        if (!sanitizedUrl.matches("^(https?)://.*$")) {
            sanitizedUrl = "https://" + sanitizedUrl;
        }

        String shortCode;
        do {
            shortCode = RandomStringUtils.randomAlphanumeric(7);
        } while (urlMappingRepository.findByShortCode(shortCode).isPresent());

        UrlMapping urlMapping = new UrlMapping(sanitizedUrl, shortCode, userId);
        return urlMappingRepository.save(urlMapping);
    }
    
    public UrlMapping shortenUrlAnonymously(String longUrl, String ipAddress) {
        AnonymousUsage usage = anonymousUsageRepository.findByIpAddress(ipAddress)
                .orElse(new AnonymousUsage(ipAddress));

        if (usage.getId() != null && usage.getUrlCount() >= ANONYMOUS_URL_LIMIT) {
            throw new RateLimitException("IP address has reached the limit of " + ANONYMOUS_URL_LIMIT + " free URLs.");
        }

        // Increment count and save
        usage.setUrlCount(usage.getUrlCount() + 1);
        if (usage.getId() == null) { // If it's a new record, the count is initialized to 1 already
            usage.setUrlCount(1);
        }
        anonymousUsageRepository.save(usage);
        
        String sanitizedUrl = longUrl.trim();
        if (!sanitizedUrl.matches("^(https?)://.*$")) {
            sanitizedUrl = "https://" + sanitizedUrl;
        }

        String shortCode;
        do {
            shortCode = RandomStringUtils.randomAlphanumeric(7);
        } while (urlMappingRepository.findByShortCode(shortCode).isPresent());

        UrlMapping urlMapping = new UrlMapping(sanitizedUrl, shortCode, null);
        return urlMappingRepository.save(urlMapping);
    }


    public Optional<UrlMapping> getLongUrl(String shortCode) {
        Optional<UrlMapping> urlMappingOptional = urlMappingRepository.findByShortCode(shortCode);
        if (urlMappingOptional.isPresent()) {
            UrlMapping urlMapping = urlMappingOptional.get();
            urlMapping.setClickCount(urlMapping.getClickCount() + 1);
            urlMappingRepository.save(urlMapping);
        }
        return urlMappingOptional;
    }

    public void deleteUrl(String shortCode, String userId) {
        UrlMapping urlMapping = urlMappingRepository.findByShortCode(shortCode)
            .orElseThrow(() -> new RuntimeException("URL not found with short code: " + shortCode));

        if (!urlMapping.getUserId().equals(userId)) {
            throw new SecurityException("User is not authorized to delete this URL");
        }

        urlMappingRepository.delete(urlMapping);
    }
}