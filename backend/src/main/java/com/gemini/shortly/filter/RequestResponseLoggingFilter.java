// File: backend/src/main/java/com/gemini/shortly/filter/RequestResponseLoggingFilter.java
package com.gemini.shortly.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // We need to wrap the request and response to cache their content.
        // This is because the request/response body can only be read once.
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        // Let the request proceed down the filter chain
        filterChain.doFilter(requestWrapper, responseWrapper);

        long timeTaken = System.currentTimeMillis() - startTime;

        // Get request and response body as strings
        String requestBody = getContentAsString(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = getContentAsString(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

        // Log the details
        log.info(
            "\n==================== REQUEST LOG START ====================\n" +
            "METHOD: [{}]; URI: [{}];\n" +
            "REQUEST BODY: {};\n" +
            "RESPONSE STATUS: [{}]; RESPONSE BODY: {};\n" +
            "TIME TAKEN: {}ms\n" +
            "==================== REQUEST LOG END ======================",
            request.getMethod(), request.getRequestURI(), requestBody,
            response.getStatus(), responseBody, timeTaken
        );

        // Finally, copy the cached response content to the actual response.
        responseWrapper.copyBodyToResponse();
    }

    private String getContentAsString(byte[] buf, String charsetName) {
        if (buf == null || buf.length == 0) {
            return "";
        }
        try {
            return new String(buf, 0, buf.length, charsetName);
        } catch (UnsupportedEncodingException ex) {
            return "Unsupported Encoding";
        }
    }
}