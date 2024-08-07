package com.ang.rest.exceptions;


import lombok.Data;

@Data
public class ErrorResponse {
    public String message;
    public int statusCode;

    public long timeStamp;


    public ErrorResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.timeStamp = System.currentTimeMillis();
    }
}
