package com.ang.rest.exception;

import com.ang.rest.jwt.JwtAuthenticationFilter;
import com.ang.rest.domain.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@ControllerAdvice
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),webRequest.getRequestURI(), HttpStatus.NOT_FOUND.value());
        return createJsonResponse(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),webRequest.getRequestURI(), HttpStatus.CONFLICT.value() );
        return createJsonResponse(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MalformedTokenException.class)
    public  ResponseEntity<String> handleMalformedTokenException(MalformedTokenException ex, HttpServletRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), webRequest.getRequestURI(),HttpStatus.UNAUTHORIZED.value());
        logger.error("Failed to extract claims from token: {}", HttpStatus.UNAUTHORIZED.value() );
        return createJsonResponse(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<String> createJsonResponse(ErrorResponse errorResponse, HttpStatus status) {
        try {
            String json = objectMapper.writeValueAsString(errorResponse);
            return new ResponseEntity<>(json, status);
        } catch (Exception e) {
            return new ResponseEntity<>("Unhandled exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}