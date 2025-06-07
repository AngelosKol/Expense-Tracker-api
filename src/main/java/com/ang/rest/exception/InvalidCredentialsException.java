package com.ang.rest.exception;

public class InvalidCredentialsException extends  RuntimeException{
    public InvalidCredentialsException(String msg) {
        super(msg);
    }
}
