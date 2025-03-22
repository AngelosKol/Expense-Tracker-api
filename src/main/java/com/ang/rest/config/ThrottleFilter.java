package com.ang.rest.config;

import com.ang.rest.domain.dto.ErrorResponse;
import com.ang.rest.exception.RateLimitException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ThrottleFilter implements Filter {
    private final int requestsPerSecond = 5;
    private final Map<String, RateLimiter> limiters = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String clientIp = httpRequest.getRemoteAddr();

        RateLimiter rateLimiter = limiters.computeIfAbsent(clientIp, ip -> RateLimiter.create(requestsPerSecond));

        if (!rateLimiter.tryAcquire()) {
            handleRateLimitsException(httpResponse, "Too many requests. Please try again later",httpRequest.getRequestURI());
            return;
        }

        chain.doFilter(request, response);
    }

    private void handleRateLimitsException(HttpServletResponse response, String message, String path) throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType("application/json");
        ErrorResponse errorResponse = new ErrorResponse(message, path, HttpStatus.TOO_MANY_REQUESTS.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
