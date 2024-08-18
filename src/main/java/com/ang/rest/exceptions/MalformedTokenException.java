package com.ang.rest.exceptions;

public class MalformedTokenException extends  RuntimeException {

    public MalformedTokenException(String message){
        super( message);
    }
}
