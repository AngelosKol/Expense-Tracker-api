package com.ang.rest.exception;

public class MalformedTokenException extends RuntimeException {

    public MalformedTokenException(String message) {
        super(message);
    }
}
